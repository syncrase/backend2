package fr.olympp.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.olympp.service.LivreService;
import fr.olympp.web.rest.errors.BadRequestAlertException;
import fr.olympp.web.rest.util.HeaderUtil;
import fr.olympp.web.rest.util.PaginationUtil;
import fr.olympp.service.dto.LivreDTO;
import fr.olympp.service.dto.LivreCriteria;
import fr.olympp.service.LivreQueryService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Livre.
 */
@RestController
@RequestMapping("/api")
public class LivreResource {

    private final Logger log = LoggerFactory.getLogger(LivreResource.class);

    private static final String ENTITY_NAME = "backend2Livre";

    private final LivreService livreService;

    private final LivreQueryService livreQueryService;

    public LivreResource(LivreService livreService, LivreQueryService livreQueryService) {
        this.livreService = livreService;
        this.livreQueryService = livreQueryService;
    }

    /**
     * POST  /livres : Create a new livre.
     *
     * @param livreDTO the livreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new livreDTO, or with status 400 (Bad Request) if the livre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/livres")
    @Timed
    public ResponseEntity<LivreDTO> createLivre(@RequestBody LivreDTO livreDTO) throws URISyntaxException {
        log.debug("REST request to save Livre : {}", livreDTO);
        if (livreDTO.getId() != null) {
            throw new BadRequestAlertException("A new livre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LivreDTO result = livreService.save(livreDTO);
        return ResponseEntity.created(new URI("/api/livres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /livres : Updates an existing livre.
     *
     * @param livreDTO the livreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated livreDTO,
     * or with status 400 (Bad Request) if the livreDTO is not valid,
     * or with status 500 (Internal Server Error) if the livreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/livres")
    @Timed
    public ResponseEntity<LivreDTO> updateLivre(@RequestBody LivreDTO livreDTO) throws URISyntaxException {
        log.debug("REST request to update Livre : {}", livreDTO);
        if (livreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LivreDTO result = livreService.save(livreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, livreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /livres : get all the livres.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of livres in body
     */
    @GetMapping("/livres")
    @Timed
    public ResponseEntity<List<LivreDTO>> getAllLivres(LivreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Livres by criteria: {}", criteria);
        Page<LivreDTO> page = livreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/livres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /livres/count : count all the livres.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/livres/count")
    @Timed
    public ResponseEntity<Long> countLivres(LivreCriteria criteria) {
        log.debug("REST request to count Livres by criteria: {}", criteria);
        return ResponseEntity.ok().body(livreQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /livres/:id : get the "id" livre.
     *
     * @param id the id of the livreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the livreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/livres/{id}")
    @Timed
    public ResponseEntity<LivreDTO> getLivre(@PathVariable Long id) {
        log.debug("REST request to get Livre : {}", id);
        Optional<LivreDTO> livreDTO = livreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livreDTO);
    }

    /**
     * DELETE  /livres/:id : delete the "id" livre.
     *
     * @param id the id of the livreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/livres/{id}")
    @Timed
    public ResponseEntity<Void> deleteLivre(@PathVariable Long id) {
        log.debug("REST request to delete Livre : {}", id);
        livreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
