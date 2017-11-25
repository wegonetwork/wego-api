package network.wego.web.rest;

import network.wego.WegoApp;

import network.wego.domain.Terms;
import network.wego.repository.TermsRepository;
import network.wego.service.TermsService;
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
 * Test class for the TermsResource REST controller.
 *
 * @see TermsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WegoApp.class)
public class TermsResourceIntTest {

    private static final String DEFAULT_TERMS = "AAAAAAAAAA";
    private static final String UPDATED_TERMS = "BBBBBBBBBB";

    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private TermsService termsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTermsMockMvc;

    private Terms terms;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TermsResource termsResource = new TermsResource(termsService);
        this.restTermsMockMvc = MockMvcBuilders.standaloneSetup(termsResource)
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
    public static Terms createEntity(EntityManager em) {
        Terms terms = new Terms()
            .terms(DEFAULT_TERMS);
        return terms;
    }

    @Before
    public void initTest() {
        terms = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerms() throws Exception {
        int databaseSizeBeforeCreate = termsRepository.findAll().size();

        // Create the Terms
        restTermsMockMvc.perform(post("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terms)))
            .andExpect(status().isCreated());

        // Validate the Terms in the database
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeCreate + 1);
        Terms testTerms = termsList.get(termsList.size() - 1);
        assertThat(testTerms.getTerms()).isEqualTo(DEFAULT_TERMS);
    }

    @Test
    @Transactional
    public void createTermsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = termsRepository.findAll().size();

        // Create the Terms with an existing ID
        terms.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermsMockMvc.perform(post("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terms)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTerms() throws Exception {
        // Initialize the database
        termsRepository.saveAndFlush(terms);

        // Get all the termsList
        restTermsMockMvc.perform(get("/api/terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terms.getId().intValue())))
            .andExpect(jsonPath("$.[*].terms").value(hasItem(DEFAULT_TERMS.toString())));
    }

    @Test
    @Transactional
    public void getTerms() throws Exception {
        // Initialize the database
        termsRepository.saveAndFlush(terms);

        // Get the terms
        restTermsMockMvc.perform(get("/api/terms/{id}", terms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(terms.getId().intValue()))
            .andExpect(jsonPath("$.terms").value(DEFAULT_TERMS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTerms() throws Exception {
        // Get the terms
        restTermsMockMvc.perform(get("/api/terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerms() throws Exception {
        // Initialize the database
        termsService.save(terms);

        int databaseSizeBeforeUpdate = termsRepository.findAll().size();

        // Update the terms
        Terms updatedTerms = termsRepository.findOne(terms.getId());
        updatedTerms
            .terms(UPDATED_TERMS);

        restTermsMockMvc.perform(put("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerms)))
            .andExpect(status().isOk());

        // Validate the Terms in the database
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeUpdate);
        Terms testTerms = termsList.get(termsList.size() - 1);
        assertThat(testTerms.getTerms()).isEqualTo(UPDATED_TERMS);
    }

    @Test
    @Transactional
    public void updateNonExistingTerms() throws Exception {
        int databaseSizeBeforeUpdate = termsRepository.findAll().size();

        // Create the Terms

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTermsMockMvc.perform(put("/api/terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terms)))
            .andExpect(status().isCreated());

        // Validate the Terms in the database
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTerms() throws Exception {
        // Initialize the database
        termsService.save(terms);

        int databaseSizeBeforeDelete = termsRepository.findAll().size();

        // Get the terms
        restTermsMockMvc.perform(delete("/api/terms/{id}", terms.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Terms> termsList = termsRepository.findAll();
        assertThat(termsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Terms.class);
        Terms terms1 = new Terms();
        terms1.setId(1L);
        Terms terms2 = new Terms();
        terms2.setId(terms1.getId());
        assertThat(terms1).isEqualTo(terms2);
        terms2.setId(2L);
        assertThat(terms1).isNotEqualTo(terms2);
        terms1.setId(null);
        assertThat(terms1).isNotEqualTo(terms2);
    }
}
