package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.InteractionPlantePlanteService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.InteractionPlantePlanteDTO;
import fr.olympp.service.dto.InteractionPlantePlanteCriteria;
import fr.olympp.service.InteractionPlantePlanteQueryService;
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
 * REST controller for managing InteractionPlantePlante.
 */
@RestController
@RequestMapping("/api")
public class InteractionPlantePlanteResource {

    private final Logger log = LoggerFactory.getLogger(InteractionPlantePlanteResource.class);

    private static final String ENTITY_NAME = "backend2InteractionPlantePlante";

    private final InteractionPlantePlanteService interactionPlantePlanteService;

    private final InteractionPlantePlanteQueryService interactionPlantePlanteQueryService;

    public InteractionPlantePlanteResource(InteractionPlantePlanteService interactionPlantePlanteService, InteractionPlantePlanteQueryService interactionPlantePlanteQueryService) {
        this.interactionPlantePlanteService = interactionPlantePlanteService;
        this.interactionPlantePlanteQueryService = interactionPlantePlanteQueryService;
    }

    /**
     * POST  /interaction-plante-plantes : Create a new interactionPlantePlante.
     *
     * @param interactionPlantePlanteDTO the interactionPlantePlanteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new interactionPlantePlanteDTO, or with status 400 (Bad Request) if the interactionPlantePlante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/interaction-plante-plantes")
    @Timed
    public ResponseEntity<InteractionPlantePlanteDTO> createInteractionPlantePlante(@Valid @RequestBody InteractionPlantePlanteDTO interactionPlantePlanteDTO) throws URISyntaxException {
        log.debug("REST request to save InteractionPlantePlante : {}", interactionPlantePlanteDTO);
        if (interactionPlantePlanteDTO.getId() != null) {
            throw new BadRequestAlertException("A new interactionPlantePlante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InteractionPlantePlanteDTO result = interactionPlantePlanteService.save(interactionPlantePlanteDTO);
        return ResponseEntity.created(new URI("/api/interaction-plante-plantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /interaction-plante-plantes : Updates an existing interactionPlantePlante.
     *
     * @param interactionPlantePlanteDTO the interactionPlantePlanteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated interactionPlantePlanteDTO,
     * or with status 400 (Bad Request) if the interactionPlantePlanteDTO is not valid,
     * or with status 500 (Internal Server Error) if the interactionPlantePlanteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/interaction-plante-plantes")
    @Timed
    public ResponseEntity<InteractionPlantePlanteDTO> updateInteractionPlantePlante(@Valid @RequestBody InteractionPlantePlanteDTO interactionPlantePlanteDTO) throws URISyntaxException {
        log.debug("REST request to update InteractionPlantePlante : {}", interactionPlantePlanteDTO);
        if (interactionPlantePlanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InteractionPlantePlanteDTO result = interactionPlantePlanteService.save(interactionPlantePlanteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, interactionPlantePlanteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /interaction-plante-plantes : get all the interactionPlantePlantes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of interactionPlantePlantes in body
     */
    @GetMapping("/interaction-plante-plantes")
    @Timed
    public ResponseEntity<List<InteractionPlantePlanteDTO>> getAllInteractionPlantePlantes(InteractionPlantePlanteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InteractionPlantePlantes by criteria: {}", criteria);
        Page<InteractionPlantePlanteDTO> page = interactionPlantePlanteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/interaction-plante-plantes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /interaction-plante-plantes/count : count all the interactionPlantePlantes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/interaction-plante-plantes/count")
    @Timed
    public ResponseEntity<Long> countInteractionPlantePlantes(InteractionPlantePlanteCriteria criteria) {
        log.debug("REST request to count InteractionPlantePlantes by criteria: {}", criteria);
        return ResponseEntity.ok().body(interactionPlantePlanteQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /interaction-plante-plantes/:id : get the "id" interactionPlantePlante.
     *
     * @param id the id of the interactionPlantePlanteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the interactionPlantePlanteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/interaction-plante-plantes/{id}")
    @Timed
    public ResponseEntity<InteractionPlantePlanteDTO> getInteractionPlantePlante(@PathVariable Long id) {
        log.debug("REST request to get InteractionPlantePlante : {}", id);
        Optional<InteractionPlantePlanteDTO> interactionPlantePlanteDTO = interactionPlantePlanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interactionPlantePlanteDTO);
    }

    /**
     * DELETE  /interaction-plante-plantes/:id : delete the "id" interactionPlantePlante.
     *
     * @param id the id of the interactionPlantePlanteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/interaction-plante-plantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteInteractionPlantePlante(@PathVariable Long id) {
        log.debug("REST request to delete InteractionPlantePlante : {}", id);
        interactionPlantePlanteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
