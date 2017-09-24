package network.wego.web.rest;

import com.codahale.metrics.annotation.Timed;
import network.wego.domain.RideOffer;
import network.wego.service.RideOfferService;
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
 * REST controller for managing RideOffer.
 */
@RestController
@RequestMapping("/api")
public class RideOfferResource {

    private final Logger log = LoggerFactory.getLogger(RideOfferResource.class);

    private static final String ENTITY_NAME = "rideOffer";

    private final RideOfferService rideOfferService;

    public RideOfferResource(RideOfferService rideOfferService) {
        this.rideOfferService = rideOfferService;
    }

    /**
     * POST  /ride-offers : Create a new rideOffer.
     *
     * @param rideOffer the rideOffer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rideOffer, or with status 400 (Bad Request) if the rideOffer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ride-offers")
    @Timed
    public ResponseEntity<RideOffer> createRideOffer(@Valid @RequestBody RideOffer rideOffer) throws URISyntaxException {
        log.debug("REST request to save RideOffer : {}", rideOffer);
        if (rideOffer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rideOffer cannot already have an ID")).body(null);
        }
        RideOffer result = rideOfferService.save(rideOffer);
        return ResponseEntity.created(new URI("/api/ride-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ride-offers : Updates an existing rideOffer.
     *
     * @param rideOffer the rideOffer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rideOffer,
     * or with status 400 (Bad Request) if the rideOffer is not valid,
     * or with status 500 (Internal Server Error) if the rideOffer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ride-offers")
    @Timed
    public ResponseEntity<RideOffer> updateRideOffer(@Valid @RequestBody RideOffer rideOffer) throws URISyntaxException {
        log.debug("REST request to update RideOffer : {}", rideOffer);
        if (rideOffer.getId() == null) {
            return createRideOffer(rideOffer);
        }
        RideOffer result = rideOfferService.save(rideOffer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rideOffer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ride-offers : get all the rideOffers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rideOffers in body
     */
    @GetMapping("/ride-offers")
    @Timed
    public ResponseEntity<List<RideOffer>> getAllRideOffers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RideOffers");
        Page<RideOffer> page = rideOfferService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ride-offers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ride-offers/:id : get the "id" rideOffer.
     *
     * @param id the id of the rideOffer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rideOffer, or with status 404 (Not Found)
     */
    @GetMapping("/ride-offers/{id}")
    @Timed
    public ResponseEntity<RideOffer> getRideOffer(@PathVariable Long id) {
        log.debug("REST request to get RideOffer : {}", id);
        RideOffer rideOffer = rideOfferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rideOffer));
    }

    /**
     * DELETE  /ride-offers/:id : delete the "id" rideOffer.
     *
     * @param id the id of the rideOffer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ride-offers/{id}")
    @Timed
    public ResponseEntity<Void> deleteRideOffer(@PathVariable Long id) {
        log.debug("REST request to delete RideOffer : {}", id);
        rideOfferService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
