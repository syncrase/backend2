package fr.olympp.service.impl;

import fr.olympp.service.RichesseSolService;
import fr.olympp.domain.RichesseSol;
import fr.olympp.repository.RichesseSolRepository;
import fr.olympp.service.dto.RichesseSolDTO;
import fr.olympp.service.mapper.RichesseSolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RichesseSol.
 */
@Service
@Transactional
public class RichesseSolServiceImpl implements RichesseSolService {

    private final Logger log = LoggerFactory.getLogger(RichesseSolServiceImpl.class);

    private final RichesseSolRepository richesseSolRepository;

    private final RichesseSolMapper richesseSolMapper;

    public RichesseSolServiceImpl(RichesseSolRepository richesseSolRepository, RichesseSolMapper richesseSolMapper) {
        this.richesseSolRepository = richesseSolRepository;
        this.richesseSolMapper = richesseSolMapper;
    }

    /**
     * Save a richesseSol.
     *
     * @param richesseSolDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RichesseSolDTO save(RichesseSolDTO richesseSolDTO) {
        log.debug("Request to save RichesseSol : {}", richesseSolDTO);

        RichesseSol richesseSol = richesseSolMapper.toEntity(richesseSolDTO);
        richesseSol = richesseSolRepository.save(richesseSol);
        return richesseSolMapper.toDto(richesseSol);
    }

    /**
     * Get all the richesseSols.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RichesseSolDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RichesseSols");
        return richesseSolRepository.findAll(pageable)
            .map(richesseSolMapper::toDto);
    }


    /**
     * Get one richesseSol by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RichesseSolDTO> findOne(Long id) {
        log.debug("Request to get RichesseSol : {}", id);
        return richesseSolRepository.findById(id)
            .map(richesseSolMapper::toDto);
    }

    /**
     * Delete the richesseSol by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RichesseSol : {}", id);
        richesseSolRepository.deleteById(id);
    }
}
