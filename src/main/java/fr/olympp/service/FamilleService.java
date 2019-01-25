package fr.olympp.service;

import fr.olympp.service.dto.FamilleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Famille.
 */
public interface FamilleService {

    /**
     * Save a famille.
     *
     * @param familleDTO the entity to save
     * @return the persisted entity
     */
    FamilleDTO save(FamilleDTO familleDTO);

    /**
     * Get all the familles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FamilleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" famille.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FamilleDTO> findOne(Long id);

    /**
     * Delete the "id" famille.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
