package fr.olympp.service.impl;

import fr.olympp.service.FamilleService;
import fr.olympp.domain.Famille;
import fr.olympp.repository.FamilleRepository;
import fr.olympp.service.dto.FamilleDTO;
import fr.olympp.service.mapper.FamilleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Famille.
 */
@Service
@Transactional
public class FamilleServiceImpl implements FamilleService {

    private final Logger log = LoggerFactory.getLogger(FamilleServiceImpl.class);

    private final FamilleRepository familleRepository;

    private final FamilleMapper familleMapper;

    public FamilleServiceImpl(FamilleRepository familleRepository, FamilleMapper familleMapper) {
        this.familleRepository = familleRepository;
        this.familleMapper = familleMapper;
    }

    /**
     * Save a famille.
     *
     * @param familleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FamilleDTO save(FamilleDTO familleDTO) {
        log.debug("Request to save Famille : {}", familleDTO);

        Famille famille = familleMapper.toEntity(familleDTO);
        famille = familleRepository.save(famille);
        return familleMapper.toDto(famille);
    }

    /**
     * Get all the familles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FamilleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Familles");
        return familleRepository.findAll(pageable)
            .map(familleMapper::toDto);
    }


    /**
     * Get one famille by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FamilleDTO> findOne(Long id) {
        log.debug("Request to get Famille : {}", id);
        return familleRepository.findById(id)
            .map(familleMapper::toDto);
    }

    /**
     * Delete the famille by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Famille : {}", id);
        familleRepository.deleteById(id);
    }
}
