package fr.olympp.service.impl;

import fr.olympp.service.ClassificationCronquistService;
import fr.olympp.domain.ClassificationCronquist;
import fr.olympp.repository.ClassificationCronquistRepository;
import fr.olympp.service.dto.ClassificationCronquistDTO;
import fr.olympp.service.mapper.ClassificationCronquistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ClassificationCronquist.
 */
@Service
@Transactional
public class ClassificationCronquistServiceImpl implements ClassificationCronquistService {

    private final Logger log = LoggerFactory.getLogger(ClassificationCronquistServiceImpl.class);

    private final ClassificationCronquistRepository classificationCronquistRepository;

    private final ClassificationCronquistMapper classificationCronquistMapper;

    public ClassificationCronquistServiceImpl(ClassificationCronquistRepository classificationCronquistRepository, ClassificationCronquistMapper classificationCronquistMapper) {
        this.classificationCronquistRepository = classificationCronquistRepository;
        this.classificationCronquistMapper = classificationCronquistMapper;
    }

    /**
     * Save a classificationCronquist.
     *
     * @param classificationCronquistDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassificationCronquistDTO save(ClassificationCronquistDTO classificationCronquistDTO) {
        log.debug("Request to save ClassificationCronquist : {}", classificationCronquistDTO);

        ClassificationCronquist classificationCronquist = classificationCronquistMapper.toEntity(classificationCronquistDTO);
        classificationCronquist = classificationCronquistRepository.save(classificationCronquist);
        return classificationCronquistMapper.toDto(classificationCronquist);
    }

    /**
     * Get all the classificationCronquists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassificationCronquistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassificationCronquists");
        return classificationCronquistRepository.findAll(pageable)
            .map(classificationCronquistMapper::toDto);
    }


    /**
     * Get one classificationCronquist by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClassificationCronquistDTO> findOne(Long id) {
        log.debug("Request to get ClassificationCronquist : {}", id);
        return classificationCronquistRepository.findById(id)
            .map(classificationCronquistMapper::toDto);
    }

    /**
     * Delete the classificationCronquist by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassificationCronquist : {}", id);
        classificationCronquistRepository.deleteById(id);
    }
}
