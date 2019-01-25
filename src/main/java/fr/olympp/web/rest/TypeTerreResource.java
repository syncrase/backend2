package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.TypeTerreService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.TypeTerreDTO;
import fr.olympp.service.dto.TypeTerreCriteria;
import fr.olympp.service.TypeTerreQueryService;
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
 * REST controller for managing TypeTerre.
 */
@RestController
@RequestMapping("/api")
public class TypeTerreResource {

    private final Logger log = LoggerFactory.getLogger(TypeTerreResource.class);

    private static final String ENTITY_NAME = "backend2TypeTerre";

    private final TypeTerreService typeTerreService;

    private final TypeTerreQueryService typeTerreQueryService;

    public TypeTerreResource(TypeTerreService typeTerreService, TypeTerreQueryService typeTerreQueryService) {
        this.typeTerreService = typeTerreService;
        this.typeTerreQueryService = typeTerreQueryService;
    }

    /**
     * POST  /type-terres : Create a new typeTerre.
     *
     * @param typeTerreDTO the typeTerreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeTerreDTO, or with status 400 (Bad Request) if the typeTerre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-terres")
    @Timed
    public ResponseEntity<TypeTerreDTO> createTypeTerre(@RequestBody TypeTerreDTO typeTerreDTO) throws URISyntaxException {
        log.debug("REST request to save TypeTerre : {}", typeTerreDTO);
        if (typeTerreDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeTerre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeTerreDTO result = typeTerreService.save(typeTerreDTO);
        return ResponseEntity.created(new URI("/api/type-terres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-terres : Updates an existing typeTerre.
     *
     * @param typeTerreDTO the typeTerreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeTerreDTO,
     * or with status 400 (Bad Request) if the typeTerreDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeTerreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-terres")
    @Timed
    public ResponseEntity<TypeTerreDTO> updateTypeTerre(@RequestBody TypeTerreDTO typeTerreDTO) throws URISyntaxException {
        log.debug("REST request to update TypeTerre : {}", typeTerreDTO);
        if (typeTerreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeTerreDTO result = typeTerreService.save(typeTerreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeTerreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-terres : get all the typeTerres.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of typeTerres in body
     */
    @GetMapping("/type-terres")
    @Timed
    public ResponseEntity<List<TypeTerreDTO>> getAllTypeTerres(TypeTerreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeTerres by criteria: {}", criteria);
        Page<TypeTerreDTO> page = typeTerreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-terres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /type-terres/count : count all the typeTerres.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/type-terres/count")
    @Timed
    public ResponseEntity<Long> countTypeTerres(TypeTerreCriteria criteria) {
        log.debug("REST request to count TypeTerres by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeTerreQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /type-terres/:id : get the "id" typeTerre.
     *
     * @param id the id of the typeTerreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeTerreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-terres/{id}")
    @Timed
    public ResponseEntity<TypeTerreDTO> getTypeTerre(@PathVariable Long id) {
        log.debug("REST request to get TypeTerre : {}", id);
        Optional<TypeTerreDTO> typeTerreDTO = typeTerreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeTerreDTO);
    }

    /**
     * DELETE  /type-terres/:id : delete the "id" typeTerre.
     *
     * @param id the id of the typeTerreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-terres/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeTerre(@PathVariable Long id) {
        log.debug("REST request to delete TypeTerre : {}", id);
        typeTerreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
