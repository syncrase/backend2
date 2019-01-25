package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.StrateService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.StrateDTO;
import fr.olympp.service.dto.StrateCriteria;
import fr.olympp.service.StrateQueryService;
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
 * REST controller for managing Strate.
 */
@RestController
@RequestMapping("/api")
public class StrateResource {

    private final Logger log = LoggerFactory.getLogger(StrateResource.class);

    private static final String ENTITY_NAME = "backend2Strate";

    private final StrateService strateService;

    private final StrateQueryService strateQueryService;

    public StrateResource(StrateService strateService, StrateQueryService strateQueryService) {
        this.strateService = strateService;
        this.strateQueryService = strateQueryService;
    }

    /**
     * POST  /strates : Create a new strate.
     *
     * @param strateDTO the strateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new strateDTO, or with status 400 (Bad Request) if the strate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/strates")
    @Timed
    public ResponseEntity<StrateDTO> createStrate(@RequestBody StrateDTO strateDTO) throws URISyntaxException {
        log.debug("REST request to save Strate : {}", strateDTO);
        if (strateDTO.getId() != null) {
            throw new BadRequestAlertException("A new strate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StrateDTO result = strateService.save(strateDTO);
        return ResponseEntity.created(new URI("/api/strates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /strates : Updates an existing strate.
     *
     * @param strateDTO the strateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated strateDTO,
     * or with status 400 (Bad Request) if the strateDTO is not valid,
     * or with status 500 (Internal Server Error) if the strateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/strates")
    @Timed
    public ResponseEntity<StrateDTO> updateStrate(@RequestBody StrateDTO strateDTO) throws URISyntaxException {
        log.debug("REST request to update Strate : {}", strateDTO);
        if (strateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StrateDTO result = strateService.save(strateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, strateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /strates : get all the strates.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of strates in body
     */
    @GetMapping("/strates")
    @Timed
    public ResponseEntity<List<StrateDTO>> getAllStrates(StrateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Strates by criteria: {}", criteria);
        Page<StrateDTO> page = strateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/strates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /strates/count : count all the strates.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/strates/count")
    @Timed
    public ResponseEntity<Long> countStrates(StrateCriteria criteria) {
        log.debug("REST request to count Strates by criteria: {}", criteria);
        return ResponseEntity.ok().body(strateQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /strates/:id : get the "id" strate.
     *
     * @param id the id of the strateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the strateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/strates/{id}")
    @Timed
    public ResponseEntity<StrateDTO> getStrate(@PathVariable Long id) {
        log.debug("REST request to get Strate : {}", id);
        Optional<StrateDTO> strateDTO = strateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(strateDTO);
    }

    /**
     * DELETE  /strates/:id : delete the "id" strate.
     *
     * @param id the id of the strateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/strates/{id}")
    @Timed
    public ResponseEntity<Void> deleteStrate(@PathVariable Long id) {
        log.debug("REST request to delete Strate : {}", id);
        strateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
