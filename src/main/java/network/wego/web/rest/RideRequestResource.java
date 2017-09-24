package network.wego.web.rest;

import com.codahale.metrics.annotation.Timed;
import network.wego.domain.RideRequest;
import network.wego.service.RideRequestService;
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
 * REST controller for managing RideRequest.
 */
@RestController
@RequestMapping("/api")
public class RideRequestResource {

    private final Logger log = LoggerFactory.getLogger(RideRequestResource.class);

    private static final String ENTITY_NAME = "rideRequest";

    private final RideRequestService rideRequestService;

    public RideRequestResource(RideRequestService rideRequestService) {
        this.rideRequestService = rideRequestService;
    }

    /**
     * POST  /ride-requests : Create a new rideRequest.
     *
     * @param rideRequest the rideRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rideRequest, or with status 400 (Bad Request) if the rideRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ride-requests")
    @Timed
    public ResponseEntity<RideRequest> createRideRequest(@Valid @RequestBody RideRequest rideRequest) throws URISyntaxException {
        log.debug("REST request to save RideRequest : {}", rideRequest);
        if (rideRequest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rideRequest cannot already have an ID")).body(null);
        }
        RideRequest result = rideRequestService.save(rideRequest);
        return ResponseEntity.created(new URI("/api/ride-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ride-requests : Updates an existing rideRequest.
     *
     * @param rideRequest the rideRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rideRequest,
     * or with status 400 (Bad Request) if the rideRequest is not valid,
     * or with status 500 (Internal Server Error) if the rideRequest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ride-requests")
    @Timed
    public ResponseEntity<RideRequest> updateRideRequest(@Valid @RequestBody RideRequest rideRequest) throws URISyntaxException {
        log.debug("REST request to update RideRequest : {}", rideRequest);
        if (rideRequest.getId() == null) {
            return createRideRequest(rideRequest);
        }
        RideRequest result = rideRequestService.save(rideRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rideRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ride-requests : get all the rideRequests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rideRequests in body
     */
    @GetMapping("/ride-requests")
    @Timed
    public ResponseEntity<List<RideRequest>> getAllRideRequests(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RideRequests");
        Page<RideRequest> page = rideRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ride-requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ride-requests/:id : get the "id" rideRequest.
     *
     * @param id the id of the rideRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rideRequest, or with status 404 (Not Found)
     */
    @GetMapping("/ride-requests/{id}")
    @Timed
    public ResponseEntity<RideRequest> getRideRequest(@PathVariable Long id) {
        log.debug("REST request to get RideRequest : {}", id);
        RideRequest rideRequest = rideRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rideRequest));
    }

    /**
     * DELETE  /ride-requests/:id : delete the "id" rideRequest.
     *
     * @param id the id of the rideRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ride-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteRideRequest(@PathVariable Long id) {
        log.debug("REST request to delete RideRequest : {}", id);
        rideRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
