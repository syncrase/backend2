package fr.olympp.service.impl;

import fr.olympp.service.OrdreService;
import fr.olympp.domain.Ordre;
import fr.olympp.repository.OrdreRepository;
import fr.olympp.service.dto.OrdreDTO;
import fr.olympp.service.mapper.OrdreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Ordre.
 */
@Service
@Transactional
public class OrdreServiceImpl implements OrdreService {

    private final Logger log = LoggerFactory.getLogger(OrdreServiceImpl.class);

    private final OrdreRepository ordreRepository;

    private final OrdreMapper ordreMapper;

    public OrdreServiceImpl(OrdreRepository ordreRepository, OrdreMapper ordreMapper) {
        this.ordreRepository = ordreRepository;
        this.ordreMapper = ordreMapper;
    }

    /**
     * Save a ordre.
     *
     * @param ordreDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrdreDTO save(OrdreDTO ordreDTO) {
        log.debug("Request to save Ordre : {}", ordreDTO);

        Ordre ordre = ordreMapper.toEntity(ordreDTO);
        ordre = ordreRepository.save(ordre);
        return ordreMapper.toDto(ordre);
    }

    /**
     * Get all the ordres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrdreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ordres");
        return ordreRepository.findAll(pageable)
            .map(ordreMapper::toDto);
    }


    /**
     * Get one ordre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrdreDTO> findOne(Long id) {
        log.debug("Request to get Ordre : {}", id);
        return ordreRepository.findById(id)
            .map(ordreMapper::toDto);
    }

    /**
     * Delete the ordre by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ordre : {}", id);
        ordreRepository.deleteById(id);
    }
}
