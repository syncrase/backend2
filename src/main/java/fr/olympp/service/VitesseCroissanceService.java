package fr.olympp.service;

import fr.olympp.service.dto.VitesseCroissanceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing VitesseCroissance.
 */
public interface VitesseCroissanceService {

    /**
     * Save a vitesseCroissance.
     *
     * @param vitesseCroissanceDTO the entity to save
     * @return the persisted entity
     */
    VitesseCroissanceDTO save(VitesseCroissanceDTO vitesseCroissanceDTO);

    /**
     * Get all the vitesseCroissances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VitesseCroissanceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vitesseCroissance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VitesseCroissanceDTO> findOne(Long id);

    /**
     * Delete the "id" vitesseCroissance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
