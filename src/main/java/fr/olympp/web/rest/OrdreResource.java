package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.OrdreService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.OrdreDTO;
import fr.olympp.service.dto.OrdreCriteria;
import fr.olympp.service.OrdreQueryService;
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
 * REST controller for managing Ordre.
 */
@RestController
@RequestMapping("/api")
public class OrdreResource {

    private final Logger log = LoggerFactory.getLogger(OrdreResource.class);

    private static final String ENTITY_NAME = "backend2Ordre";

    private final OrdreService ordreService;

    private final OrdreQueryService ordreQueryService;

    public OrdreResource(OrdreService ordreService, OrdreQueryService ordreQueryService) {
        this.ordreService = ordreService;
        this.ordreQueryService = ordreQueryService;
    }

    /**
     * POST  /ordres : Create a new ordre.
     *
     * @param ordreDTO the ordreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordreDTO, or with status 400 (Bad Request) if the ordre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordres")
    @Timed
    public ResponseEntity<OrdreDTO> createOrdre(@RequestBody OrdreDTO ordreDTO) throws URISyntaxException {
        log.debug("REST request to save Ordre : {}", ordreDTO);
        if (ordreDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdreDTO result = ordreService.save(ordreDTO);
        return ResponseEntity.created(new URI("/api/ordres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordres : Updates an existing ordre.
     *
     * @param ordreDTO the ordreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordreDTO,
     * or with status 400 (Bad Request) if the ordreDTO is not valid,
     * or with status 500 (Internal Server Error) if the ordreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordres")
    @Timed
    public ResponseEntity<OrdreDTO> updateOrdre(@RequestBody OrdreDTO ordreDTO) throws URISyntaxException {
        log.debug("REST request to update Ordre : {}", ordreDTO);
        if (ordreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrdreDTO result = ordreService.save(ordreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordres : get all the ordres.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ordres in body
     */
    @GetMapping("/ordres")
    @Timed
    public ResponseEntity<List<OrdreDTO>> getAllOrdres(OrdreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ordres by criteria: {}", criteria);
        Page<OrdreDTO> page = ordreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /ordres/count : count all the ordres.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/ordres/count")
    @Timed
    public ResponseEntity<Long> countOrdres(OrdreCriteria criteria) {
        log.debug("REST request to count Ordres by criteria: {}", criteria);
        return ResponseEntity.ok().body(ordreQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /ordres/:id : get the "id" ordre.
     *
     * @param id the id of the ordreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ordres/{id}")
    @Timed
    public ResponseEntity<OrdreDTO> getOrdre(@PathVariable Long id) {
        log.debug("REST request to get Ordre : {}", id);
        Optional<OrdreDTO> ordreDTO = ordreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordreDTO);
    }

    /**
     * DELETE  /ordres/:id : delete the "id" ordre.
     *
     * @param id the id of the ordreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordres/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdre(@PathVariable Long id) {
        log.debug("REST request to delete Ordre : {}", id);
        ordreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
