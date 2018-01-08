package at.meroff.bac.web.rest;

import at.meroff.bac.InterpreterApp;

import at.meroff.bac.domain.Card;
import at.meroff.bac.domain.Field;
import at.meroff.bac.domain.Card;
import at.meroff.bac.domain.Card;
import at.meroff.bac.repository.CardRepository;
import at.meroff.bac.service.CardService;
import at.meroff.bac.service.dto.CardDTO;
import at.meroff.bac.service.mapper.CardMapper;
import at.meroff.bac.web.rest.errors.ExceptionTranslator;
import at.meroff.bac.service.dto.CardCriteria;
import at.meroff.bac.service.CardQueryService;

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

import javax.persistence.EntityManager;
import java.util.List;

import static at.meroff.bac.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import at.meroff.bac.domain.enumeration.CardType;
/**
 * Test class for the CardResource REST controller.
 *
 * @see CardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterpreterApp.class)
public class CardResourceIntTest {

    private static final Integer DEFAULT_CARD_ID = 1;
    private static final Integer UPDATED_CARD_ID = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final CardType DEFAULT_CARD_TYPE = CardType.TASK;
    private static final CardType UPDATED_CARD_TYPE = CardType.TRANSFER;

    private static final Double DEFAULT_X_1 = 1D;
    private static final Double UPDATED_X_1 = 2D;

    private static final Double DEFAULT_Y_1 = 1D;
    private static final Double UPDATED_Y_1 = 2D;

    private static final Double DEFAULT_X_2 = 1D;
    private static final Double UPDATED_X_2 = 2D;

    private static final Double DEFAULT_Y_2 = 1D;
    private static final Double UPDATED_Y_2 = 2D;

    private static final Double DEFAULT_X_3 = 1D;
    private static final Double UPDATED_X_3 = 2D;

    private static final Double DEFAULT_Y_3 = 1D;
    private static final Double UPDATED_Y_3 = 2D;

    private static final Double DEFAULT_X_4 = 1D;
    private static final Double UPDATED_X_4 = 2D;

    private static final Double DEFAULT_Y_4 = 1D;
    private static final Double UPDATED_Y_4 = 2D;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardQueryService cardQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCardMockMvc;

    private Card card;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CardResource cardResource = new CardResource(cardService, cardQueryService);
        this.restCardMockMvc = MockMvcBuilders.standaloneSetup(cardResource)
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
    public static Card createEntity(EntityManager em) {
        Card card = new Card()
            .cardId(DEFAULT_CARD_ID)
            .description(DEFAULT_DESCRIPTION)
            .cardType(DEFAULT_CARD_TYPE)
            .x1(DEFAULT_X_1)
            .y1(DEFAULT_Y_1)
            .x2(DEFAULT_X_2)
            .y2(DEFAULT_Y_2)
            .x3(DEFAULT_X_3)
            .y3(DEFAULT_Y_3)
            .x4(DEFAULT_X_4)
            .y4(DEFAULT_Y_4);
        return card;
    }

    @Before
    public void initTest() {
        card = createEntity(em);
    }

    @Test
    @Transactional
    public void createCard() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);
        restCardMockMvc.perform(post("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isCreated());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate + 1);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getCardId()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testCard.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCard.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testCard.getx1()).isEqualTo(DEFAULT_X_1);
        assertThat(testCard.gety1()).isEqualTo(DEFAULT_Y_1);
        assertThat(testCard.getx2()).isEqualTo(DEFAULT_X_2);
        assertThat(testCard.gety2()).isEqualTo(DEFAULT_Y_2);
        assertThat(testCard.getx3()).isEqualTo(DEFAULT_X_3);
        assertThat(testCard.gety3()).isEqualTo(DEFAULT_Y_3);
        assertThat(testCard.getx4()).isEqualTo(DEFAULT_X_4);
        assertThat(testCard.gety4()).isEqualTo(DEFAULT_Y_4);
    }

    @Test
    @Transactional
    public void createCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card with an existing ID
        card.setId(1L);
        CardDTO cardDTO = cardMapper.toDto(card);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardMockMvc.perform(post("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCards() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList
        restCardMockMvc.perform(get("/api/cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardId").value(hasItem(DEFAULT_CARD_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].x1").value(hasItem(DEFAULT_X_1.doubleValue())))
            .andExpect(jsonPath("$.[*].y1").value(hasItem(DEFAULT_Y_1.doubleValue())))
            .andExpect(jsonPath("$.[*].x2").value(hasItem(DEFAULT_X_2.doubleValue())))
            .andExpect(jsonPath("$.[*].y2").value(hasItem(DEFAULT_Y_2.doubleValue())))
            .andExpect(jsonPath("$.[*].x3").value(hasItem(DEFAULT_X_3.doubleValue())))
            .andExpect(jsonPath("$.[*].y3").value(hasItem(DEFAULT_Y_3.doubleValue())))
            .andExpect(jsonPath("$.[*].x4").value(hasItem(DEFAULT_X_4.doubleValue())))
            .andExpect(jsonPath("$.[*].y4").value(hasItem(DEFAULT_Y_4.doubleValue())));
    }

    @Test
    @Transactional
    public void getCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(card.getId().intValue()))
            .andExpect(jsonPath("$.cardId").value(DEFAULT_CARD_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE.toString()))
            .andExpect(jsonPath("$.x1").value(DEFAULT_X_1.doubleValue()))
            .andExpect(jsonPath("$.y1").value(DEFAULT_Y_1.doubleValue()))
            .andExpect(jsonPath("$.x2").value(DEFAULT_X_2.doubleValue()))
            .andExpect(jsonPath("$.y2").value(DEFAULT_Y_2.doubleValue()))
            .andExpect(jsonPath("$.x3").value(DEFAULT_X_3.doubleValue()))
            .andExpect(jsonPath("$.y3").value(DEFAULT_Y_3.doubleValue()))
            .andExpect(jsonPath("$.x4").value(DEFAULT_X_4.doubleValue()))
            .andExpect(jsonPath("$.y4").value(DEFAULT_Y_4.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllCardsByCardIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where cardId equals to DEFAULT_CARD_ID
        defaultCardShouldBeFound("cardId.equals=" + DEFAULT_CARD_ID);

        // Get all the cardList where cardId equals to UPDATED_CARD_ID
        defaultCardShouldNotBeFound("cardId.equals=" + UPDATED_CARD_ID);
    }

    @Test
    @Transactional
    public void getAllCardsByCardIdIsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where cardId in DEFAULT_CARD_ID or UPDATED_CARD_ID
        defaultCardShouldBeFound("cardId.in=" + DEFAULT_CARD_ID + "," + UPDATED_CARD_ID);

        // Get all the cardList where cardId equals to UPDATED_CARD_ID
        defaultCardShouldNotBeFound("cardId.in=" + UPDATED_CARD_ID);
    }

    @Test
    @Transactional
    public void getAllCardsByCardIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where cardId is not null
        defaultCardShouldBeFound("cardId.specified=true");

        // Get all the cardList where cardId is null
        defaultCardShouldNotBeFound("cardId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByCardIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where cardId greater than or equals to DEFAULT_CARD_ID
        defaultCardShouldBeFound("cardId.greaterOrEqualThan=" + DEFAULT_CARD_ID);

        // Get all the cardList where cardId greater than or equals to UPDATED_CARD_ID
        defaultCardShouldNotBeFound("cardId.greaterOrEqualThan=" + UPDATED_CARD_ID);
    }

    @Test
    @Transactional
    public void getAllCardsByCardIdIsLessThanSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where cardId less than or equals to DEFAULT_CARD_ID
        defaultCardShouldNotBeFound("cardId.lessThan=" + DEFAULT_CARD_ID);

        // Get all the cardList where cardId less than or equals to UPDATED_CARD_ID
        defaultCardShouldBeFound("cardId.lessThan=" + UPDATED_CARD_ID);
    }


    @Test
    @Transactional
    public void getAllCardsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where description equals to DEFAULT_DESCRIPTION
        defaultCardShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cardList where description equals to UPDATED_DESCRIPTION
        defaultCardShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCardsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCardShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cardList where description equals to UPDATED_DESCRIPTION
        defaultCardShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCardsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where description is not null
        defaultCardShouldBeFound("description.specified=true");

        // Get all the cardList where description is null
        defaultCardShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByCardTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where cardType equals to DEFAULT_CARD_TYPE
        defaultCardShouldBeFound("cardType.equals=" + DEFAULT_CARD_TYPE);

        // Get all the cardList where cardType equals to UPDATED_CARD_TYPE
        defaultCardShouldNotBeFound("cardType.equals=" + UPDATED_CARD_TYPE);
    }

    @Test
    @Transactional
    public void getAllCardsByCardTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where cardType in DEFAULT_CARD_TYPE or UPDATED_CARD_TYPE
        defaultCardShouldBeFound("cardType.in=" + DEFAULT_CARD_TYPE + "," + UPDATED_CARD_TYPE);

        // Get all the cardList where cardType equals to UPDATED_CARD_TYPE
        defaultCardShouldNotBeFound("cardType.in=" + UPDATED_CARD_TYPE);
    }

    @Test
    @Transactional
    public void getAllCardsByCardTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where cardType is not null
        defaultCardShouldBeFound("cardType.specified=true");

        // Get all the cardList where cardType is null
        defaultCardShouldNotBeFound("cardType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByx1IsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x1 equals to DEFAULT_X_1
        defaultCardShouldBeFound("x1.equals=" + DEFAULT_X_1);

        // Get all the cardList where x1 equals to UPDATED_X_1
        defaultCardShouldNotBeFound("x1.equals=" + UPDATED_X_1);
    }

    @Test
    @Transactional
    public void getAllCardsByx1IsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x1 in DEFAULT_X_1 or UPDATED_X_1
        defaultCardShouldBeFound("x1.in=" + DEFAULT_X_1 + "," + UPDATED_X_1);

        // Get all the cardList where x1 equals to UPDATED_X_1
        defaultCardShouldNotBeFound("x1.in=" + UPDATED_X_1);
    }

    @Test
    @Transactional
    public void getAllCardsByx1IsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x1 is not null
        defaultCardShouldBeFound("x1.specified=true");

        // Get all the cardList where x1 is null
        defaultCardShouldNotBeFound("x1.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByy1IsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y1 equals to DEFAULT_Y_1
        defaultCardShouldBeFound("y1.equals=" + DEFAULT_Y_1);

        // Get all the cardList where y1 equals to UPDATED_Y_1
        defaultCardShouldNotBeFound("y1.equals=" + UPDATED_Y_1);
    }

    @Test
    @Transactional
    public void getAllCardsByy1IsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y1 in DEFAULT_Y_1 or UPDATED_Y_1
        defaultCardShouldBeFound("y1.in=" + DEFAULT_Y_1 + "," + UPDATED_Y_1);

        // Get all the cardList where y1 equals to UPDATED_Y_1
        defaultCardShouldNotBeFound("y1.in=" + UPDATED_Y_1);
    }

    @Test
    @Transactional
    public void getAllCardsByy1IsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y1 is not null
        defaultCardShouldBeFound("y1.specified=true");

        // Get all the cardList where y1 is null
        defaultCardShouldNotBeFound("y1.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByx2IsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x2 equals to DEFAULT_X_2
        defaultCardShouldBeFound("x2.equals=" + DEFAULT_X_2);

        // Get all the cardList where x2 equals to UPDATED_X_2
        defaultCardShouldNotBeFound("x2.equals=" + UPDATED_X_2);
    }

    @Test
    @Transactional
    public void getAllCardsByx2IsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x2 in DEFAULT_X_2 or UPDATED_X_2
        defaultCardShouldBeFound("x2.in=" + DEFAULT_X_2 + "," + UPDATED_X_2);

        // Get all the cardList where x2 equals to UPDATED_X_2
        defaultCardShouldNotBeFound("x2.in=" + UPDATED_X_2);
    }

    @Test
    @Transactional
    public void getAllCardsByx2IsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x2 is not null
        defaultCardShouldBeFound("x2.specified=true");

        // Get all the cardList where x2 is null
        defaultCardShouldNotBeFound("x2.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByy2IsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y2 equals to DEFAULT_Y_2
        defaultCardShouldBeFound("y2.equals=" + DEFAULT_Y_2);

        // Get all the cardList where y2 equals to UPDATED_Y_2
        defaultCardShouldNotBeFound("y2.equals=" + UPDATED_Y_2);
    }

    @Test
    @Transactional
    public void getAllCardsByy2IsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y2 in DEFAULT_Y_2 or UPDATED_Y_2
        defaultCardShouldBeFound("y2.in=" + DEFAULT_Y_2 + "," + UPDATED_Y_2);

        // Get all the cardList where y2 equals to UPDATED_Y_2
        defaultCardShouldNotBeFound("y2.in=" + UPDATED_Y_2);
    }

    @Test
    @Transactional
    public void getAllCardsByy2IsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y2 is not null
        defaultCardShouldBeFound("y2.specified=true");

        // Get all the cardList where y2 is null
        defaultCardShouldNotBeFound("y2.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByx3IsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x3 equals to DEFAULT_X_3
        defaultCardShouldBeFound("x3.equals=" + DEFAULT_X_3);

        // Get all the cardList where x3 equals to UPDATED_X_3
        defaultCardShouldNotBeFound("x3.equals=" + UPDATED_X_3);
    }

    @Test
    @Transactional
    public void getAllCardsByx3IsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x3 in DEFAULT_X_3 or UPDATED_X_3
        defaultCardShouldBeFound("x3.in=" + DEFAULT_X_3 + "," + UPDATED_X_3);

        // Get all the cardList where x3 equals to UPDATED_X_3
        defaultCardShouldNotBeFound("x3.in=" + UPDATED_X_3);
    }

    @Test
    @Transactional
    public void getAllCardsByx3IsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x3 is not null
        defaultCardShouldBeFound("x3.specified=true");

        // Get all the cardList where x3 is null
        defaultCardShouldNotBeFound("x3.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByy3IsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y3 equals to DEFAULT_Y_3
        defaultCardShouldBeFound("y3.equals=" + DEFAULT_Y_3);

        // Get all the cardList where y3 equals to UPDATED_Y_3
        defaultCardShouldNotBeFound("y3.equals=" + UPDATED_Y_3);
    }

    @Test
    @Transactional
    public void getAllCardsByy3IsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y3 in DEFAULT_Y_3 or UPDATED_Y_3
        defaultCardShouldBeFound("y3.in=" + DEFAULT_Y_3 + "," + UPDATED_Y_3);

        // Get all the cardList where y3 equals to UPDATED_Y_3
        defaultCardShouldNotBeFound("y3.in=" + UPDATED_Y_3);
    }

    @Test
    @Transactional
    public void getAllCardsByy3IsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y3 is not null
        defaultCardShouldBeFound("y3.specified=true");

        // Get all the cardList where y3 is null
        defaultCardShouldNotBeFound("y3.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByx4IsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x4 equals to DEFAULT_X_4
        defaultCardShouldBeFound("x4.equals=" + DEFAULT_X_4);

        // Get all the cardList where x4 equals to UPDATED_X_4
        defaultCardShouldNotBeFound("x4.equals=" + UPDATED_X_4);
    }

    @Test
    @Transactional
    public void getAllCardsByx4IsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x4 in DEFAULT_X_4 or UPDATED_X_4
        defaultCardShouldBeFound("x4.in=" + DEFAULT_X_4 + "," + UPDATED_X_4);

        // Get all the cardList where x4 equals to UPDATED_X_4
        defaultCardShouldNotBeFound("x4.in=" + UPDATED_X_4);
    }

    @Test
    @Transactional
    public void getAllCardsByx4IsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where x4 is not null
        defaultCardShouldBeFound("x4.specified=true");

        // Get all the cardList where x4 is null
        defaultCardShouldNotBeFound("x4.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByy4IsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y4 equals to DEFAULT_Y_4
        defaultCardShouldBeFound("y4.equals=" + DEFAULT_Y_4);

        // Get all the cardList where y4 equals to UPDATED_Y_4
        defaultCardShouldNotBeFound("y4.equals=" + UPDATED_Y_4);
    }

    @Test
    @Transactional
    public void getAllCardsByy4IsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y4 in DEFAULT_Y_4 or UPDATED_Y_4
        defaultCardShouldBeFound("y4.in=" + DEFAULT_Y_4 + "," + UPDATED_Y_4);

        // Get all the cardList where y4 equals to UPDATED_Y_4
        defaultCardShouldNotBeFound("y4.in=" + UPDATED_Y_4);
    }

    @Test
    @Transactional
    public void getAllCardsByy4IsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where y4 is not null
        defaultCardShouldBeFound("y4.specified=true");

        // Get all the cardList where y4 is null
        defaultCardShouldNotBeFound("y4.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardsByFieldIsEqualToSomething() throws Exception {
        // Initialize the database
        Field field = FieldResourceIntTest.createEntity(em);
        em.persist(field);
        em.flush();
        card.setField(field);
        cardRepository.saveAndFlush(card);
        Long fieldId = field.getId();

        // Get all the cardList where field equals to fieldId
        defaultCardShouldBeFound("fieldId.equals=" + fieldId);

        // Get all the cardList where field equals to fieldId + 1
        defaultCardShouldNotBeFound("fieldId.equals=" + (fieldId + 1));
    }


    @Test
    @Transactional
    public void getAllCardsByTasksIsEqualToSomething() throws Exception {
        // Initialize the database
        Card tasks = CardResourceIntTest.createEntity(em);
        em.persist(tasks);
        em.flush();
        card.addTasks(tasks);
        cardRepository.saveAndFlush(card);
        Long tasksId = tasks.getId();

        // Get all the cardList where tasks equals to tasksId
        defaultCardShouldBeFound("tasksId.equals=" + tasksId);

        // Get all the cardList where tasks equals to tasksId + 1
//        defaultCardShouldNotBeFound("tasksId.equals=" + (tasksId + 1));
    }


    @Test
    @Transactional
    public void getAllCardsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        Card subject = CardResourceIntTest.createEntity(em);
        em.persist(subject);
        em.flush();
        card.setSubject(subject);
        cardRepository.saveAndFlush(card);
        Long subjectId = subject.getId();

        // Get all the cardList where subject equals to subjectId
        defaultCardShouldBeFound("subjectId.equals=" + subjectId);

        // Get all the cardList where subject equals to subjectId + 1
        defaultCardShouldNotBeFound("subjectId.equals=" + (subjectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCardShouldBeFound(String filter) throws Exception {
        restCardMockMvc.perform(get("/api/cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardId").value(hasItem(DEFAULT_CARD_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].x1").value(hasItem(DEFAULT_X_1.doubleValue())))
            .andExpect(jsonPath("$.[*].y1").value(hasItem(DEFAULT_Y_1.doubleValue())))
            .andExpect(jsonPath("$.[*].x2").value(hasItem(DEFAULT_X_2.doubleValue())))
            .andExpect(jsonPath("$.[*].y2").value(hasItem(DEFAULT_Y_2.doubleValue())))
            .andExpect(jsonPath("$.[*].x3").value(hasItem(DEFAULT_X_3.doubleValue())))
            .andExpect(jsonPath("$.[*].y3").value(hasItem(DEFAULT_Y_3.doubleValue())))
            .andExpect(jsonPath("$.[*].x4").value(hasItem(DEFAULT_X_4.doubleValue())))
            .andExpect(jsonPath("$.[*].y4").value(hasItem(DEFAULT_Y_4.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCardShouldNotBeFound(String filter) throws Exception {
        restCardMockMvc.perform(get("/api/cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card
        Card updatedCard = cardRepository.findOne(card.getId());
        // Disconnect from session so that the updates on updatedCard are not directly saved in db
        em.detach(updatedCard);
        updatedCard
            .cardId(UPDATED_CARD_ID)
            .description(UPDATED_DESCRIPTION)
            .cardType(UPDATED_CARD_TYPE)
            .x1(UPDATED_X_1)
            .y1(UPDATED_Y_1)
            .x2(UPDATED_X_2)
            .y2(UPDATED_Y_2)
            .x3(UPDATED_X_3)
            .y3(UPDATED_Y_3)
            .x4(UPDATED_X_4)
            .y4(UPDATED_Y_4);
        CardDTO cardDTO = cardMapper.toDto(updatedCard);

        restCardMockMvc.perform(put("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getCardId()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testCard.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCard.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testCard.getx1()).isEqualTo(UPDATED_X_1);
        assertThat(testCard.gety1()).isEqualTo(UPDATED_Y_1);
        assertThat(testCard.getx2()).isEqualTo(UPDATED_X_2);
        assertThat(testCard.gety2()).isEqualTo(UPDATED_Y_2);
        assertThat(testCard.getx3()).isEqualTo(UPDATED_X_3);
        assertThat(testCard.gety3()).isEqualTo(UPDATED_Y_3);
        assertThat(testCard.getx4()).isEqualTo(UPDATED_X_4);
        assertThat(testCard.gety4()).isEqualTo(UPDATED_Y_4);
    }

    @Test
    @Transactional
    public void updateNonExistingCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCardMockMvc.perform(put("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isCreated());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);
        int databaseSizeBeforeDelete = cardRepository.findAll().size();

        // Get the card
        restCardMockMvc.perform(delete("/api/cards/{id}", card.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Card.class);
        Card card1 = new Card();
        card1.setId(1L);
        Card card2 = new Card();
        card2.setId(card1.getId());
        assertThat(card1).isEqualTo(card2);
        card2.setId(2L);
        assertThat(card1).isNotEqualTo(card2);
        card1.setId(null);
        assertThat(card1).isNotEqualTo(card2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardDTO.class);
        CardDTO cardDTO1 = new CardDTO();
        cardDTO1.setId(1L);
        CardDTO cardDTO2 = new CardDTO();
        assertThat(cardDTO1).isNotEqualTo(cardDTO2);
        cardDTO2.setId(cardDTO1.getId());
        assertThat(cardDTO1).isEqualTo(cardDTO2);
        cardDTO2.setId(2L);
        assertThat(cardDTO1).isNotEqualTo(cardDTO2);
        cardDTO1.setId(null);
        assertThat(cardDTO1).isNotEqualTo(cardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cardMapper.fromId(null)).isNull();
    }
}
