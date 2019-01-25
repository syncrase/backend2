package fr.olympp.service;

import fr.olympp.service.dto.ReferenceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Reference.
 */
public interface ReferenceService {

    /**
     * Save a reference.
     *
     * @param referenceDTO the entity to save
     * @return the persisted entity
     */
    ReferenceDTO save(ReferenceDTO referenceDTO);

    /**
     * Get all the references.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReferenceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reference.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReferenceDTO> findOne(Long id);

    /**
     * Delete the "id" reference.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
