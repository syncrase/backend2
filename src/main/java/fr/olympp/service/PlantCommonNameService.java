package fr.olympp.service;

import fr.olympp.service.dto.PlantCommonNameDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PlantCommonName.
 */
public interface PlantCommonNameService {

    /**
     * Save a plantCommonName.
     *
     * @param plantCommonNameDTO the entity to save
     * @return the persisted entity
     */
    PlantCommonNameDTO save(PlantCommonNameDTO plantCommonNameDTO);

    /**
     * Get all the plantCommonNames.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PlantCommonNameDTO> findAll(Pageable pageable);


    /**
     * Get the "id" plantCommonName.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PlantCommonNameDTO> findOne(Long id);

    /**
     * Delete the "id" plantCommonName.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
