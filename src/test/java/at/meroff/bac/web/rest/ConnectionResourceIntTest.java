package at.meroff.bac.web.rest;

import at.meroff.bac.InterpreterApp;

import at.meroff.bac.domain.Connection;
import at.meroff.bac.domain.Field;
import at.meroff.bac.repository.ConnectionRepository;
import at.meroff.bac.service.ConnectionService;
import at.meroff.bac.service.dto.ConnectionDTO;
import at.meroff.bac.service.mapper.ConnectionMapper;
import at.meroff.bac.web.rest.errors.ExceptionTranslator;
import at.meroff.bac.service.dto.ConnectionCriteria;
import at.meroff.bac.service.ConnectionQueryService;

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

/**
 * Test class for the ConnectionResource REST controller.
 *
 * @see ConnectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterpreterApp.class)
public class ConnectionResourceIntTest {

    private static final Integer DEFAULT_UUID = 1;
    private static final Integer UPDATED_UUID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_END_POINT_1_UUID = 1;
    private static final Integer UPDATED_END_POINT_1_UUID = 2;

    private static final Double DEFAULT_END_POINT_1_X = 1D;
    private static final Double UPDATED_END_POINT_1_X = 2D;

    private static final Double DEFAULT_END_POINT_1_Y = 1D;
    private static final Double UPDATED_END_POINT_1_Y = 2D;

    private static final Double DEFAULT_END_POINT_1_ANGLE = 1D;
    private static final Double UPDATED_END_POINT_1_ANGLE = 2D;

    private static final Boolean DEFAULT_DIRECTED_1 = false;
    private static final Boolean UPDATED_DIRECTED_1 = true;

    private static final Integer DEFAULT_END_POINT_2_UUID = 1;
    private static final Integer UPDATED_END_POINT_2_UUID = 2;

    private static final Double DEFAULT_END_POINT_2_X = 1D;
    private static final Double UPDATED_END_POINT_2_X = 2D;

    private static final Double DEFAULT_END_POINT_2_Y = 1D;
    private static final Double UPDATED_END_POINT_2_Y = 2D;

    private static final Double DEFAULT_END_POINT_2_ANGLE = 1D;
    private static final Double UPDATED_END_POINT_2_ANGLE = 2D;

    private static final Boolean DEFAULT_DIRECTED_2 = false;
    private static final Boolean UPDATED_DIRECTED_2 = true;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private ConnectionMapper connectionMapper;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ConnectionQueryService connectionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConnectionMockMvc;

    private Connection connection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConnectionResource connectionResource = new ConnectionResource(connectionService, connectionQueryService);
        this.restConnectionMockMvc = MockMvcBuilders.standaloneSetup(connectionResource)
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
    public static Connection createEntity(EntityManager em) {
        Connection connection = new Connection()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .endPoint1Uuid(DEFAULT_END_POINT_1_UUID)
            .endPoint1X(DEFAULT_END_POINT_1_X)
            .endPoint1Y(DEFAULT_END_POINT_1_Y)
            .endPoint1Angle(DEFAULT_END_POINT_1_ANGLE)
            .directed1(DEFAULT_DIRECTED_1)
            .endPoint2Uuid(DEFAULT_END_POINT_2_UUID)
            .endPoint2X(DEFAULT_END_POINT_2_X)
            .endPoint2Y(DEFAULT_END_POINT_2_Y)
            .endPoint2Angle(DEFAULT_END_POINT_2_ANGLE)
            .directed2(DEFAULT_DIRECTED_2);
        return connection;
    }

    @Before
    public void initTest() {
        connection = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnection() throws Exception {
        int databaseSizeBeforeCreate = connectionRepository.findAll().size();

        // Create the Connection
        ConnectionDTO connectionDTO = connectionMapper.toDto(connection);
        restConnectionMockMvc.perform(post("/api/connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeCreate + 1);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConnection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConnection.getEndPoint1Uuid()).isEqualTo(DEFAULT_END_POINT_1_UUID);
        assertThat(testConnection.getEndPoint1X()).isEqualTo(DEFAULT_END_POINT_1_X);
        assertThat(testConnection.getEndPoint1Y()).isEqualTo(DEFAULT_END_POINT_1_Y);
        assertThat(testConnection.getEndPoint1Angle()).isEqualTo(DEFAULT_END_POINT_1_ANGLE);
        assertThat(testConnection.isDirected1()).isEqualTo(DEFAULT_DIRECTED_1);
        assertThat(testConnection.getEndPoint2Uuid()).isEqualTo(DEFAULT_END_POINT_2_UUID);
        assertThat(testConnection.getEndPoint2X()).isEqualTo(DEFAULT_END_POINT_2_X);
        assertThat(testConnection.getEndPoint2Y()).isEqualTo(DEFAULT_END_POINT_2_Y);
        assertThat(testConnection.getEndPoint2Angle()).isEqualTo(DEFAULT_END_POINT_2_ANGLE);
        assertThat(testConnection.isDirected2()).isEqualTo(DEFAULT_DIRECTED_2);
    }

    @Test
    @Transactional
    public void createConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectionRepository.findAll().size();

        // Create the Connection with an existing ID
        connection.setId(1L);
        ConnectionDTO connectionDTO = connectionMapper.toDto(connection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectionMockMvc.perform(post("/api/connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConnections() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList
        restConnectionMockMvc.perform(get("/api/connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connection.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].endPoint1Uuid").value(hasItem(DEFAULT_END_POINT_1_UUID)))
            .andExpect(jsonPath("$.[*].endPoint1X").value(hasItem(DEFAULT_END_POINT_1_X.doubleValue())))
            .andExpect(jsonPath("$.[*].endPoint1Y").value(hasItem(DEFAULT_END_POINT_1_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].endPoint1Angle").value(hasItem(DEFAULT_END_POINT_1_ANGLE.doubleValue())))
            .andExpect(jsonPath("$.[*].directed1").value(hasItem(DEFAULT_DIRECTED_1.booleanValue())))
            .andExpect(jsonPath("$.[*].endPoint2Uuid").value(hasItem(DEFAULT_END_POINT_2_UUID)))
            .andExpect(jsonPath("$.[*].endPoint2X").value(hasItem(DEFAULT_END_POINT_2_X.doubleValue())))
            .andExpect(jsonPath("$.[*].endPoint2Y").value(hasItem(DEFAULT_END_POINT_2_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].endPoint2Angle").value(hasItem(DEFAULT_END_POINT_2_ANGLE.doubleValue())))
            .andExpect(jsonPath("$.[*].directed2").value(hasItem(DEFAULT_DIRECTED_2.booleanValue())));
    }

    @Test
    @Transactional
    public void getConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get the connection
        restConnectionMockMvc.perform(get("/api/connections/{id}", connection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(connection.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.endPoint1Uuid").value(DEFAULT_END_POINT_1_UUID))
            .andExpect(jsonPath("$.endPoint1X").value(DEFAULT_END_POINT_1_X.doubleValue()))
            .andExpect(jsonPath("$.endPoint1Y").value(DEFAULT_END_POINT_1_Y.doubleValue()))
            .andExpect(jsonPath("$.endPoint1Angle").value(DEFAULT_END_POINT_1_ANGLE.doubleValue()))
            .andExpect(jsonPath("$.directed1").value(DEFAULT_DIRECTED_1.booleanValue()))
            .andExpect(jsonPath("$.endPoint2Uuid").value(DEFAULT_END_POINT_2_UUID))
            .andExpect(jsonPath("$.endPoint2X").value(DEFAULT_END_POINT_2_X.doubleValue()))
            .andExpect(jsonPath("$.endPoint2Y").value(DEFAULT_END_POINT_2_Y.doubleValue()))
            .andExpect(jsonPath("$.endPoint2Angle").value(DEFAULT_END_POINT_2_ANGLE.doubleValue()))
            .andExpect(jsonPath("$.directed2").value(DEFAULT_DIRECTED_2.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllConnectionsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where uuid equals to DEFAULT_UUID
        defaultConnectionShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the connectionList where uuid equals to UPDATED_UUID
        defaultConnectionShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllConnectionsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultConnectionShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the connectionList where uuid equals to UPDATED_UUID
        defaultConnectionShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllConnectionsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where uuid is not null
        defaultConnectionShouldBeFound("uuid.specified=true");

        // Get all the connectionList where uuid is null
        defaultConnectionShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByUuidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where uuid greater than or equals to DEFAULT_UUID
        defaultConnectionShouldBeFound("uuid.greaterOrEqualThan=" + DEFAULT_UUID);

        // Get all the connectionList where uuid greater than or equals to UPDATED_UUID
        defaultConnectionShouldNotBeFound("uuid.greaterOrEqualThan=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllConnectionsByUuidIsLessThanSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where uuid less than or equals to DEFAULT_UUID
        defaultConnectionShouldNotBeFound("uuid.lessThan=" + DEFAULT_UUID);

        // Get all the connectionList where uuid less than or equals to UPDATED_UUID
        defaultConnectionShouldBeFound("uuid.lessThan=" + UPDATED_UUID);
    }


    @Test
    @Transactional
    public void getAllConnectionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where name equals to DEFAULT_NAME
        defaultConnectionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the connectionList where name equals to UPDATED_NAME
        defaultConnectionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllConnectionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultConnectionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the connectionList where name equals to UPDATED_NAME
        defaultConnectionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllConnectionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where name is not null
        defaultConnectionShouldBeFound("name.specified=true");

        // Get all the connectionList where name is null
        defaultConnectionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1UuidIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Uuid equals to DEFAULT_END_POINT_1_UUID
        defaultConnectionShouldBeFound("endPoint1Uuid.equals=" + DEFAULT_END_POINT_1_UUID);

        // Get all the connectionList where endPoint1Uuid equals to UPDATED_END_POINT_1_UUID
        defaultConnectionShouldNotBeFound("endPoint1Uuid.equals=" + UPDATED_END_POINT_1_UUID);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1UuidIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Uuid in DEFAULT_END_POINT_1_UUID or UPDATED_END_POINT_1_UUID
        defaultConnectionShouldBeFound("endPoint1Uuid.in=" + DEFAULT_END_POINT_1_UUID + "," + UPDATED_END_POINT_1_UUID);

        // Get all the connectionList where endPoint1Uuid equals to UPDATED_END_POINT_1_UUID
        defaultConnectionShouldNotBeFound("endPoint1Uuid.in=" + UPDATED_END_POINT_1_UUID);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1UuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Uuid is not null
        defaultConnectionShouldBeFound("endPoint1Uuid.specified=true");

        // Get all the connectionList where endPoint1Uuid is null
        defaultConnectionShouldNotBeFound("endPoint1Uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1UuidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Uuid greater than or equals to DEFAULT_END_POINT_1_UUID
        defaultConnectionShouldBeFound("endPoint1Uuid.greaterOrEqualThan=" + DEFAULT_END_POINT_1_UUID);

        // Get all the connectionList where endPoint1Uuid greater than or equals to UPDATED_END_POINT_1_UUID
        defaultConnectionShouldNotBeFound("endPoint1Uuid.greaterOrEqualThan=" + UPDATED_END_POINT_1_UUID);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1UuidIsLessThanSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Uuid less than or equals to DEFAULT_END_POINT_1_UUID
        defaultConnectionShouldNotBeFound("endPoint1Uuid.lessThan=" + DEFAULT_END_POINT_1_UUID);

        // Get all the connectionList where endPoint1Uuid less than or equals to UPDATED_END_POINT_1_UUID
        defaultConnectionShouldBeFound("endPoint1Uuid.lessThan=" + UPDATED_END_POINT_1_UUID);
    }


    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1XIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1X equals to DEFAULT_END_POINT_1_X
        defaultConnectionShouldBeFound("endPoint1X.equals=" + DEFAULT_END_POINT_1_X);

        // Get all the connectionList where endPoint1X equals to UPDATED_END_POINT_1_X
        defaultConnectionShouldNotBeFound("endPoint1X.equals=" + UPDATED_END_POINT_1_X);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1XIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1X in DEFAULT_END_POINT_1_X or UPDATED_END_POINT_1_X
        defaultConnectionShouldBeFound("endPoint1X.in=" + DEFAULT_END_POINT_1_X + "," + UPDATED_END_POINT_1_X);

        // Get all the connectionList where endPoint1X equals to UPDATED_END_POINT_1_X
        defaultConnectionShouldNotBeFound("endPoint1X.in=" + UPDATED_END_POINT_1_X);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1XIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1X is not null
        defaultConnectionShouldBeFound("endPoint1X.specified=true");

        // Get all the connectionList where endPoint1X is null
        defaultConnectionShouldNotBeFound("endPoint1X.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1YIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Y equals to DEFAULT_END_POINT_1_Y
        defaultConnectionShouldBeFound("endPoint1Y.equals=" + DEFAULT_END_POINT_1_Y);

        // Get all the connectionList where endPoint1Y equals to UPDATED_END_POINT_1_Y
        defaultConnectionShouldNotBeFound("endPoint1Y.equals=" + UPDATED_END_POINT_1_Y);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1YIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Y in DEFAULT_END_POINT_1_Y or UPDATED_END_POINT_1_Y
        defaultConnectionShouldBeFound("endPoint1Y.in=" + DEFAULT_END_POINT_1_Y + "," + UPDATED_END_POINT_1_Y);

        // Get all the connectionList where endPoint1Y equals to UPDATED_END_POINT_1_Y
        defaultConnectionShouldNotBeFound("endPoint1Y.in=" + UPDATED_END_POINT_1_Y);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1YIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Y is not null
        defaultConnectionShouldBeFound("endPoint1Y.specified=true");

        // Get all the connectionList where endPoint1Y is null
        defaultConnectionShouldNotBeFound("endPoint1Y.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1AngleIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Angle equals to DEFAULT_END_POINT_1_ANGLE
        defaultConnectionShouldBeFound("endPoint1Angle.equals=" + DEFAULT_END_POINT_1_ANGLE);

        // Get all the connectionList where endPoint1Angle equals to UPDATED_END_POINT_1_ANGLE
        defaultConnectionShouldNotBeFound("endPoint1Angle.equals=" + UPDATED_END_POINT_1_ANGLE);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1AngleIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Angle in DEFAULT_END_POINT_1_ANGLE or UPDATED_END_POINT_1_ANGLE
        defaultConnectionShouldBeFound("endPoint1Angle.in=" + DEFAULT_END_POINT_1_ANGLE + "," + UPDATED_END_POINT_1_ANGLE);

        // Get all the connectionList where endPoint1Angle equals to UPDATED_END_POINT_1_ANGLE
        defaultConnectionShouldNotBeFound("endPoint1Angle.in=" + UPDATED_END_POINT_1_ANGLE);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint1AngleIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint1Angle is not null
        defaultConnectionShouldBeFound("endPoint1Angle.specified=true");

        // Get all the connectionList where endPoint1Angle is null
        defaultConnectionShouldNotBeFound("endPoint1Angle.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByDirected1IsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where directed1 equals to DEFAULT_DIRECTED_1
        defaultConnectionShouldBeFound("directed1.equals=" + DEFAULT_DIRECTED_1);

        // Get all the connectionList where directed1 equals to UPDATED_DIRECTED_1
        defaultConnectionShouldNotBeFound("directed1.equals=" + UPDATED_DIRECTED_1);
    }

    @Test
    @Transactional
    public void getAllConnectionsByDirected1IsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where directed1 in DEFAULT_DIRECTED_1 or UPDATED_DIRECTED_1
        defaultConnectionShouldBeFound("directed1.in=" + DEFAULT_DIRECTED_1 + "," + UPDATED_DIRECTED_1);

        // Get all the connectionList where directed1 equals to UPDATED_DIRECTED_1
        defaultConnectionShouldNotBeFound("directed1.in=" + UPDATED_DIRECTED_1);
    }

    @Test
    @Transactional
    public void getAllConnectionsByDirected1IsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where directed1 is not null
        defaultConnectionShouldBeFound("directed1.specified=true");

        // Get all the connectionList where directed1 is null
        defaultConnectionShouldNotBeFound("directed1.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2UuidIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Uuid equals to DEFAULT_END_POINT_2_UUID
        defaultConnectionShouldBeFound("endPoint2Uuid.equals=" + DEFAULT_END_POINT_2_UUID);

        // Get all the connectionList where endPoint2Uuid equals to UPDATED_END_POINT_2_UUID
        defaultConnectionShouldNotBeFound("endPoint2Uuid.equals=" + UPDATED_END_POINT_2_UUID);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2UuidIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Uuid in DEFAULT_END_POINT_2_UUID or UPDATED_END_POINT_2_UUID
        defaultConnectionShouldBeFound("endPoint2Uuid.in=" + DEFAULT_END_POINT_2_UUID + "," + UPDATED_END_POINT_2_UUID);

        // Get all the connectionList where endPoint2Uuid equals to UPDATED_END_POINT_2_UUID
        defaultConnectionShouldNotBeFound("endPoint2Uuid.in=" + UPDATED_END_POINT_2_UUID);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2UuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Uuid is not null
        defaultConnectionShouldBeFound("endPoint2Uuid.specified=true");

        // Get all the connectionList where endPoint2Uuid is null
        defaultConnectionShouldNotBeFound("endPoint2Uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2UuidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Uuid greater than or equals to DEFAULT_END_POINT_2_UUID
        defaultConnectionShouldBeFound("endPoint2Uuid.greaterOrEqualThan=" + DEFAULT_END_POINT_2_UUID);

        // Get all the connectionList where endPoint2Uuid greater than or equals to UPDATED_END_POINT_2_UUID
        defaultConnectionShouldNotBeFound("endPoint2Uuid.greaterOrEqualThan=" + UPDATED_END_POINT_2_UUID);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2UuidIsLessThanSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Uuid less than or equals to DEFAULT_END_POINT_2_UUID
        defaultConnectionShouldNotBeFound("endPoint2Uuid.lessThan=" + DEFAULT_END_POINT_2_UUID);

        // Get all the connectionList where endPoint2Uuid less than or equals to UPDATED_END_POINT_2_UUID
        defaultConnectionShouldBeFound("endPoint2Uuid.lessThan=" + UPDATED_END_POINT_2_UUID);
    }


    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2XIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2X equals to DEFAULT_END_POINT_2_X
        defaultConnectionShouldBeFound("endPoint2X.equals=" + DEFAULT_END_POINT_2_X);

        // Get all the connectionList where endPoint2X equals to UPDATED_END_POINT_2_X
        defaultConnectionShouldNotBeFound("endPoint2X.equals=" + UPDATED_END_POINT_2_X);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2XIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2X in DEFAULT_END_POINT_2_X or UPDATED_END_POINT_2_X
        defaultConnectionShouldBeFound("endPoint2X.in=" + DEFAULT_END_POINT_2_X + "," + UPDATED_END_POINT_2_X);

        // Get all the connectionList where endPoint2X equals to UPDATED_END_POINT_2_X
        defaultConnectionShouldNotBeFound("endPoint2X.in=" + UPDATED_END_POINT_2_X);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2XIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2X is not null
        defaultConnectionShouldBeFound("endPoint2X.specified=true");

        // Get all the connectionList where endPoint2X is null
        defaultConnectionShouldNotBeFound("endPoint2X.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2YIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Y equals to DEFAULT_END_POINT_2_Y
        defaultConnectionShouldBeFound("endPoint2Y.equals=" + DEFAULT_END_POINT_2_Y);

        // Get all the connectionList where endPoint2Y equals to UPDATED_END_POINT_2_Y
        defaultConnectionShouldNotBeFound("endPoint2Y.equals=" + UPDATED_END_POINT_2_Y);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2YIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Y in DEFAULT_END_POINT_2_Y or UPDATED_END_POINT_2_Y
        defaultConnectionShouldBeFound("endPoint2Y.in=" + DEFAULT_END_POINT_2_Y + "," + UPDATED_END_POINT_2_Y);

        // Get all the connectionList where endPoint2Y equals to UPDATED_END_POINT_2_Y
        defaultConnectionShouldNotBeFound("endPoint2Y.in=" + UPDATED_END_POINT_2_Y);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2YIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Y is not null
        defaultConnectionShouldBeFound("endPoint2Y.specified=true");

        // Get all the connectionList where endPoint2Y is null
        defaultConnectionShouldNotBeFound("endPoint2Y.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2AngleIsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Angle equals to DEFAULT_END_POINT_2_ANGLE
        defaultConnectionShouldBeFound("endPoint2Angle.equals=" + DEFAULT_END_POINT_2_ANGLE);

        // Get all the connectionList where endPoint2Angle equals to UPDATED_END_POINT_2_ANGLE
        defaultConnectionShouldNotBeFound("endPoint2Angle.equals=" + UPDATED_END_POINT_2_ANGLE);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2AngleIsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Angle in DEFAULT_END_POINT_2_ANGLE or UPDATED_END_POINT_2_ANGLE
        defaultConnectionShouldBeFound("endPoint2Angle.in=" + DEFAULT_END_POINT_2_ANGLE + "," + UPDATED_END_POINT_2_ANGLE);

        // Get all the connectionList where endPoint2Angle equals to UPDATED_END_POINT_2_ANGLE
        defaultConnectionShouldNotBeFound("endPoint2Angle.in=" + UPDATED_END_POINT_2_ANGLE);
    }

    @Test
    @Transactional
    public void getAllConnectionsByEndPoint2AngleIsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where endPoint2Angle is not null
        defaultConnectionShouldBeFound("endPoint2Angle.specified=true");

        // Get all the connectionList where endPoint2Angle is null
        defaultConnectionShouldNotBeFound("endPoint2Angle.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByDirected2IsEqualToSomething() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where directed2 equals to DEFAULT_DIRECTED_2
        defaultConnectionShouldBeFound("directed2.equals=" + DEFAULT_DIRECTED_2);

        // Get all the connectionList where directed2 equals to UPDATED_DIRECTED_2
        defaultConnectionShouldNotBeFound("directed2.equals=" + UPDATED_DIRECTED_2);
    }

    @Test
    @Transactional
    public void getAllConnectionsByDirected2IsInShouldWork() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where directed2 in DEFAULT_DIRECTED_2 or UPDATED_DIRECTED_2
        defaultConnectionShouldBeFound("directed2.in=" + DEFAULT_DIRECTED_2 + "," + UPDATED_DIRECTED_2);

        // Get all the connectionList where directed2 equals to UPDATED_DIRECTED_2
        defaultConnectionShouldNotBeFound("directed2.in=" + UPDATED_DIRECTED_2);
    }

    @Test
    @Transactional
    public void getAllConnectionsByDirected2IsNullOrNotNull() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList where directed2 is not null
        defaultConnectionShouldBeFound("directed2.specified=true");

        // Get all the connectionList where directed2 is null
        defaultConnectionShouldNotBeFound("directed2.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnectionsByFieldIsEqualToSomething() throws Exception {
        // Initialize the database
        Field field = FieldResourceIntTest.createEntity(em);
        em.persist(field);
        em.flush();
        connection.setField(field);
        connectionRepository.saveAndFlush(connection);
        Long fieldId = field.getId();

        // Get all the connectionList where field equals to fieldId
        defaultConnectionShouldBeFound("fieldId.equals=" + fieldId);

        // Get all the connectionList where field equals to fieldId + 1
        defaultConnectionShouldNotBeFound("fieldId.equals=" + (fieldId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultConnectionShouldBeFound(String filter) throws Exception {
        restConnectionMockMvc.perform(get("/api/connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connection.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].endPoint1Uuid").value(hasItem(DEFAULT_END_POINT_1_UUID)))
            .andExpect(jsonPath("$.[*].endPoint1X").value(hasItem(DEFAULT_END_POINT_1_X.doubleValue())))
            .andExpect(jsonPath("$.[*].endPoint1Y").value(hasItem(DEFAULT_END_POINT_1_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].endPoint1Angle").value(hasItem(DEFAULT_END_POINT_1_ANGLE.doubleValue())))
            .andExpect(jsonPath("$.[*].directed1").value(hasItem(DEFAULT_DIRECTED_1.booleanValue())))
            .andExpect(jsonPath("$.[*].endPoint2Uuid").value(hasItem(DEFAULT_END_POINT_2_UUID)))
            .andExpect(jsonPath("$.[*].endPoint2X").value(hasItem(DEFAULT_END_POINT_2_X.doubleValue())))
            .andExpect(jsonPath("$.[*].endPoint2Y").value(hasItem(DEFAULT_END_POINT_2_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].endPoint2Angle").value(hasItem(DEFAULT_END_POINT_2_ANGLE.doubleValue())))
            .andExpect(jsonPath("$.[*].directed2").value(hasItem(DEFAULT_DIRECTED_2.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultConnectionShouldNotBeFound(String filter) throws Exception {
        restConnectionMockMvc.perform(get("/api/connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingConnection() throws Exception {
        // Get the connection
        restConnectionMockMvc.perform(get("/api/connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();

        // Update the connection
        Connection updatedConnection = connectionRepository.findOne(connection.getId());
        // Disconnect from session so that the updates on updatedConnection are not directly saved in db
        em.detach(updatedConnection);
        updatedConnection
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .endPoint1Uuid(UPDATED_END_POINT_1_UUID)
            .endPoint1X(UPDATED_END_POINT_1_X)
            .endPoint1Y(UPDATED_END_POINT_1_Y)
            .endPoint1Angle(UPDATED_END_POINT_1_ANGLE)
            .directed1(UPDATED_DIRECTED_1)
            .endPoint2Uuid(UPDATED_END_POINT_2_UUID)
            .endPoint2X(UPDATED_END_POINT_2_X)
            .endPoint2Y(UPDATED_END_POINT_2_Y)
            .endPoint2Angle(UPDATED_END_POINT_2_ANGLE)
            .directed2(UPDATED_DIRECTED_2);
        ConnectionDTO connectionDTO = connectionMapper.toDto(updatedConnection);

        restConnectionMockMvc.perform(put("/api/connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectionDTO)))
            .andExpect(status().isOk());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConnection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConnection.getEndPoint1Uuid()).isEqualTo(UPDATED_END_POINT_1_UUID);
        assertThat(testConnection.getEndPoint1X()).isEqualTo(UPDATED_END_POINT_1_X);
        assertThat(testConnection.getEndPoint1Y()).isEqualTo(UPDATED_END_POINT_1_Y);
        assertThat(testConnection.getEndPoint1Angle()).isEqualTo(UPDATED_END_POINT_1_ANGLE);
        assertThat(testConnection.isDirected1()).isEqualTo(UPDATED_DIRECTED_1);
        assertThat(testConnection.getEndPoint2Uuid()).isEqualTo(UPDATED_END_POINT_2_UUID);
        assertThat(testConnection.getEndPoint2X()).isEqualTo(UPDATED_END_POINT_2_X);
        assertThat(testConnection.getEndPoint2Y()).isEqualTo(UPDATED_END_POINT_2_Y);
        assertThat(testConnection.getEndPoint2Angle()).isEqualTo(UPDATED_END_POINT_2_ANGLE);
        assertThat(testConnection.isDirected2()).isEqualTo(UPDATED_DIRECTED_2);
    }

    @Test
    @Transactional
    public void updateNonExistingConnection() throws Exception {
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();

        // Create the Connection
        ConnectionDTO connectionDTO = connectionMapper.toDto(connection);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConnectionMockMvc.perform(put("/api/connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);
        int databaseSizeBeforeDelete = connectionRepository.findAll().size();

        // Get the connection
        restConnectionMockMvc.perform(delete("/api/connections/{id}", connection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Connection.class);
        Connection connection1 = new Connection();
        connection1.setId(1L);
        Connection connection2 = new Connection();
        connection2.setId(connection1.getId());
        assertThat(connection1).isEqualTo(connection2);
        connection2.setId(2L);
        assertThat(connection1).isNotEqualTo(connection2);
        connection1.setId(null);
        assertThat(connection1).isNotEqualTo(connection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectionDTO.class);
        ConnectionDTO connectionDTO1 = new ConnectionDTO();
        connectionDTO1.setId(1L);
        ConnectionDTO connectionDTO2 = new ConnectionDTO();
        assertThat(connectionDTO1).isNotEqualTo(connectionDTO2);
        connectionDTO2.setId(connectionDTO1.getId());
        assertThat(connectionDTO1).isEqualTo(connectionDTO2);
        connectionDTO2.setId(2L);
        assertThat(connectionDTO1).isNotEqualTo(connectionDTO2);
        connectionDTO1.setId(null);
        assertThat(connectionDTO1).isNotEqualTo(connectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(connectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(connectionMapper.fromId(null)).isNull();
    }
}
