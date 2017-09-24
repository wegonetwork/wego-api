package network.wego.web.rest;

import com.codahale.metrics.annotation.Timed;
import network.wego.domain.Passenger;
import network.wego.service.PassengerService;
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
 * REST controller for managing Passenger.
 */
@RestController
@RequestMapping("/api")
public class PassengerResource {

    private final Logger log = LoggerFactory.getLogger(PassengerResource.class);

    private static final String ENTITY_NAME = "passenger";

    private final PassengerService passengerService;

    public PassengerResource(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    /**
     * POST  /passengers : Create a new passenger.
     *
     * @param passenger the passenger to create
     * @return the ResponseEntity with status 201 (Created) and with body the new passenger, or with status 400 (Bad Request) if the passenger has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/passengers")
    @Timed
    public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody Passenger passenger) throws URISyntaxException {
        log.debug("REST request to save Passenger : {}", passenger);
        if (passenger.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new passenger cannot already have an ID")).body(null);
        }
        Passenger result = passengerService.save(passenger);
        return ResponseEntity.created(new URI("/api/passengers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /passengers : Updates an existing passenger.
     *
     * @param passenger the passenger to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated passenger,
     * or with status 400 (Bad Request) if the passenger is not valid,
     * or with status 500 (Internal Server Error) if the passenger couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/passengers")
    @Timed
    public ResponseEntity<Passenger> updatePassenger(@Valid @RequestBody Passenger passenger) throws URISyntaxException {
        log.debug("REST request to update Passenger : {}", passenger);
        if (passenger.getId() == null) {
            return createPassenger(passenger);
        }
        Passenger result = passengerService.save(passenger);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, passenger.getId().toString()))
            .body(result);
    }

    /**
     * GET  /passengers : get all the passengers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of passengers in body
     */
    @GetMapping("/passengers")
    @Timed
    public ResponseEntity<List<Passenger>> getAllPassengers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Passengers");
        Page<Passenger> page = passengerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/passengers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /passengers/:id : get the "id" passenger.
     *
     * @param id the id of the passenger to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the passenger, or with status 404 (Not Found)
     */
    @GetMapping("/passengers/{id}")
    @Timed
    public ResponseEntity<Passenger> getPassenger(@PathVariable Long id) {
        log.debug("REST request to get Passenger : {}", id);
        Passenger passenger = passengerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(passenger));
    }

    /**
     * DELETE  /passengers/:id : delete the "id" passenger.
     *
     * @param id the id of the passenger to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/passengers/{id}")
    @Timed
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        log.debug("REST request to delete Passenger : {}", id);
        passengerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
