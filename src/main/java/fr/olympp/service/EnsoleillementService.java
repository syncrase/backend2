package fr.olympp.service;

import fr.olympp.service.dto.EnsoleillementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Ensoleillement.
 */
public interface EnsoleillementService {

    /**
     * Save a ensoleillement.
     *
     * @param ensoleillementDTO the entity to save
     * @return the persisted entity
     */
    EnsoleillementDTO save(EnsoleillementDTO ensoleillementDTO);

    /**
     * Get all the ensoleillements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EnsoleillementDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ensoleillement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EnsoleillementDTO> findOne(Long id);

    /**
     * Delete the "id" ensoleillement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
