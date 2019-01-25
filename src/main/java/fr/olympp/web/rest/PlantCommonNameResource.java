package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.PlantCommonNameService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.PlantCommonNameDTO;
import fr.olympp.service.dto.PlantCommonNameCriteria;
import fr.olympp.service.PlantCommonNameQueryService;
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
 * REST controller for managing PlantCommonName.
 */
@RestController
@RequestMapping("/api")
public class PlantCommonNameResource {

    private final Logger log = LoggerFactory.getLogger(PlantCommonNameResource.class);

    private static final String ENTITY_NAME = "backend2PlantCommonName";

    private final PlantCommonNameService plantCommonNameService;

    private final PlantCommonNameQueryService plantCommonNameQueryService;

    public PlantCommonNameResource(PlantCommonNameService plantCommonNameService, PlantCommonNameQueryService plantCommonNameQueryService) {
        this.plantCommonNameService = plantCommonNameService;
        this.plantCommonNameQueryService = plantCommonNameQueryService;
    }

    /**
     * POST  /plant-common-names : Create a new plantCommonName.
     *
     * @param plantCommonNameDTO the plantCommonNameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plantCommonNameDTO, or with status 400 (Bad Request) if the plantCommonName has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plant-common-names")
    @Timed
    public ResponseEntity<PlantCommonNameDTO> createPlantCommonName(@RequestBody PlantCommonNameDTO plantCommonNameDTO) throws URISyntaxException {
        log.debug("REST request to save PlantCommonName : {}", plantCommonNameDTO);
        if (plantCommonNameDTO.getId() != null) {
            throw new BadRequestAlertException("A new plantCommonName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlantCommonNameDTO result = plantCommonNameService.save(plantCommonNameDTO);
        return ResponseEntity.created(new URI("/api/plant-common-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plant-common-names : Updates an existing plantCommonName.
     *
     * @param plantCommonNameDTO the plantCommonNameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plantCommonNameDTO,
     * or with status 400 (Bad Request) if the plantCommonNameDTO is not valid,
     * or with status 500 (Internal Server Error) if the plantCommonNameDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plant-common-names")
    @Timed
    public ResponseEntity<PlantCommonNameDTO> updatePlantCommonName(@RequestBody PlantCommonNameDTO plantCommonNameDTO) throws URISyntaxException {
        log.debug("REST request to update PlantCommonName : {}", plantCommonNameDTO);
        if (plantCommonNameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlantCommonNameDTO result = plantCommonNameService.save(plantCommonNameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plantCommonNameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plant-common-names : get all the plantCommonNames.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of plantCommonNames in body
     */
    @GetMapping("/plant-common-names")
    @Timed
    public ResponseEntity<List<PlantCommonNameDTO>> getAllPlantCommonNames(PlantCommonNameCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PlantCommonNames by criteria: {}", criteria);
        Page<PlantCommonNameDTO> page = plantCommonNameQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plant-common-names");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /plant-common-names/count : count all the plantCommonNames.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/plant-common-names/count")
    @Timed
    public ResponseEntity<Long> countPlantCommonNames(PlantCommonNameCriteria criteria) {
        log.debug("REST request to count PlantCommonNames by criteria: {}", criteria);
        return ResponseEntity.ok().body(plantCommonNameQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /plant-common-names/:id : get the "id" plantCommonName.
     *
     * @param id the id of the plantCommonNameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plantCommonNameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/plant-common-names/{id}")
    @Timed
    public ResponseEntity<PlantCommonNameDTO> getPlantCommonName(@PathVariable Long id) {
        log.debug("REST request to get PlantCommonName : {}", id);
        Optional<PlantCommonNameDTO> plantCommonNameDTO = plantCommonNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plantCommonNameDTO);
    }

    /**
     * DELETE  /plant-common-names/:id : delete the "id" plantCommonName.
     *
     * @param id the id of the plantCommonNameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plant-common-names/{id}")
    @Timed
    public ResponseEntity<Void> deletePlantCommonName(@PathVariable Long id) {
        log.debug("REST request to delete PlantCommonName : {}", id);
        plantCommonNameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
