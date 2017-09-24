package network.wego.web.rest;

import network.wego.JhipsterApp;

import network.wego.domain.EthAccount;
import network.wego.repository.EthAccountRepository;
import network.wego.service.EthAccountService;
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
 * Test class for the EthAccountResource REST controller.
 *
 * @see EthAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class EthAccountResourceIntTest {

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    @Autowired
    private EthAccountRepository ethAccountRepository;

    @Autowired
    private EthAccountService ethAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEthAccountMockMvc;

    private EthAccount ethAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EthAccountResource ethAccountResource = new EthAccountResource(ethAccountService);
        this.restEthAccountMockMvc = MockMvcBuilders.standaloneSetup(ethAccountResource)
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
    public static EthAccount createEntity(EntityManager em) {
        EthAccount ethAccount = new EthAccount()
            .account(DEFAULT_ACCOUNT)
            .date(DEFAULT_DATE);
        return ethAccount;
    }

    @Before
    public void initTest() {
        ethAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createEthAccount() throws Exception {
        int databaseSizeBeforeCreate = ethAccountRepository.findAll().size();

        // Create the EthAccount
        restEthAccountMockMvc.perform(post("/api/eth-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethAccount)))
            .andExpect(status().isCreated());

        // Validate the EthAccount in the database
        List<EthAccount> ethAccountList = ethAccountRepository.findAll();
        assertThat(ethAccountList).hasSize(databaseSizeBeforeCreate + 1);
        EthAccount testEthAccount = ethAccountList.get(ethAccountList.size() - 1);
        assertThat(testEthAccount.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testEthAccount.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createEthAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ethAccountRepository.findAll().size();

        // Create the EthAccount with an existing ID
        ethAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEthAccountMockMvc.perform(post("/api/eth-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethAccount)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EthAccount> ethAccountList = ethAccountRepository.findAll();
        assertThat(ethAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEthAccounts() throws Exception {
        // Initialize the database
        ethAccountRepository.saveAndFlush(ethAccount);

        // Get all the ethAccountList
        restEthAccountMockMvc.perform(get("/api/eth-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ethAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getEthAccount() throws Exception {
        // Initialize the database
        ethAccountRepository.saveAndFlush(ethAccount);

        // Get the ethAccount
        restEthAccountMockMvc.perform(get("/api/eth-accounts/{id}", ethAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ethAccount.getId().intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEthAccount() throws Exception {
        // Get the ethAccount
        restEthAccountMockMvc.perform(get("/api/eth-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEthAccount() throws Exception {
        // Initialize the database
        ethAccountService.save(ethAccount);

        int databaseSizeBeforeUpdate = ethAccountRepository.findAll().size();

        // Update the ethAccount
        EthAccount updatedEthAccount = ethAccountRepository.findOne(ethAccount.getId());
        updatedEthAccount
            .account(UPDATED_ACCOUNT)
            .date(UPDATED_DATE);

        restEthAccountMockMvc.perform(put("/api/eth-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEthAccount)))
            .andExpect(status().isOk());

        // Validate the EthAccount in the database
        List<EthAccount> ethAccountList = ethAccountRepository.findAll();
        assertThat(ethAccountList).hasSize(databaseSizeBeforeUpdate);
        EthAccount testEthAccount = ethAccountList.get(ethAccountList.size() - 1);
        assertThat(testEthAccount.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testEthAccount.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEthAccount() throws Exception {
        int databaseSizeBeforeUpdate = ethAccountRepository.findAll().size();

        // Create the EthAccount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEthAccountMockMvc.perform(put("/api/eth-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethAccount)))
            .andExpect(status().isCreated());

        // Validate the EthAccount in the database
        List<EthAccount> ethAccountList = ethAccountRepository.findAll();
        assertThat(ethAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEthAccount() throws Exception {
        // Initialize the database
        ethAccountService.save(ethAccount);

        int databaseSizeBeforeDelete = ethAccountRepository.findAll().size();

        // Get the ethAccount
        restEthAccountMockMvc.perform(delete("/api/eth-accounts/{id}", ethAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EthAccount> ethAccountList = ethAccountRepository.findAll();
        assertThat(ethAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EthAccount.class);
        EthAccount ethAccount1 = new EthAccount();
        ethAccount1.setId(1L);
        EthAccount ethAccount2 = new EthAccount();
        ethAccount2.setId(ethAccount1.getId());
        assertThat(ethAccount1).isEqualTo(ethAccount2);
        ethAccount2.setId(2L);
        assertThat(ethAccount1).isNotEqualTo(ethAccount2);
        ethAccount1.setId(null);
        assertThat(ethAccount1).isNotEqualTo(ethAccount2);
    }
}
