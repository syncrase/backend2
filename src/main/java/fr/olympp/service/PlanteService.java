package fr.olympp.service;

import fr.olympp.service.dto.PlanteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Plante.
 */
public interface PlanteService {

    /**
     * Save a plante.
     *
     * @param planteDTO the entity to save
     * @return the persisted entity
     */
    PlanteDTO save(PlanteDTO planteDTO);

    /**
     * Get all the plantes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PlanteDTO> findAll(Pageable pageable);

    /**
     * Get all the Plante with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<PlanteDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" plante.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PlanteDTO> findOne(Long id);

    /**
     * Delete the "id" plante.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
