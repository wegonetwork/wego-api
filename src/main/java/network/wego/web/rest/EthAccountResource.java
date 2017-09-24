package network.wego.web.rest;

import com.codahale.metrics.annotation.Timed;
import network.wego.domain.EthAccount;
import network.wego.service.EthAccountService;
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
 * REST controller for managing EthAccount.
 */
@RestController
@RequestMapping("/api")
public class EthAccountResource {

    private final Logger log = LoggerFactory.getLogger(EthAccountResource.class);

    private static final String ENTITY_NAME = "ethAccount";

    private final EthAccountService ethAccountService;

    public EthAccountResource(EthAccountService ethAccountService) {
        this.ethAccountService = ethAccountService;
    }

    /**
     * POST  /eth-accounts : Create a new ethAccount.
     *
     * @param ethAccount the ethAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ethAccount, or with status 400 (Bad Request) if the ethAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eth-accounts")
    @Timed
    public ResponseEntity<EthAccount> createEthAccount(@RequestBody EthAccount ethAccount) throws URISyntaxException {
        log.debug("REST request to save EthAccount : {}", ethAccount);
        if (ethAccount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ethAccount cannot already have an ID")).body(null);
        }
        EthAccount result = ethAccountService.save(ethAccount);
        return ResponseEntity.created(new URI("/api/eth-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eth-accounts : Updates an existing ethAccount.
     *
     * @param ethAccount the ethAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ethAccount,
     * or with status 400 (Bad Request) if the ethAccount is not valid,
     * or with status 500 (Internal Server Error) if the ethAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eth-accounts")
    @Timed
    public ResponseEntity<EthAccount> updateEthAccount(@RequestBody EthAccount ethAccount) throws URISyntaxException {
        log.debug("REST request to update EthAccount : {}", ethAccount);
        if (ethAccount.getId() == null) {
            return createEthAccount(ethAccount);
        }
        EthAccount result = ethAccountService.save(ethAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ethAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eth-accounts : get all the ethAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ethAccounts in body
     */
    @GetMapping("/eth-accounts")
    @Timed
    public ResponseEntity<List<EthAccount>> getAllEthAccounts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EthAccounts");
        Page<EthAccount> page = ethAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eth-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eth-accounts/:id : get the "id" ethAccount.
     *
     * @param id the id of the ethAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ethAccount, or with status 404 (Not Found)
     */
    @GetMapping("/eth-accounts/{id}")
    @Timed
    public ResponseEntity<EthAccount> getEthAccount(@PathVariable Long id) {
        log.debug("REST request to get EthAccount : {}", id);
        EthAccount ethAccount = ethAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ethAccount));
    }

    /**
     * DELETE  /eth-accounts/:id : delete the "id" ethAccount.
     *
     * @param id the id of the ethAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eth-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteEthAccount(@PathVariable Long id) {
        log.debug("REST request to delete EthAccount : {}", id);
        ethAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
