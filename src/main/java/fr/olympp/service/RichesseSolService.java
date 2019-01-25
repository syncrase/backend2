package fr.olympp.service;

import fr.olympp.service.dto.RichesseSolDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RichesseSol.
 */
public interface RichesseSolService {

    /**
     * Save a richesseSol.
     *
     * @param richesseSolDTO the entity to save
     * @return the persisted entity
     */
    RichesseSolDTO save(RichesseSolDTO richesseSolDTO);

    /**
     * Get all the richesseSols.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RichesseSolDTO> findAll(Pageable pageable);


    /**
     * Get the "id" richesseSol.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RichesseSolDTO> findOne(Long id);

    /**
     * Delete the "id" richesseSol.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
