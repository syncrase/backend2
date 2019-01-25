package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.ClassificationCronquistService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.ClassificationCronquistDTO;
import fr.olympp.service.dto.ClassificationCronquistCriteria;
import fr.olympp.service.ClassificationCronquistQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClassificationCronquist.
 */
@RestController
@RequestMapping("/api")
public class ClassificationCronquistResource {

    private final Logger log = LoggerFactory.getLogger(ClassificationCronquistResource.class);

    private static final String ENTITY_NAME = "backend2ClassificationCronquist";

    private final ClassificationCronquistService classificationCronquistService;

    private final ClassificationCronquistQueryService classificationCronquistQueryService;

    public ClassificationCronquistResource(ClassificationCronquistService classificationCronquistService, ClassificationCronquistQueryService classificationCronquistQueryService) {
        this.classificationCronquistService = classificationCronquistService;
        this.classificationCronquistQueryService = classificationCronquistQueryService;
    }

    /**
     * POST  /classification-cronquists : Create a new classificationCronquist.
     *
     * @param classificationCronquistDTO the classificationCronquistDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classificationCronquistDTO, or with status 400 (Bad Request) if the classificationCronquist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classification-cronquists")
    @Timed
    public ResponseEntity<ClassificationCronquistDTO> createClassificationCronquist(@Valid @RequestBody ClassificationCronquistDTO classificationCronquistDTO) throws URISyntaxException {
        log.debug("REST request to save ClassificationCronquist : {}", classificationCronquistDTO);
        if (classificationCronquistDTO.getId() != null) {
            throw new BadRequestAlertException("A new classificationCronquist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassificationCronquistDTO result = classificationCronquistService.save(classificationCronquistDTO);
        return ResponseEntity.created(new URI("/api/classification-cronquists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classification-cronquists : Updates an existing classificationCronquist.
     *
     * @param classificationCronquistDTO the classificationCronquistDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classificationCronquistDTO,
     * or with status 400 (Bad Request) if the classificationCronquistDTO is not valid,
     * or with status 500 (Internal Server Error) if the classificationCronquistDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/classification-cronquists")
    @Timed
    public ResponseEntity<ClassificationCronquistDTO> updateClassificationCronquist(@Valid @RequestBody ClassificationCronquistDTO classificationCronquistDTO) throws URISyntaxException {
        log.debug("REST request to update ClassificationCronquist : {}", classificationCronquistDTO);
        if (classificationCronquistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassificationCronquistDTO result = classificationCronquistService.save(classificationCronquistDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classificationCronquistDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classification-cronquists : get all the classificationCronquists.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classificationCronquists in body
     */
    @GetMapping("/classification-cronquists")
    @Timed
    public ResponseEntity<List<ClassificationCronquistDTO>> getAllClassificationCronquists(ClassificationCronquistCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClassificationCronquists by criteria: {}", criteria);
        Page<ClassificationCronquistDTO> page = classificationCronquistQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/classification-cronquists");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /classification-cronquists/count : count all the classificationCronquists.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/classification-cronquists/count")
    @Timed
    public ResponseEntity<Long> countClassificationCronquists(ClassificationCronquistCriteria criteria) {
        log.debug("REST request to count ClassificationCronquists by criteria: {}", criteria);
        return ResponseEntity.ok().body(classificationCronquistQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /classification-cronquists/:id : get the "id" classificationCronquist.
     *
     * @param id the id of the classificationCronquistDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classificationCronquistDTO, or with status 404 (Not Found)
     */
    @GetMapping("/classification-cronquists/{id}")
    @Timed
    public ResponseEntity<ClassificationCronquistDTO> getClassificationCronquist(@PathVariable Long id) {
        log.debug("REST request to get ClassificationCronquist : {}", id);
        Optional<ClassificationCronquistDTO> classificationCronquistDTO = classificationCronquistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classificationCronquistDTO);
    }

    /**
     * DELETE  /classification-cronquists/:id : delete the "id" classificationCronquist.
     *
     * @param id the id of the classificationCronquistDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classification-cronquists/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassificationCronquist(@PathVariable Long id) {
        log.debug("REST request to delete ClassificationCronquist : {}", id);
        classificationCronquistService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
