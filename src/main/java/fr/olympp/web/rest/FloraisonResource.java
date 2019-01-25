package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.FloraisonService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.FloraisonDTO;
import fr.olympp.service.dto.FloraisonCriteria;
import fr.olympp.service.FloraisonQueryService;
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
 * REST controller for managing Floraison.
 */
@RestController
@RequestMapping("/api")
public class FloraisonResource {

    private final Logger log = LoggerFactory.getLogger(FloraisonResource.class);

    private static final String ENTITY_NAME = "backend2Floraison";

    private final FloraisonService floraisonService;

    private final FloraisonQueryService floraisonQueryService;

    public FloraisonResource(FloraisonService floraisonService, FloraisonQueryService floraisonQueryService) {
        this.floraisonService = floraisonService;
        this.floraisonQueryService = floraisonQueryService;
    }

    /**
     * POST  /floraisons : Create a new floraison.
     *
     * @param floraisonDTO the floraisonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new floraisonDTO, or with status 400 (Bad Request) if the floraison has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/floraisons")
    @Timed
    public ResponseEntity<FloraisonDTO> createFloraison(@RequestBody FloraisonDTO floraisonDTO) throws URISyntaxException {
        log.debug("REST request to save Floraison : {}", floraisonDTO);
        if (floraisonDTO.getId() != null) {
            throw new BadRequestAlertException("A new floraison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FloraisonDTO result = floraisonService.save(floraisonDTO);
        return ResponseEntity.created(new URI("/api/floraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /floraisons : Updates an existing floraison.
     *
     * @param floraisonDTO the floraisonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated floraisonDTO,
     * or with status 400 (Bad Request) if the floraisonDTO is not valid,
     * or with status 500 (Internal Server Error) if the floraisonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/floraisons")
    @Timed
    public ResponseEntity<FloraisonDTO> updateFloraison(@RequestBody FloraisonDTO floraisonDTO) throws URISyntaxException {
        log.debug("REST request to update Floraison : {}", floraisonDTO);
        if (floraisonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FloraisonDTO result = floraisonService.save(floraisonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, floraisonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /floraisons : get all the floraisons.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of floraisons in body
     */
    @GetMapping("/floraisons")
    @Timed
    public ResponseEntity<List<FloraisonDTO>> getAllFloraisons(FloraisonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Floraisons by criteria: {}", criteria);
        Page<FloraisonDTO> page = floraisonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/floraisons");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /floraisons/count : count all the floraisons.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/floraisons/count")
    @Timed
    public ResponseEntity<Long> countFloraisons(FloraisonCriteria criteria) {
        log.debug("REST request to count Floraisons by criteria: {}", criteria);
        return ResponseEntity.ok().body(floraisonQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /floraisons/:id : get the "id" floraison.
     *
     * @param id the id of the floraisonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the floraisonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/floraisons/{id}")
    @Timed
    public ResponseEntity<FloraisonDTO> getFloraison(@PathVariable Long id) {
        log.debug("REST request to get Floraison : {}", id);
        Optional<FloraisonDTO> floraisonDTO = floraisonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(floraisonDTO);
    }

    /**
     * DELETE  /floraisons/:id : delete the "id" floraison.
     *
     * @param id the id of the floraisonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/floraisons/{id}")
    @Timed
    public ResponseEntity<Void> deleteFloraison(@PathVariable Long id) {
        log.debug("REST request to delete Floraison : {}", id);
        floraisonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
