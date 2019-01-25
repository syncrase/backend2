package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.TypeRacineService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.TypeRacineDTO;
import fr.olympp.service.dto.TypeRacineCriteria;
import fr.olympp.service.TypeRacineQueryService;
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
 * REST controller for managing TypeRacine.
 */
@RestController
@RequestMapping("/api")
public class TypeRacineResource {

    private final Logger log = LoggerFactory.getLogger(TypeRacineResource.class);

    private static final String ENTITY_NAME = "backend2TypeRacine";

    private final TypeRacineService typeRacineService;

    private final TypeRacineQueryService typeRacineQueryService;

    public TypeRacineResource(TypeRacineService typeRacineService, TypeRacineQueryService typeRacineQueryService) {
        this.typeRacineService = typeRacineService;
        this.typeRacineQueryService = typeRacineQueryService;
    }

    /**
     * POST  /type-racines : Create a new typeRacine.
     *
     * @param typeRacineDTO the typeRacineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeRacineDTO, or with status 400 (Bad Request) if the typeRacine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-racines")
    @Timed
    public ResponseEntity<TypeRacineDTO> createTypeRacine(@RequestBody TypeRacineDTO typeRacineDTO) throws URISyntaxException {
        log.debug("REST request to save TypeRacine : {}", typeRacineDTO);
        if (typeRacineDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeRacine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeRacineDTO result = typeRacineService.save(typeRacineDTO);
        return ResponseEntity.created(new URI("/api/type-racines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-racines : Updates an existing typeRacine.
     *
     * @param typeRacineDTO the typeRacineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeRacineDTO,
     * or with status 400 (Bad Request) if the typeRacineDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeRacineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-racines")
    @Timed
    public ResponseEntity<TypeRacineDTO> updateTypeRacine(@RequestBody TypeRacineDTO typeRacineDTO) throws URISyntaxException {
        log.debug("REST request to update TypeRacine : {}", typeRacineDTO);
        if (typeRacineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeRacineDTO result = typeRacineService.save(typeRacineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeRacineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-racines : get all the typeRacines.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of typeRacines in body
     */
    @GetMapping("/type-racines")
    @Timed
    public ResponseEntity<List<TypeRacineDTO>> getAllTypeRacines(TypeRacineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeRacines by criteria: {}", criteria);
        Page<TypeRacineDTO> page = typeRacineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-racines");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /type-racines/count : count all the typeRacines.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/type-racines/count")
    @Timed
    public ResponseEntity<Long> countTypeRacines(TypeRacineCriteria criteria) {
        log.debug("REST request to count TypeRacines by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeRacineQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /type-racines/:id : get the "id" typeRacine.
     *
     * @param id the id of the typeRacineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeRacineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-racines/{id}")
    @Timed
    public ResponseEntity<TypeRacineDTO> getTypeRacine(@PathVariable Long id) {
        log.debug("REST request to get TypeRacine : {}", id);
        Optional<TypeRacineDTO> typeRacineDTO = typeRacineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeRacineDTO);
    }

    /**
     * DELETE  /type-racines/:id : delete the "id" typeRacine.
     *
     * @param id the id of the typeRacineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-racines/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeRacine(@PathVariable Long id) {
        log.debug("REST request to delete TypeRacine : {}", id);
        typeRacineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
