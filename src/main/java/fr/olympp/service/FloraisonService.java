package fr.olympp.service;

import fr.olympp.service.dto.FloraisonDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Floraison.
 */
public interface FloraisonService {

    /**
     * Save a floraison.
     *
     * @param floraisonDTO the entity to save
     * @return the persisted entity
     */
    FloraisonDTO save(FloraisonDTO floraisonDTO);

    /**
     * Get all the floraisons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FloraisonDTO> findAll(Pageable pageable);


    /**
     * Get the "id" floraison.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FloraisonDTO> findOne(Long id);

    /**
     * Delete the "id" floraison.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
