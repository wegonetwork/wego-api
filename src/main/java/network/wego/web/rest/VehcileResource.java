package network.wego.web.rest;

import com.codahale.metrics.annotation.Timed;
import network.wego.domain.Vehcile;
import network.wego.service.VehcileService;
import network.wego.web.rest.util.HeaderUtil;
import network.wego.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Vehcile.
 */
@RestController
@RequestMapping("/api")
public class VehcileResource {

    private final Logger log = LoggerFactory.getLogger(VehcileResource.class);

    private static final String ENTITY_NAME = "vehcile";

    private final VehcileService vehcileService;

    public VehcileResource(VehcileService vehcileService) {
        this.vehcileService = vehcileService;
    }

    /**
     * POST  /vehciles : Create a new vehcile.
     *
     * @param vehcile the vehcile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehcile, or with status 400 (Bad Request) if the vehcile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehciles")
    @Timed
    public ResponseEntity<Vehcile> createVehcile(@Valid @RequestBody Vehcile vehcile) throws URISyntaxException {
        log.debug("REST request to save Vehcile : {}", vehcile);
        if (vehcile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehcile cannot already have an ID")).body(null);
        }
        Vehcile result = vehcileService.save(vehcile);
        return ResponseEntity.created(new URI("/api/vehciles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehciles : Updates an existing vehcile.
     *
     * @param vehcile the vehcile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehcile,
     * or with status 400 (Bad Request) if the vehcile is not valid,
     * or with status 500 (Internal Server Error) if the vehcile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehciles")
    @Timed
    public ResponseEntity<Vehcile> updateVehcile(@Valid @RequestBody Vehcile vehcile) throws URISyntaxException {
        log.debug("REST request to update Vehcile : {}", vehcile);
        if (vehcile.getId() == null) {
            return createVehcile(vehcile);
        }
        Vehcile result = vehcileService.save(vehcile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehcile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehciles : get all the vehciles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vehciles in body
     */
    @GetMapping("/vehciles")
    @Timed
    public ResponseEntity<List<Vehcile>> getAllVehciles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Vehciles");
        Page<Vehcile> page = vehcileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vehciles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vehciles/:id : get the "id" vehcile.
     *
     * @param id the id of the vehcile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehcile, or with status 404 (Not Found)
     */
    @GetMapping("/vehciles/{id}")
    @Timed
    public ResponseEntity<Vehcile> getVehcile(@PathVariable Long id) {
        log.debug("REST request to get Vehcile : {}", id);
        Vehcile vehcile = vehcileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehcile));
    }

    /**
     * DELETE  /vehciles/:id : delete the "id" vehcile.
     *
     * @param id the id of the vehcile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehciles/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehcile(@PathVariable Long id) {
        log.debug("REST request to delete Vehcile : {}", id);
        vehcileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
