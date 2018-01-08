package at.meroff.bac.web.rest;

import at.meroff.bac.InterpreterApp;

import at.meroff.bac.domain.Field;
import at.meroff.bac.domain.Card;
import at.meroff.bac.repository.FieldRepository;
import at.meroff.bac.service.FieldService;
import at.meroff.bac.service.dto.FieldDTO;
import at.meroff.bac.service.mapper.FieldMapper;
import at.meroff.bac.web.rest.errors.ExceptionTranslator;
import at.meroff.bac.service.dto.FieldCriteria;
import at.meroff.bac.service.FieldQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static at.meroff.bac.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import at.meroff.bac.domain.enumeration.LayoutType;
/**
 * Test class for the FieldResource REST controller.
 *
 * @see FieldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterpreterApp.class)
public class FieldResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ORIG_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ORIG_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ORIG_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ORIG_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SVG_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SVG_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_SVG_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SVG_IMAGE_CONTENT_TYPE = "image/png";

    private static final LayoutType DEFAULT_LAYOUT_TYPE = LayoutType.DEFAULT;
    private static final LayoutType UPDATED_LAYOUT_TYPE = LayoutType.STAR;

    private static final byte[] DEFAULT_RESULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESULT_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_RESULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESULT_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private FieldQueryService fieldQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFieldMockMvc;

    private Field field;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FieldResource fieldResource = new FieldResource(fieldService, fieldQueryService);
        this.restFieldMockMvc = MockMvcBuilders.standaloneSetup(fieldResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Field createEntity(EntityManager em) {
        Field field = new Field()
            .description(DEFAULT_DESCRIPTION)
            .origImage(DEFAULT_ORIG_IMAGE)
            .origImageContentType(DEFAULT_ORIG_IMAGE_CONTENT_TYPE)
            .svgImage(DEFAULT_SVG_IMAGE)
            .svgImageContentType(DEFAULT_SVG_IMAGE_CONTENT_TYPE)
            .layoutType(DEFAULT_LAYOUT_TYPE)
            .resultImage(DEFAULT_RESULT_IMAGE)
            .resultImageContentType(DEFAULT_RESULT_IMAGE_CONTENT_TYPE);
        return field;
    }

    @Before
    public void initTest() {
        field = createEntity(em);
    }

    @Test
    @Transactional
    public void createField() throws Exception {
        int databaseSizeBeforeCreate = fieldRepository.findAll().size();

        // Create the Field
        FieldDTO fieldDTO = fieldMapper.toDto(field);
        restFieldMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldDTO)))
            .andExpect(status().isCreated());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeCreate + 1);
        Field testField = fieldList.get(fieldList.size() - 1);
        assertThat(testField.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testField.getOrigImage()).isEqualTo(DEFAULT_ORIG_IMAGE);
        assertThat(testField.getOrigImageContentType()).isEqualTo(DEFAULT_ORIG_IMAGE_CONTENT_TYPE);
        assertThat(testField.getSvgImage()).isEqualTo(DEFAULT_SVG_IMAGE);
        assertThat(testField.getSvgImageContentType()).isEqualTo(DEFAULT_SVG_IMAGE_CONTENT_TYPE);
        assertThat(testField.getLayoutType()).isEqualTo(DEFAULT_LAYOUT_TYPE);
        assertThat(testField.getResultImage()).isEqualTo(DEFAULT_RESULT_IMAGE);
        assertThat(testField.getResultImageContentType()).isEqualTo(DEFAULT_RESULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldRepository.findAll().size();

        // Create the Field with an existing ID
        field.setId(1L);
        FieldDTO fieldDTO = fieldMapper.toDto(field);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFields() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get all the fieldList
        restFieldMockMvc.perform(get("/api/fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(field.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
//            .andExpect(jsonPath("$.[*].origImageContentType").value(hasItem(DEFAULT_ORIG_IMAGE_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].origImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ORIG_IMAGE))))
  //          .andExpect(jsonPath("$.[*].svgImageContentType").value(hasItem(DEFAULT_SVG_IMAGE_CONTENT_TYPE)))
    //        .andExpect(jsonPath("$.[*].svgImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_SVG_IMAGE))))
            .andExpect(jsonPath("$.[*].layoutType").value(hasItem(DEFAULT_LAYOUT_TYPE.toString())));
      //      .andExpect(jsonPath("$.[*].resultImageContentType").value(hasItem(DEFAULT_RESULT_IMAGE_CONTENT_TYPE)))
        //    .andExpect(jsonPath("$.[*].resultImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getField() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get the field
        restFieldMockMvc.perform(get("/api/fields/{id}", field.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(field.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.origImageContentType").value(DEFAULT_ORIG_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.origImage").value(Base64Utils.encodeToString(DEFAULT_ORIG_IMAGE)))
            .andExpect(jsonPath("$.svgImageContentType").value(DEFAULT_SVG_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.svgImage").value(Base64Utils.encodeToString(DEFAULT_SVG_IMAGE)))
            .andExpect(jsonPath("$.layoutType").value(DEFAULT_LAYOUT_TYPE.toString()))
            .andExpect(jsonPath("$.resultImageContentType").value(DEFAULT_RESULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.resultImage").value(Base64Utils.encodeToString(DEFAULT_RESULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getAllFieldsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get all the fieldList where description equals to DEFAULT_DESCRIPTION
//        defaultFieldShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the fieldList where description equals to UPDATED_DESCRIPTION
        defaultFieldShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFieldsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get all the fieldList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
//        defaultFieldShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the fieldList where description equals to UPDATED_DESCRIPTION
        defaultFieldShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFieldsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get all the fieldList where description is not null
//        defaultFieldShouldBeFound("description.specified=true");

        // Get all the fieldList where description is null
        defaultFieldShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByLayoutTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get all the fieldList where layoutType equals to DEFAULT_LAYOUT_TYPE
//        defaultFieldShouldBeFound("layoutType.equals=" + DEFAULT_LAYOUT_TYPE);

        // Get all the fieldList where layoutType equals to UPDATED_LAYOUT_TYPE
        defaultFieldShouldNotBeFound("layoutType.equals=" + UPDATED_LAYOUT_TYPE);
    }

    @Test
    @Transactional
    public void getAllFieldsByLayoutTypeIsInShouldWork() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get all the fieldList where layoutType in DEFAULT_LAYOUT_TYPE or UPDATED_LAYOUT_TYPE
//        defaultFieldShouldBeFound("layoutType.in=" + DEFAULT_LAYOUT_TYPE + "," + UPDATED_LAYOUT_TYPE);

        // Get all the fieldList where layoutType equals to UPDATED_LAYOUT_TYPE
        defaultFieldShouldNotBeFound("layoutType.in=" + UPDATED_LAYOUT_TYPE);
    }

    @Test
    @Transactional
    public void getAllFieldsByLayoutTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get all the fieldList where layoutType is not null
//        defaultFieldShouldBeFound("layoutType.specified=true");

        // Get all the fieldList where layoutType is null
        defaultFieldShouldNotBeFound("layoutType.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByCardIsEqualToSomething() throws Exception {
        // Initialize the database
        Card card = CardResourceIntTest.createEntity(em);
        em.persist(card);
        em.flush();
        field.addCard(card);
        fieldRepository.saveAndFlush(field);
        Long cardId = card.getId();

        // Get all the fieldList where card equals to cardId
//        defaultFieldShouldBeFound("cardId.equals=" + cardId);

        // Get all the fieldList where card equals to cardId + 1
        defaultFieldShouldNotBeFound("cardId.equals=" + (cardId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFieldShouldBeFound(String filter) throws Exception {
        restFieldMockMvc.perform(get("/api/fields?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(field.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].origImageContentType").value(hasItem(DEFAULT_ORIG_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].origImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ORIG_IMAGE))))
            .andExpect(jsonPath("$.[*].svgImageContentType").value(hasItem(DEFAULT_SVG_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].svgImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_SVG_IMAGE))))
            .andExpect(jsonPath("$.[*].layoutType").value(hasItem(DEFAULT_LAYOUT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].resultImageContentType").value(hasItem(DEFAULT_RESULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resultImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESULT_IMAGE))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFieldShouldNotBeFound(String filter) throws Exception {
        restFieldMockMvc.perform(get("/api/fields?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingField() throws Exception {
        // Get the field
        restFieldMockMvc.perform(get("/api/fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateField() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);
        int databaseSizeBeforeUpdate = fieldRepository.findAll().size();

        // Update the field
        Field updatedField = fieldRepository.findOne(field.getId());
        // Disconnect from session so that the updates on updatedField are not directly saved in db
        em.detach(updatedField);
        updatedField
            .description(UPDATED_DESCRIPTION)
            .origImage(UPDATED_ORIG_IMAGE)
            .origImageContentType(UPDATED_ORIG_IMAGE_CONTENT_TYPE)
            .svgImage(UPDATED_SVG_IMAGE)
            .svgImageContentType(UPDATED_SVG_IMAGE_CONTENT_TYPE)
            .layoutType(UPDATED_LAYOUT_TYPE)
            .resultImage(UPDATED_RESULT_IMAGE)
            .resultImageContentType(UPDATED_RESULT_IMAGE_CONTENT_TYPE);
        FieldDTO fieldDTO = fieldMapper.toDto(updatedField);

        restFieldMockMvc.perform(put("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldDTO)))
            .andExpect(status().isOk());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeUpdate);
        Field testField = fieldList.get(fieldList.size() - 1);
        assertThat(testField.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testField.getOrigImage()).isEqualTo(UPDATED_ORIG_IMAGE);
        assertThat(testField.getOrigImageContentType()).isEqualTo(UPDATED_ORIG_IMAGE_CONTENT_TYPE);
        assertThat(testField.getSvgImage()).isEqualTo(UPDATED_SVG_IMAGE);
        assertThat(testField.getSvgImageContentType()).isEqualTo(UPDATED_SVG_IMAGE_CONTENT_TYPE);
        assertThat(testField.getLayoutType()).isEqualTo(UPDATED_LAYOUT_TYPE);
        assertThat(testField.getResultImage()).isEqualTo(UPDATED_RESULT_IMAGE);
        assertThat(testField.getResultImageContentType()).isEqualTo(UPDATED_RESULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingField() throws Exception {
        int databaseSizeBeforeUpdate = fieldRepository.findAll().size();

        // Create the Field
        FieldDTO fieldDTO = fieldMapper.toDto(field);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFieldMockMvc.perform(put("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldDTO)))
            .andExpect(status().isCreated());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteField() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);
        int databaseSizeBeforeDelete = fieldRepository.findAll().size();

        // Get the field
        restFieldMockMvc.perform(delete("/api/fields/{id}", field.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Field.class);
        Field field1 = new Field();
        field1.setId(1L);
        Field field2 = new Field();
        field2.setId(field1.getId());
        assertThat(field1).isEqualTo(field2);
        field2.setId(2L);
        assertThat(field1).isNotEqualTo(field2);
        field1.setId(null);
        assertThat(field1).isNotEqualTo(field2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldDTO.class);
        FieldDTO fieldDTO1 = new FieldDTO();
        fieldDTO1.setId(1L);
        FieldDTO fieldDTO2 = new FieldDTO();
        assertThat(fieldDTO1).isNotEqualTo(fieldDTO2);
        fieldDTO2.setId(fieldDTO1.getId());
        assertThat(fieldDTO1).isEqualTo(fieldDTO2);
        fieldDTO2.setId(2L);
        assertThat(fieldDTO1).isNotEqualTo(fieldDTO2);
        fieldDTO1.setId(null);
        assertThat(fieldDTO1).isNotEqualTo(fieldDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fieldMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fieldMapper.fromId(null)).isNull();
    }
}
