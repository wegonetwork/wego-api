package network.wego.service;

import network.wego.domain.Vehcile;
import network.wego.repository.VehcileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Vehcile.
 */
@Service
@Transactional
public class VehcileService {

    private final Logger log = LoggerFactory.getLogger(VehcileService.class);

    private final VehcileRepository vehcileRepository;

    public VehcileService(VehcileRepository vehcileRepository) {
        this.vehcileRepository = vehcileRepository;
    }

    /**
     * Save a vehcile.
     *
     * @param vehcile the entity to save
     * @return the persisted entity
     */
    public Vehcile save(Vehcile vehcile) {
        log.debug("Request to save Vehcile : {}", vehcile);
        return vehcileRepository.save(vehcile);
    }

    /**
     *  Get all the vehciles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Vehcile> findAll(Pageable pageable) {
        log.debug("Request to get all Vehciles");
        return vehcileRepository.findAll(pageable);
    }

    /**
     *  Get one vehcile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Vehcile findOne(Long id) {
        log.debug("Request to get Vehcile : {}", id);
        return vehcileRepository.findOne(id);
    }

    /**
     *  Delete the  vehcile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Vehcile : {}", id);
        vehcileRepository.delete(id);
    }
}
