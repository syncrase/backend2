package fr.olympp.service;

import fr.olympp.service.dto.GenreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Genre.
 */
public interface GenreService {

    /**
     * Save a genre.
     *
     * @param genreDTO the entity to save
     * @return the persisted entity
     */
    GenreDTO save(GenreDTO genreDTO);

    /**
     * Get all the genres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GenreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" genre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GenreDTO> findOne(Long id);

    /**
     * Delete the "id" genre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
