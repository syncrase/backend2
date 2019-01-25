package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.RichesseSolService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.RichesseSolDTO;
import fr.olympp.service.dto.RichesseSolCriteria;
import fr.olympp.service.RichesseSolQueryService;
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
 * REST controller for managing RichesseSol.
 */
@RestController
@RequestMapping("/api")
public class RichesseSolResource {

    private final Logger log = LoggerFactory.getLogger(RichesseSolResource.class);

    private static final String ENTITY_NAME = "backend2RichesseSol";

    private final RichesseSolService richesseSolService;

    private final RichesseSolQueryService richesseSolQueryService;

    public RichesseSolResource(RichesseSolService richesseSolService, RichesseSolQueryService richesseSolQueryService) {
        this.richesseSolService = richesseSolService;
        this.richesseSolQueryService = richesseSolQueryService;
    }

    /**
     * POST  /richesse-sols : Create a new richesseSol.
     *
     * @param richesseSolDTO the richesseSolDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new richesseSolDTO, or with status 400 (Bad Request) if the richesseSol has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/richesse-sols")
    @Timed
    public ResponseEntity<RichesseSolDTO> createRichesseSol(@RequestBody RichesseSolDTO richesseSolDTO) throws URISyntaxException {
        log.debug("REST request to save RichesseSol : {}", richesseSolDTO);
        if (richesseSolDTO.getId() != null) {
            throw new BadRequestAlertException("A new richesseSol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RichesseSolDTO result = richesseSolService.save(richesseSolDTO);
        return ResponseEntity.created(new URI("/api/richesse-sols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /richesse-sols : Updates an existing richesseSol.
     *
     * @param richesseSolDTO the richesseSolDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated richesseSolDTO,
     * or with status 400 (Bad Request) if the richesseSolDTO is not valid,
     * or with status 500 (Internal Server Error) if the richesseSolDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/richesse-sols")
    @Timed
    public ResponseEntity<RichesseSolDTO> updateRichesseSol(@RequestBody RichesseSolDTO richesseSolDTO) throws URISyntaxException {
        log.debug("REST request to update RichesseSol : {}", richesseSolDTO);
        if (richesseSolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RichesseSolDTO result = richesseSolService.save(richesseSolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, richesseSolDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /richesse-sols : get all the richesseSols.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of richesseSols in body
     */
    @GetMapping("/richesse-sols")
    @Timed
    public ResponseEntity<List<RichesseSolDTO>> getAllRichesseSols(RichesseSolCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RichesseSols by criteria: {}", criteria);
        Page<RichesseSolDTO> page = richesseSolQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/richesse-sols");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /richesse-sols/count : count all the richesseSols.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/richesse-sols/count")
    @Timed
    public ResponseEntity<Long> countRichesseSols(RichesseSolCriteria criteria) {
        log.debug("REST request to count RichesseSols by criteria: {}", criteria);
        return ResponseEntity.ok().body(richesseSolQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /richesse-sols/:id : get the "id" richesseSol.
     *
     * @param id the id of the richesseSolDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the richesseSolDTO, or with status 404 (Not Found)
     */
    @GetMapping("/richesse-sols/{id}")
    @Timed
    public ResponseEntity<RichesseSolDTO> getRichesseSol(@PathVariable Long id) {
        log.debug("REST request to get RichesseSol : {}", id);
        Optional<RichesseSolDTO> richesseSolDTO = richesseSolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(richesseSolDTO);
    }

    /**
     * DELETE  /richesse-sols/:id : delete the "id" richesseSol.
     *
     * @param id the id of the richesseSolDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/richesse-sols/{id}")
    @Timed
    public ResponseEntity<Void> deleteRichesseSol(@PathVariable Long id) {
        log.debug("REST request to delete RichesseSol : {}", id);
        richesseSolService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
