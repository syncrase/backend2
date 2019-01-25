package fr.olympp.service;

import fr.olympp.service.dto.EspeceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Espece.
 */
public interface EspeceService {

    /**
     * Save a espece.
     *
     * @param especeDTO the entity to save
     * @return the persisted entity
     */
    EspeceDTO save(EspeceDTO especeDTO);

    /**
     * Get all the especes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EspeceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" espece.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EspeceDTO> findOne(Long id);

    /**
     * Delete the "id" espece.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
