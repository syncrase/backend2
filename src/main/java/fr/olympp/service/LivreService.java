package fr.olympp.service;

import fr.olympp.service.dto.LivreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Livre.
 */
public interface LivreService {

    /**
     * Save a livre.
     *
     * @param livreDTO the entity to save
     * @return the persisted entity
     */
    LivreDTO save(LivreDTO livreDTO);

    /**
     * Get all the livres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LivreDTO> findAll(Pageable pageable);
    /**
     * Get all the LivreDTO where Reference is null.
     *
     * @return the list of entities
     */
    List<LivreDTO> findAllWhereReferenceIsNull();


    /**
     * Get the "id" livre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LivreDTO> findOne(Long id);

    /**
     * Delete the "id" livre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
