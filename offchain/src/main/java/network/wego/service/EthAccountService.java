package network.wego.service;

import network.wego.domain.EthAccount;
import network.wego.repository.EthAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EthAccount.
 */
@Service
@Transactional
public class EthAccountService {

    private final Logger log = LoggerFactory.getLogger(EthAccountService.class);

    private final EthAccountRepository ethAccountRepository;

    public EthAccountService(EthAccountRepository ethAccountRepository) {
        this.ethAccountRepository = ethAccountRepository;
    }

    /**
     * Save a ethAccount.
     *
     * @param ethAccount the entity to save
     * @return the persisted entity
     */
    public EthAccount save(EthAccount ethAccount) {
        log.debug("Request to save EthAccount : {}", ethAccount);
        return ethAccountRepository.save(ethAccount);
    }

    /**
     *  Get all the ethAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EthAccount> findAll(Pageable pageable) {
        log.debug("Request to get all EthAccounts");
        return ethAccountRepository.findAll(pageable);
    }

    /**
     *  Get one ethAccount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EthAccount findOne(Long id) {
        log.debug("Request to get EthAccount : {}", id);
        return ethAccountRepository.findOne(id);
    }

    /**
     *  Delete the  ethAccount by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EthAccount : {}", id);
        ethAccountRepository.delete(id);
    }
}
