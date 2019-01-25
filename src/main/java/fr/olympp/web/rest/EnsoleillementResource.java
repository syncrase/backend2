package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.EnsoleillementService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.EnsoleillementDTO;
import fr.olympp.service.dto.EnsoleillementCriteria;
import fr.olympp.service.EnsoleillementQueryService;
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
 * REST controller for managing Ensoleillement.
 */
@RestController
@RequestMapping("/api")
public class EnsoleillementResource {

    private final Logger log = LoggerFactory.getLogger(EnsoleillementResource.class);

    private static final String ENTITY_NAME = "backend2Ensoleillement";

    private final EnsoleillementService ensoleillementService;

    private final EnsoleillementQueryService ensoleillementQueryService;

    public EnsoleillementResource(EnsoleillementService ensoleillementService, EnsoleillementQueryService ensoleillementQueryService) {
        this.ensoleillementService = ensoleillementService;
        this.ensoleillementQueryService = ensoleillementQueryService;
    }

    /**
     * POST  /ensoleillements : Create a new ensoleillement.
     *
     * @param ensoleillementDTO the ensoleillementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ensoleillementDTO, or with status 400 (Bad Request) if the ensoleillement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ensoleillements")
    @Timed
    public ResponseEntity<EnsoleillementDTO> createEnsoleillement(@RequestBody EnsoleillementDTO ensoleillementDTO) throws URISyntaxException {
        log.debug("REST request to save Ensoleillement : {}", ensoleillementDTO);
        if (ensoleillementDTO.getId() != null) {
            throw new BadRequestAlertException("A new ensoleillement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnsoleillementDTO result = ensoleillementService.save(ensoleillementDTO);
        return ResponseEntity.created(new URI("/api/ensoleillements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ensoleillements : Updates an existing ensoleillement.
     *
     * @param ensoleillementDTO the ensoleillementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ensoleillementDTO,
     * or with status 400 (Bad Request) if the ensoleillementDTO is not valid,
     * or with status 500 (Internal Server Error) if the ensoleillementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ensoleillements")
    @Timed
    public ResponseEntity<EnsoleillementDTO> updateEnsoleillement(@RequestBody EnsoleillementDTO ensoleillementDTO) throws URISyntaxException {
        log.debug("REST request to update Ensoleillement : {}", ensoleillementDTO);
        if (ensoleillementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnsoleillementDTO result = ensoleillementService.save(ensoleillementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ensoleillementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ensoleillements : get all the ensoleillements.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ensoleillements in body
     */
    @GetMapping("/ensoleillements")
    @Timed
    public ResponseEntity<List<EnsoleillementDTO>> getAllEnsoleillements(EnsoleillementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ensoleillements by criteria: {}", criteria);
        Page<EnsoleillementDTO> page = ensoleillementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ensoleillements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /ensoleillements/count : count all the ensoleillements.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/ensoleillements/count")
    @Timed
    public ResponseEntity<Long> countEnsoleillements(EnsoleillementCriteria criteria) {
        log.debug("REST request to count Ensoleillements by criteria: {}", criteria);
        return ResponseEntity.ok().body(ensoleillementQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /ensoleillements/:id : get the "id" ensoleillement.
     *
     * @param id the id of the ensoleillementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ensoleillementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ensoleillements/{id}")
    @Timed
    public ResponseEntity<EnsoleillementDTO> getEnsoleillement(@PathVariable Long id) {
        log.debug("REST request to get Ensoleillement : {}", id);
        Optional<EnsoleillementDTO> ensoleillementDTO = ensoleillementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ensoleillementDTO);
    }

    /**
     * DELETE  /ensoleillements/:id : delete the "id" ensoleillement.
     *
     * @param id the id of the ensoleillementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ensoleillements/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnsoleillement(@PathVariable Long id) {
        log.debug("REST request to delete Ensoleillement : {}", id);
        ensoleillementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
