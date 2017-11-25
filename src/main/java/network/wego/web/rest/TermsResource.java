package network.wego.web.rest;

import com.codahale.metrics.annotation.Timed;
import network.wego.domain.Terms;
import network.wego.service.TermsService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Terms.
 */
@RestController
@RequestMapping("/api")
public class TermsResource {

    private final Logger log = LoggerFactory.getLogger(TermsResource.class);

    private static final String ENTITY_NAME = "terms";

    private final TermsService termsService;

    public TermsResource(TermsService termsService) {
        this.termsService = termsService;
    }

    /**
     * POST  /terms : Create a new terms.
     *
     * @param terms the terms to create
     * @return the ResponseEntity with status 201 (Created) and with body the new terms, or with status 400 (Bad Request) if the terms has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/terms")
    @Timed
    public ResponseEntity<Terms> createTerms(@RequestBody Terms terms) throws URISyntaxException {
        log.debug("REST request to save Terms : {}", terms);
        if (terms.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new terms cannot already have an ID")).body(null);
        }
        Terms result = termsService.save(terms);
        return ResponseEntity.created(new URI("/api/terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /terms : Updates an existing terms.
     *
     * @param terms the terms to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated terms,
     * or with status 400 (Bad Request) if the terms is not valid,
     * or with status 500 (Internal Server Error) if the terms couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/terms")
    @Timed
    public ResponseEntity<Terms> updateTerms(@RequestBody Terms terms) throws URISyntaxException {
        log.debug("REST request to update Terms : {}", terms);
        if (terms.getId() == null) {
            return createTerms(terms);
        }
        Terms result = termsService.save(terms);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, terms.getId().toString()))
            .body(result);
    }

    /**
     * GET  /terms : get all the terms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of terms in body
     */
    @GetMapping("/terms")
    @Timed
    public ResponseEntity<List<Terms>> getAllTerms(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Terms");
        Page<Terms> page = termsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/terms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /terms/:id : get the "id" terms.
     *
     * @param id the id of the terms to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the terms, or with status 404 (Not Found)
     */
    @GetMapping("/terms/{id}")
    @Timed
    public ResponseEntity<Terms> getTerms(@PathVariable Long id) {
        log.debug("REST request to get Terms : {}", id);
        Terms terms = termsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(terms));
    }

    /**
     * DELETE  /terms/:id : delete the "id" terms.
     *
     * @param id the id of the terms to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/terms/{id}")
    @Timed
    public ResponseEntity<Void> deleteTerms(@PathVariable Long id) {
        log.debug("REST request to delete Terms : {}", id);
        termsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
