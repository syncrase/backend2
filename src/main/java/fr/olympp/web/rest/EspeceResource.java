package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.EspeceService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.EspeceDTO;
import fr.olympp.service.dto.EspeceCriteria;
import fr.olympp.service.EspeceQueryService;
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
 * REST controller for managing Espece.
 */
@RestController
@RequestMapping("/api")
public class EspeceResource {

    private final Logger log = LoggerFactory.getLogger(EspeceResource.class);

    private static final String ENTITY_NAME = "backend2Espece";

    private final EspeceService especeService;

    private final EspeceQueryService especeQueryService;

    public EspeceResource(EspeceService especeService, EspeceQueryService especeQueryService) {
        this.especeService = especeService;
        this.especeQueryService = especeQueryService;
    }

    /**
     * POST  /especes : Create a new espece.
     *
     * @param especeDTO the especeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new especeDTO, or with status 400 (Bad Request) if the espece has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/especes")
    @Timed
    public ResponseEntity<EspeceDTO> createEspece(@RequestBody EspeceDTO especeDTO) throws URISyntaxException {
        log.debug("REST request to save Espece : {}", especeDTO);
        if (especeDTO.getId() != null) {
            throw new BadRequestAlertException("A new espece cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspeceDTO result = especeService.save(especeDTO);
        return ResponseEntity.created(new URI("/api/especes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /especes : Updates an existing espece.
     *
     * @param especeDTO the especeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated especeDTO,
     * or with status 400 (Bad Request) if the especeDTO is not valid,
     * or with status 500 (Internal Server Error) if the especeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/especes")
    @Timed
    public ResponseEntity<EspeceDTO> updateEspece(@RequestBody EspeceDTO especeDTO) throws URISyntaxException {
        log.debug("REST request to update Espece : {}", especeDTO);
        if (especeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EspeceDTO result = especeService.save(especeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, especeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /especes : get all the especes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of especes in body
     */
    @GetMapping("/especes")
    @Timed
    public ResponseEntity<List<EspeceDTO>> getAllEspeces(EspeceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Especes by criteria: {}", criteria);
        Page<EspeceDTO> page = especeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/especes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /especes/count : count all the especes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/especes/count")
    @Timed
    public ResponseEntity<Long> countEspeces(EspeceCriteria criteria) {
        log.debug("REST request to count Especes by criteria: {}", criteria);
        return ResponseEntity.ok().body(especeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /especes/:id : get the "id" espece.
     *
     * @param id the id of the especeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the especeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/especes/{id}")
    @Timed
    public ResponseEntity<EspeceDTO> getEspece(@PathVariable Long id) {
        log.debug("REST request to get Espece : {}", id);
        Optional<EspeceDTO> especeDTO = especeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(especeDTO);
    }

    /**
     * DELETE  /especes/:id : delete the "id" espece.
     *
     * @param id the id of the especeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/especes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEspece(@PathVariable Long id) {
        log.debug("REST request to delete Espece : {}", id);
        especeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
