package fr.olympp.service.impl;

import fr.olympp.service.FloraisonService;
import fr.olympp.domain.Floraison;
import fr.olympp.repository.FloraisonRepository;
import fr.olympp.service.dto.FloraisonDTO;
import fr.olympp.service.mapper.FloraisonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Floraison.
 */
@Service
@Transactional
public class FloraisonServiceImpl implements FloraisonService {

    private final Logger log = LoggerFactory.getLogger(FloraisonServiceImpl.class);

    private final FloraisonRepository floraisonRepository;

    private final FloraisonMapper floraisonMapper;

    public FloraisonServiceImpl(FloraisonRepository floraisonRepository, FloraisonMapper floraisonMapper) {
        this.floraisonRepository = floraisonRepository;
        this.floraisonMapper = floraisonMapper;
    }

    /**
     * Save a floraison.
     *
     * @param floraisonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FloraisonDTO save(FloraisonDTO floraisonDTO) {
        log.debug("Request to save Floraison : {}", floraisonDTO);

        Floraison floraison = floraisonMapper.toEntity(floraisonDTO);
        floraison = floraisonRepository.save(floraison);
        return floraisonMapper.toDto(floraison);
    }

    /**
     * Get all the floraisons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FloraisonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Floraisons");
        return floraisonRepository.findAll(pageable)
            .map(floraisonMapper::toDto);
    }


    /**
     * Get one floraison by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FloraisonDTO> findOne(Long id) {
        log.debug("Request to get Floraison : {}", id);
        return floraisonRepository.findById(id)
            .map(floraisonMapper::toDto);
    }

    /**
     * Delete the floraison by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Floraison : {}", id);
        floraisonRepository.deleteById(id);
    }
}
