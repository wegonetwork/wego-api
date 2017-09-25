package network.wego.web.rest;

import network.wego.JhipsterApp;

import network.wego.domain.Vehcile;
import network.wego.domain.User;
import network.wego.repository.VehcileRepository;
import network.wego.service.VehcileService;
import network.wego.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VehcileResource REST controller.
 *
 * @see VehcileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class VehcileResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Integer DEFAULT_SEATS_NUMBER = 1;
    private static final Integer UPDATED_SEATS_NUMBER = 2;

    private static final String DEFAULT_CAR_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_CAR_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private VehcileRepository vehcileRepository;

    @Autowired
    private VehcileService vehcileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehcileMockMvc;

    private Vehcile vehcile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehcileResource vehcileResource = new VehcileResource(vehcileService);
        this.restVehcileMockMvc = MockMvcBuilders.standaloneSetup(vehcileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehcile createEntity(EntityManager em) {
        Vehcile vehcile = new Vehcile()
            .name(DEFAULT_NAME)
            .model(DEFAULT_MODEL)
            .company(DEFAULT_COMPANY)
            .number(DEFAULT_NUMBER)
            .seatsNumber(DEFAULT_SEATS_NUMBER)
            .carImageURL(DEFAULT_CAR_IMAGE_URL);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        vehcile.setUser(user);
        return vehcile;
    }

    @Before
    public void initTest() {
        vehcile = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehcile() throws Exception {
        int databaseSizeBeforeCreate = vehcileRepository.findAll().size();

        // Create the Vehcile
        restVehcileMockMvc.perform(post("/api/vehciles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehcile)))
            .andExpect(status().isCreated());

        // Validate the Vehcile in the database
        List<Vehcile> vehcileList = vehcileRepository.findAll();
        assertThat(vehcileList).hasSize(databaseSizeBeforeCreate + 1);
        Vehcile testVehcile = vehcileList.get(vehcileList.size() - 1);
        assertThat(testVehcile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehcile.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehcile.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testVehcile.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testVehcile.getSeatsNumber()).isEqualTo(DEFAULT_SEATS_NUMBER);
        assertThat(testVehcile.getCarImageURL()).isEqualTo(DEFAULT_CAR_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createVehcileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehcileRepository.findAll().size();

        // Create the Vehcile with an existing ID
        vehcile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehcileMockMvc.perform(post("/api/vehciles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehcile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Vehcile> vehcileList = vehcileRepository.findAll();
        assertThat(vehcileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehciles() throws Exception {
        // Initialize the database
        vehcileRepository.saveAndFlush(vehcile);

        // Get all the vehcileList
        restVehcileMockMvc.perform(get("/api/vehciles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehcile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].seatsNumber").value(hasItem(DEFAULT_SEATS_NUMBER)))
            .andExpect(jsonPath("$.[*].carImageURL").value(hasItem(DEFAULT_CAR_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    public void getVehcile() throws Exception {
        // Initialize the database
        vehcileRepository.saveAndFlush(vehcile);

        // Get the vehcile
        restVehcileMockMvc.perform(get("/api/vehciles/{id}", vehcile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehcile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.seatsNumber").value(DEFAULT_SEATS_NUMBER))
            .andExpect(jsonPath("$.carImageURL").value(DEFAULT_CAR_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehcile() throws Exception {
        // Get the vehcile
        restVehcileMockMvc.perform(get("/api/vehciles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehcile() throws Exception {
        // Initialize the database
        vehcileService.save(vehcile);

        int databaseSizeBeforeUpdate = vehcileRepository.findAll().size();

        // Update the vehcile
        Vehcile updatedVehcile = vehcileRepository.findOne(vehcile.getId());
        updatedVehcile
            .name(UPDATED_NAME)
            .model(UPDATED_MODEL)
            .company(UPDATED_COMPANY)
            .number(UPDATED_NUMBER)
            .seatsNumber(UPDATED_SEATS_NUMBER)
            .carImageURL(UPDATED_CAR_IMAGE_URL);

        restVehcileMockMvc.perform(put("/api/vehciles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehcile)))
            .andExpect(status().isOk());

        // Validate the Vehcile in the database
        List<Vehcile> vehcileList = vehcileRepository.findAll();
        assertThat(vehcileList).hasSize(databaseSizeBeforeUpdate);
        Vehcile testVehcile = vehcileList.get(vehcileList.size() - 1);
        assertThat(testVehcile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehcile.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehcile.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testVehcile.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testVehcile.getSeatsNumber()).isEqualTo(UPDATED_SEATS_NUMBER);
        assertThat(testVehcile.getCarImageURL()).isEqualTo(UPDATED_CAR_IMAGE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingVehcile() throws Exception {
        int databaseSizeBeforeUpdate = vehcileRepository.findAll().size();

        // Create the Vehcile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehcileMockMvc.perform(put("/api/vehciles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehcile)))
            .andExpect(status().isCreated());

        // Validate the Vehcile in the database
        List<Vehcile> vehcileList = vehcileRepository.findAll();
        assertThat(vehcileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehcile() throws Exception {
        // Initialize the database
        vehcileService.save(vehcile);

        int databaseSizeBeforeDelete = vehcileRepository.findAll().size();

        // Get the vehcile
        restVehcileMockMvc.perform(delete("/api/vehciles/{id}", vehcile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vehcile> vehcileList = vehcileRepository.findAll();
        assertThat(vehcileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehcile.class);
        Vehcile vehcile1 = new Vehcile();
        vehcile1.setId(1L);
        Vehcile vehcile2 = new Vehcile();
        vehcile2.setId(vehcile1.getId());
        assertThat(vehcile1).isEqualTo(vehcile2);
        vehcile2.setId(2L);
        assertThat(vehcile1).isNotEqualTo(vehcile2);
        vehcile1.setId(null);
        assertThat(vehcile1).isNotEqualTo(vehcile2);
    }
}
