package fr.olympp.service;

import fr.olympp.service.dto.TypeRacineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TypeRacine.
 */
public interface TypeRacineService {

    /**
     * Save a typeRacine.
     *
     * @param typeRacineDTO the entity to save
     * @return the persisted entity
     */
    TypeRacineDTO save(TypeRacineDTO typeRacineDTO);

    /**
     * Get all the typeRacines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TypeRacineDTO> findAll(Pageable pageable);


    /**
     * Get the "id" typeRacine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TypeRacineDTO> findOne(Long id);

    /**
     * Delete the "id" typeRacine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
