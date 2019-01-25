package fr.olympp.service;

import fr.olympp.service.dto.ClassificationCronquistDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ClassificationCronquist.
 */
public interface ClassificationCronquistService {

    /**
     * Save a classificationCronquist.
     *
     * @param classificationCronquistDTO the entity to save
     * @return the persisted entity
     */
    ClassificationCronquistDTO save(ClassificationCronquistDTO classificationCronquistDTO);

    /**
     * Get all the classificationCronquists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClassificationCronquistDTO> findAll(Pageable pageable);


    /**
     * Get the "id" classificationCronquist.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClassificationCronquistDTO> findOne(Long id);

    /**
     * Delete the "id" classificationCronquist.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
