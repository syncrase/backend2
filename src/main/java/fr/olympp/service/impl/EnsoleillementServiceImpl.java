package fr.olympp.service.impl;

import fr.olympp.service.EnsoleillementService;
import fr.olympp.domain.Ensoleillement;
import fr.olympp.repository.EnsoleillementRepository;
import fr.olympp.service.dto.EnsoleillementDTO;
import fr.olympp.service.mapper.EnsoleillementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Ensoleillement.
 */
@Service
@Transactional
public class EnsoleillementServiceImpl implements EnsoleillementService {

    private final Logger log = LoggerFactory.getLogger(EnsoleillementServiceImpl.class);

    private final EnsoleillementRepository ensoleillementRepository;

    private final EnsoleillementMapper ensoleillementMapper;

    public EnsoleillementServiceImpl(EnsoleillementRepository ensoleillementRepository, EnsoleillementMapper ensoleillementMapper) {
        this.ensoleillementRepository = ensoleillementRepository;
        this.ensoleillementMapper = ensoleillementMapper;
    }

    /**
     * Save a ensoleillement.
     *
     * @param ensoleillementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnsoleillementDTO save(EnsoleillementDTO ensoleillementDTO) {
        log.debug("Request to save Ensoleillement : {}", ensoleillementDTO);

        Ensoleillement ensoleillement = ensoleillementMapper.toEntity(ensoleillementDTO);
        ensoleillement = ensoleillementRepository.save(ensoleillement);
        return ensoleillementMapper.toDto(ensoleillement);
    }

    /**
     * Get all the ensoleillements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnsoleillementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ensoleillements");
        return ensoleillementRepository.findAll(pageable)
            .map(ensoleillementMapper::toDto);
    }


    /**
     * Get one ensoleillement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnsoleillementDTO> findOne(Long id) {
        log.debug("Request to get Ensoleillement : {}", id);
        return ensoleillementRepository.findById(id)
            .map(ensoleillementMapper::toDto);
    }

    /**
     * Delete the ensoleillement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ensoleillement : {}", id);
        ensoleillementRepository.deleteById(id);
    }
}
