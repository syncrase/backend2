package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.FamilleService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.FamilleDTO;
import fr.olympp.service.dto.FamilleCriteria;
import fr.olympp.service.FamilleQueryService;
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
 * REST controller for managing Famille.
 */
@RestController
@RequestMapping("/api")
public class FamilleResource {

    private final Logger log = LoggerFactory.getLogger(FamilleResource.class);

    private static final String ENTITY_NAME = "backend2Famille";

    private final FamilleService familleService;

    private final FamilleQueryService familleQueryService;

    public FamilleResource(FamilleService familleService, FamilleQueryService familleQueryService) {
        this.familleService = familleService;
        this.familleQueryService = familleQueryService;
    }

    /**
     * POST  /familles : Create a new famille.
     *
     * @param familleDTO the familleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new familleDTO, or with status 400 (Bad Request) if the famille has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/familles")
    @Timed
    public ResponseEntity<FamilleDTO> createFamille(@RequestBody FamilleDTO familleDTO) throws URISyntaxException {
        log.debug("REST request to save Famille : {}", familleDTO);
        if (familleDTO.getId() != null) {
            throw new BadRequestAlertException("A new famille cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilleDTO result = familleService.save(familleDTO);
        return ResponseEntity.created(new URI("/api/familles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /familles : Updates an existing famille.
     *
     * @param familleDTO the familleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated familleDTO,
     * or with status 400 (Bad Request) if the familleDTO is not valid,
     * or with status 500 (Internal Server Error) if the familleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/familles")
    @Timed
    public ResponseEntity<FamilleDTO> updateFamille(@RequestBody FamilleDTO familleDTO) throws URISyntaxException {
        log.debug("REST request to update Famille : {}", familleDTO);
        if (familleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamilleDTO result = familleService.save(familleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, familleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /familles : get all the familles.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of familles in body
     */
    @GetMapping("/familles")
    @Timed
    public ResponseEntity<List<FamilleDTO>> getAllFamilles(FamilleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Familles by criteria: {}", criteria);
        Page<FamilleDTO> page = familleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/familles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /familles/count : count all the familles.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/familles/count")
    @Timed
    public ResponseEntity<Long> countFamilles(FamilleCriteria criteria) {
        log.debug("REST request to count Familles by criteria: {}", criteria);
        return ResponseEntity.ok().body(familleQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /familles/:id : get the "id" famille.
     *
     * @param id the id of the familleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the familleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/familles/{id}")
    @Timed
    public ResponseEntity<FamilleDTO> getFamille(@PathVariable Long id) {
        log.debug("REST request to get Famille : {}", id);
        Optional<FamilleDTO> familleDTO = familleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familleDTO);
    }

    /**
     * DELETE  /familles/:id : delete the "id" famille.
     *
     * @param id the id of the familleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/familles/{id}")
    @Timed
    public ResponseEntity<Void> deleteFamille(@PathVariable Long id) {
        log.debug("REST request to delete Famille : {}", id);
        familleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
