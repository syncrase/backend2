package fr.olympp.service;

import fr.olympp.service.dto.RecolteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Recolte.
 */
public interface RecolteService {

    /**
     * Save a recolte.
     *
     * @param recolteDTO the entity to save
     * @return the persisted entity
     */
    RecolteDTO save(RecolteDTO recolteDTO);

    /**
     * Get all the recoltes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecolteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" recolte.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RecolteDTO> findOne(Long id);

    /**
     * Delete the "id" recolte.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
