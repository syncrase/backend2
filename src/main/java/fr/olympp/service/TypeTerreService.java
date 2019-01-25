package fr.olympp.service;

import fr.olympp.service.dto.TypeTerreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TypeTerre.
 */
public interface TypeTerreService {

    /**
     * Save a typeTerre.
     *
     * @param typeTerreDTO the entity to save
     * @return the persisted entity
     */
    TypeTerreDTO save(TypeTerreDTO typeTerreDTO);

    /**
     * Get all the typeTerres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TypeTerreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" typeTerre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TypeTerreDTO> findOne(Long id);

    /**
     * Delete the "id" typeTerre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
