package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.VitesseCroissanceService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.VitesseCroissanceDTO;
import fr.olympp.service.dto.VitesseCroissanceCriteria;
import fr.olympp.service.VitesseCroissanceQueryService;
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
 * REST controller for managing VitesseCroissance.
 */
@RestController
@RequestMapping("/api")
public class VitesseCroissanceResource {

    private final Logger log = LoggerFactory.getLogger(VitesseCroissanceResource.class);

    private static final String ENTITY_NAME = "backend2VitesseCroissance";

    private final VitesseCroissanceService vitesseCroissanceService;

    private final VitesseCroissanceQueryService vitesseCroissanceQueryService;

    public VitesseCroissanceResource(VitesseCroissanceService vitesseCroissanceService, VitesseCroissanceQueryService vitesseCroissanceQueryService) {
        this.vitesseCroissanceService = vitesseCroissanceService;
        this.vitesseCroissanceQueryService = vitesseCroissanceQueryService;
    }

    /**
     * POST  /vitesse-croissances : Create a new vitesseCroissance.
     *
     * @param vitesseCroissanceDTO the vitesseCroissanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vitesseCroissanceDTO, or with status 400 (Bad Request) if the vitesseCroissance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vitesse-croissances")
    @Timed
    public ResponseEntity<VitesseCroissanceDTO> createVitesseCroissance(@RequestBody VitesseCroissanceDTO vitesseCroissanceDTO) throws URISyntaxException {
        log.debug("REST request to save VitesseCroissance : {}", vitesseCroissanceDTO);
        if (vitesseCroissanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new vitesseCroissance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VitesseCroissanceDTO result = vitesseCroissanceService.save(vitesseCroissanceDTO);
        return ResponseEntity.created(new URI("/api/vitesse-croissances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vitesse-croissances : Updates an existing vitesseCroissance.
     *
     * @param vitesseCroissanceDTO the vitesseCroissanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vitesseCroissanceDTO,
     * or with status 400 (Bad Request) if the vitesseCroissanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the vitesseCroissanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vitesse-croissances")
    @Timed
    public ResponseEntity<VitesseCroissanceDTO> updateVitesseCroissance(@RequestBody VitesseCroissanceDTO vitesseCroissanceDTO) throws URISyntaxException {
        log.debug("REST request to update VitesseCroissance : {}", vitesseCroissanceDTO);
        if (vitesseCroissanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VitesseCroissanceDTO result = vitesseCroissanceService.save(vitesseCroissanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vitesseCroissanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vitesse-croissances : get all the vitesseCroissances.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of vitesseCroissances in body
     */
    @GetMapping("/vitesse-croissances")
    @Timed
    public ResponseEntity<List<VitesseCroissanceDTO>> getAllVitesseCroissances(VitesseCroissanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VitesseCroissances by criteria: {}", criteria);
        Page<VitesseCroissanceDTO> page = vitesseCroissanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vitesse-croissances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /vitesse-croissances/count : count all the vitesseCroissances.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/vitesse-croissances/count")
    @Timed
    public ResponseEntity<Long> countVitesseCroissances(VitesseCroissanceCriteria criteria) {
        log.debug("REST request to count VitesseCroissances by criteria: {}", criteria);
        return ResponseEntity.ok().body(vitesseCroissanceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /vitesse-croissances/:id : get the "id" vitesseCroissance.
     *
     * @param id the id of the vitesseCroissanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vitesseCroissanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vitesse-croissances/{id}")
    @Timed
    public ResponseEntity<VitesseCroissanceDTO> getVitesseCroissance(@PathVariable Long id) {
        log.debug("REST request to get VitesseCroissance : {}", id);
        Optional<VitesseCroissanceDTO> vitesseCroissanceDTO = vitesseCroissanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vitesseCroissanceDTO);
    }

    /**
     * DELETE  /vitesse-croissances/:id : delete the "id" vitesseCroissance.
     *
     * @param id the id of the vitesseCroissanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vitesse-croissances/{id}")
    @Timed
    public ResponseEntity<Void> deleteVitesseCroissance(@PathVariable Long id) {
        log.debug("REST request to delete VitesseCroissance : {}", id);
        vitesseCroissanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
