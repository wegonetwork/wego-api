package network.wego.service;

import network.wego.domain.Terms;
import network.wego.repository.TermsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Terms.
 */
@Service
@Transactional
public class TermsService {

    private final Logger log = LoggerFactory.getLogger(TermsService.class);

    private final TermsRepository termsRepository;

    public TermsService(TermsRepository termsRepository) {
        this.termsRepository = termsRepository;
    }

    /**
     * Save a terms.
     *
     * @param terms the entity to save
     * @return the persisted entity
     */
    public Terms save(Terms terms) {
        log.debug("Request to save Terms : {}", terms);
        return termsRepository.save(terms);
    }

    /**
     *  Get all the terms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Terms> findAll(Pageable pageable) {
        log.debug("Request to get all Terms");
        return termsRepository.findAll(pageable);
    }

    /**
     *  Get one terms by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Terms findOne(Long id) {
        log.debug("Request to get Terms : {}", id);
        return termsRepository.findOne(id);
    }

    /**
     *  Delete the  terms by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Terms : {}", id);
        termsRepository.delete(id);
    }
}
