package fr.olympp.service;

import fr.olympp.service.dto.PageWebDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PageWeb.
 */
public interface PageWebService {

    /**
     * Save a pageWeb.
     *
     * @param pageWebDTO the entity to save
     * @return the persisted entity
     */
    PageWebDTO save(PageWebDTO pageWebDTO);

    /**
     * Get all the pageWebs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PageWebDTO> findAll(Pageable pageable);
    /**
     * Get all the PageWebDTO where Reference is null.
     *
     * @return the list of entities
     */
    List<PageWebDTO> findAllWhereReferenceIsNull();


    /**
     * Get the "id" pageWeb.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PageWebDTO> findOne(Long id);

    /**
     * Delete the "id" pageWeb.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
