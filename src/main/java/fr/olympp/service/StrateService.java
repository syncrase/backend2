package fr.olympp.service;

import fr.olympp.service.dto.StrateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Strate.
 */
public interface StrateService {

    /**
     * Save a strate.
     *
     * @param strateDTO the entity to save
     * @return the persisted entity
     */
    StrateDTO save(StrateDTO strateDTO);

    /**
     * Get all the strates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StrateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" strate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StrateDTO> findOne(Long id);

    /**
     * Delete the "id" strate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
