package network.wego.service;

import network.wego.domain.Country;
import network.wego.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Country.
 */
@Service
@Transactional
public class CountryService {

    private final Logger log = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Save a country.
     *
     * @param country the entity to save
     * @return the persisted entity
     */
    public Country save(Country country) {
        log.debug("Request to save Country : {}", country);
        return countryRepository.save(country);
    }

    /**
     *  Get all the countries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Country> findAll(Pageable pageable) {
        log.debug("Request to get all Countries");
        return countryRepository.findAll(pageable);
    }

    /**
     *  Get one country by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Country findOne(Long id) {
        log.debug("Request to get Country : {}", id);
        return countryRepository.findOne(id);
    }

    /**
     *  Delete the  country by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Country : {}", id);
        countryRepository.delete(id);
    }
}
