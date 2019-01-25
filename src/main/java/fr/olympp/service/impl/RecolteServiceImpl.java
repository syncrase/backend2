package fr.olympp.service.impl;

import fr.olympp.service.RecolteService;
import fr.olympp.domain.Recolte;
import fr.olympp.repository.RecolteRepository;
import fr.olympp.service.dto.RecolteDTO;
import fr.olympp.service.mapper.RecolteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Recolte.
 */
@Service
@Transactional
public class RecolteServiceImpl implements RecolteService {

    private final Logger log = LoggerFactory.getLogger(RecolteServiceImpl.class);

    private final RecolteRepository recolteRepository;

    private final RecolteMapper recolteMapper;

    public RecolteServiceImpl(RecolteRepository recolteRepository, RecolteMapper recolteMapper) {
        this.recolteRepository = recolteRepository;
        this.recolteMapper = recolteMapper;
    }

    /**
     * Save a recolte.
     *
     * @param recolteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecolteDTO save(RecolteDTO recolteDTO) {
        log.debug("Request to save Recolte : {}", recolteDTO);

        Recolte recolte = recolteMapper.toEntity(recolteDTO);
        recolte = recolteRepository.save(recolte);
        return recolteMapper.toDto(recolte);
    }

    /**
     * Get all the recoltes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecolteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recoltes");
        return recolteRepository.findAll(pageable)
            .map(recolteMapper::toDto);
    }


    /**
     * Get one recolte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecolteDTO> findOne(Long id) {
        log.debug("Request to get Recolte : {}", id);
        return recolteRepository.findById(id)
            .map(recolteMapper::toDto);
    }

    /**
     * Delete the recolte by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recolte : {}", id);
        recolteRepository.deleteById(id);
    }
}
