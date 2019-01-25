package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.TypeFeuillageService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.TypeFeuillageDTO;
import fr.olympp.service.dto.TypeFeuillageCriteria;
import fr.olympp.service.TypeFeuillageQueryService;
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
 * REST controller for managing TypeFeuillage.
 */
@RestController
@RequestMapping("/api")
public class TypeFeuillageResource {

    private final Logger log = LoggerFactory.getLogger(TypeFeuillageResource.class);

    private static final String ENTITY_NAME = "backend2TypeFeuillage";

    private final TypeFeuillageService typeFeuillageService;

    private final TypeFeuillageQueryService typeFeuillageQueryService;

    public TypeFeuillageResource(TypeFeuillageService typeFeuillageService, TypeFeuillageQueryService typeFeuillageQueryService) {
        this.typeFeuillageService = typeFeuillageService;
        this.typeFeuillageQueryService = typeFeuillageQueryService;
    }

    /**
     * POST  /type-feuillages : Create a new typeFeuillage.
     *
     * @param typeFeuillageDTO the typeFeuillageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeFeuillageDTO, or with status 400 (Bad Request) if the typeFeuillage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-feuillages")
    @Timed
    public ResponseEntity<TypeFeuillageDTO> createTypeFeuillage(@RequestBody TypeFeuillageDTO typeFeuillageDTO) throws URISyntaxException {
        log.debug("REST request to save TypeFeuillage : {}", typeFeuillageDTO);
        if (typeFeuillageDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeFeuillage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeFeuillageDTO result = typeFeuillageService.save(typeFeuillageDTO);
        return ResponseEntity.created(new URI("/api/type-feuillages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-feuillages : Updates an existing typeFeuillage.
     *
     * @param typeFeuillageDTO the typeFeuillageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeFeuillageDTO,
     * or with status 400 (Bad Request) if the typeFeuillageDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeFeuillageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-feuillages")
    @Timed
    public ResponseEntity<TypeFeuillageDTO> updateTypeFeuillage(@RequestBody TypeFeuillageDTO typeFeuillageDTO) throws URISyntaxException {
        log.debug("REST request to update TypeFeuillage : {}", typeFeuillageDTO);
        if (typeFeuillageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeFeuillageDTO result = typeFeuillageService.save(typeFeuillageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeFeuillageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-feuillages : get all the typeFeuillages.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of typeFeuillages in body
     */
    @GetMapping("/type-feuillages")
    @Timed
    public ResponseEntity<List<TypeFeuillageDTO>> getAllTypeFeuillages(TypeFeuillageCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeFeuillages by criteria: {}", criteria);
        Page<TypeFeuillageDTO> page = typeFeuillageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-feuillages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /type-feuillages/count : count all the typeFeuillages.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/type-feuillages/count")
    @Timed
    public ResponseEntity<Long> countTypeFeuillages(TypeFeuillageCriteria criteria) {
        log.debug("REST request to count TypeFeuillages by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeFeuillageQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /type-feuillages/:id : get the "id" typeFeuillage.
     *
     * @param id the id of the typeFeuillageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeFeuillageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-feuillages/{id}")
    @Timed
    public ResponseEntity<TypeFeuillageDTO> getTypeFeuillage(@PathVariable Long id) {
        log.debug("REST request to get TypeFeuillage : {}", id);
        Optional<TypeFeuillageDTO> typeFeuillageDTO = typeFeuillageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeFeuillageDTO);
    }

    /**
     * DELETE  /type-feuillages/:id : delete the "id" typeFeuillage.
     *
     * @param id the id of the typeFeuillageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-feuillages/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeFeuillage(@PathVariable Long id) {
        log.debug("REST request to delete TypeFeuillage : {}", id);
        typeFeuillageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
