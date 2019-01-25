package fr.olympp.service;

import fr.olympp.service.dto.TypeFeuillageDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TypeFeuillage.
 */
public interface TypeFeuillageService {

    /**
     * Save a typeFeuillage.
     *
     * @param typeFeuillageDTO the entity to save
     * @return the persisted entity
     */
    TypeFeuillageDTO save(TypeFeuillageDTO typeFeuillageDTO);

    /**
     * Get all the typeFeuillages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TypeFeuillageDTO> findAll(Pageable pageable);


    /**
     * Get the "id" typeFeuillage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TypeFeuillageDTO> findOne(Long id);

    /**
     * Delete the "id" typeFeuillage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
