package network.wego.web.rest;

import com.codahale.metrics.annotation.Timed;
import network.wego.domain.Ride;
import network.wego.service.RideService;
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
 * REST controller for managing Ride.
 */
@RestController
@RequestMapping("/api")
public class RideResource {

    private final Logger log = LoggerFactory.getLogger(RideResource.class);

    private static final String ENTITY_NAME = "ride";

    private final RideService rideService;

    public RideResource(RideService rideService) {
        this.rideService = rideService;
    }

    /**
     * POST  /rides : Create a new ride.
     *
     * @param ride the ride to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ride, or with status 400 (Bad Request) if the ride has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rides")
    @Timed
    public ResponseEntity<Ride> createRide(@Valid @RequestBody Ride ride) throws URISyntaxException {
        log.debug("REST request to save Ride : {}", ride);
        if (ride.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ride cannot already have an ID")).body(null);
        }
        Ride result = rideService.save(ride);
        return ResponseEntity.created(new URI("/api/rides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rides : Updates an existing ride.
     *
     * @param ride the ride to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ride,
     * or with status 400 (Bad Request) if the ride is not valid,
     * or with status 500 (Internal Server Error) if the ride couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rides")
    @Timed
    public ResponseEntity<Ride> updateRide(@Valid @RequestBody Ride ride) throws URISyntaxException {
        log.debug("REST request to update Ride : {}", ride);
        if (ride.getId() == null) {
            return createRide(ride);
        }
        Ride result = rideService.save(ride);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ride.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rides : get all the rides.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rides in body
     */
    @GetMapping("/rides")
    @Timed
    public ResponseEntity<List<Ride>> getAllRides(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Rides");
        Page<Ride> page = rideService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rides");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rides/:id : get the "id" ride.
     *
     * @param id the id of the ride to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ride, or with status 404 (Not Found)
     */
    @GetMapping("/rides/{id}")
    @Timed
    public ResponseEntity<Ride> getRide(@PathVariable Long id) {
        log.debug("REST request to get Ride : {}", id);
        Ride ride = rideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ride));
    }

    /**
     * DELETE  /rides/:id : delete the "id" ride.
     *
     * @param id the id of the ride to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rides/{id}")
    @Timed
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        log.debug("REST request to delete Ride : {}", id);
        rideService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
