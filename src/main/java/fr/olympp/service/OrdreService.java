package fr.olympp.service;

import fr.olympp.service.dto.OrdreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Ordre.
 */
public interface OrdreService {

    /**
     * Save a ordre.
     *
     * @param ordreDTO the entity to save
     * @return the persisted entity
     */
    OrdreDTO save(OrdreDTO ordreDTO);

    /**
     * Get all the ordres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrdreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ordre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrdreDTO> findOne(Long id);

    /**
     * Delete the "id" ordre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
