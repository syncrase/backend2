package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.RecolteService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.RecolteDTO;
import fr.olympp.service.dto.RecolteCriteria;
import fr.olympp.service.RecolteQueryService;
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
 * REST controller for managing Recolte.
 */
@RestController
@RequestMapping("/api")
public class RecolteResource {

    private final Logger log = LoggerFactory.getLogger(RecolteResource.class);

    private static final String ENTITY_NAME = "backend2Recolte";

    private final RecolteService recolteService;

    private final RecolteQueryService recolteQueryService;

    public RecolteResource(RecolteService recolteService, RecolteQueryService recolteQueryService) {
        this.recolteService = recolteService;
        this.recolteQueryService = recolteQueryService;
    }

    /**
     * POST  /recoltes : Create a new recolte.
     *
     * @param recolteDTO the recolteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recolteDTO, or with status 400 (Bad Request) if the recolte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recoltes")
    @Timed
    public ResponseEntity<RecolteDTO> createRecolte(@RequestBody RecolteDTO recolteDTO) throws URISyntaxException {
        log.debug("REST request to save Recolte : {}", recolteDTO);
        if (recolteDTO.getId() != null) {
            throw new BadRequestAlertException("A new recolte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecolteDTO result = recolteService.save(recolteDTO);
        return ResponseEntity.created(new URI("/api/recoltes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recoltes : Updates an existing recolte.
     *
     * @param recolteDTO the recolteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recolteDTO,
     * or with status 400 (Bad Request) if the recolteDTO is not valid,
     * or with status 500 (Internal Server Error) if the recolteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recoltes")
    @Timed
    public ResponseEntity<RecolteDTO> updateRecolte(@RequestBody RecolteDTO recolteDTO) throws URISyntaxException {
        log.debug("REST request to update Recolte : {}", recolteDTO);
        if (recolteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecolteDTO result = recolteService.save(recolteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recolteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recoltes : get all the recoltes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of recoltes in body
     */
    @GetMapping("/recoltes")
    @Timed
    public ResponseEntity<List<RecolteDTO>> getAllRecoltes(RecolteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Recoltes by criteria: {}", criteria);
        Page<RecolteDTO> page = recolteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recoltes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /recoltes/count : count all the recoltes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/recoltes/count")
    @Timed
    public ResponseEntity<Long> countRecoltes(RecolteCriteria criteria) {
        log.debug("REST request to count Recoltes by criteria: {}", criteria);
        return ResponseEntity.ok().body(recolteQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /recoltes/:id : get the "id" recolte.
     *
     * @param id the id of the recolteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recolteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recoltes/{id}")
    @Timed
    public ResponseEntity<RecolteDTO> getRecolte(@PathVariable Long id) {
        log.debug("REST request to get Recolte : {}", id);
        Optional<RecolteDTO> recolteDTO = recolteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recolteDTO);
    }

    /**
     * DELETE  /recoltes/:id : delete the "id" recolte.
     *
     * @param id the id of the recolteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recoltes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecolte(@PathVariable Long id) {
        log.debug("REST request to delete Recolte : {}", id);
        recolteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
