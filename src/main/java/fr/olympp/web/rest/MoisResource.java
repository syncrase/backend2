package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.MoisService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.MoisDTO;
import fr.olympp.service.dto.MoisCriteria;
import fr.olympp.service.MoisQueryService;
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
 * REST controller for managing Mois.
 */
@RestController
@RequestMapping("/api")
public class MoisResource {

    private final Logger log = LoggerFactory.getLogger(MoisResource.class);

    private static final String ENTITY_NAME = "backend2Mois";

    private final MoisService moisService;

    private final MoisQueryService moisQueryService;

    public MoisResource(MoisService moisService, MoisQueryService moisQueryService) {
        this.moisService = moisService;
        this.moisQueryService = moisQueryService;
    }

    /**
     * POST  /mois : Create a new mois.
     *
     * @param moisDTO the moisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moisDTO, or with status 400 (Bad Request) if the mois has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mois")
    @Timed
    public ResponseEntity<MoisDTO> createMois(@RequestBody MoisDTO moisDTO) throws URISyntaxException {
        log.debug("REST request to save Mois : {}", moisDTO);
        if (moisDTO.getId() != null) {
            throw new BadRequestAlertException("A new mois cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoisDTO result = moisService.save(moisDTO);
        return ResponseEntity.created(new URI("/api/mois/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mois : Updates an existing mois.
     *
     * @param moisDTO the moisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moisDTO,
     * or with status 400 (Bad Request) if the moisDTO is not valid,
     * or with status 500 (Internal Server Error) if the moisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mois")
    @Timed
    public ResponseEntity<MoisDTO> updateMois(@RequestBody MoisDTO moisDTO) throws URISyntaxException {
        log.debug("REST request to update Mois : {}", moisDTO);
        if (moisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MoisDTO result = moisService.save(moisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mois : get all the mois.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mois in body
     */
    @GetMapping("/mois")
    @Timed
    public ResponseEntity<List<MoisDTO>> getAllMois(MoisCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Mois by criteria: {}", criteria);
        Page<MoisDTO> page = moisQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mois");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /mois/count : count all the mois.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/mois/count")
    @Timed
    public ResponseEntity<Long> countMois(MoisCriteria criteria) {
        log.debug("REST request to count Mois by criteria: {}", criteria);
        return ResponseEntity.ok().body(moisQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /mois/:id : get the "id" mois.
     *
     * @param id the id of the moisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mois/{id}")
    @Timed
    public ResponseEntity<MoisDTO> getMois(@PathVariable Long id) {
        log.debug("REST request to get Mois : {}", id);
        Optional<MoisDTO> moisDTO = moisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moisDTO);
    }

    /**
     * DELETE  /mois/:id : delete the "id" mois.
     *
     * @param id the id of the moisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mois/{id}")
    @Timed
    public ResponseEntity<Void> deleteMois(@PathVariable Long id) {
        log.debug("REST request to delete Mois : {}", id);
        moisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
