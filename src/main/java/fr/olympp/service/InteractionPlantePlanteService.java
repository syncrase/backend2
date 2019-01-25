package fr.olympp.service;

import fr.olympp.service.dto.InteractionPlantePlanteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing InteractionPlantePlante.
 */
public interface InteractionPlantePlanteService {

    /**
     * Save a interactionPlantePlante.
     *
     * @param interactionPlantePlanteDTO the entity to save
     * @return the persisted entity
     */
    InteractionPlantePlanteDTO save(InteractionPlantePlanteDTO interactionPlantePlanteDTO);

    /**
     * Get all the interactionPlantePlantes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InteractionPlantePlanteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" interactionPlantePlante.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InteractionPlantePlanteDTO> findOne(Long id);

    /**
     * Delete the "id" interactionPlantePlante.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
