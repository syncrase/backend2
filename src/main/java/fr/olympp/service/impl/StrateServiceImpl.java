package fr.olympp.service.impl;

import fr.olympp.service.StrateService;
import fr.olympp.domain.Strate;
import fr.olympp.repository.StrateRepository;
import fr.olympp.service.dto.StrateDTO;
import fr.olympp.service.mapper.StrateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Strate.
 */
@Service
@Transactional
public class StrateServiceImpl implements StrateService {

    private final Logger log = LoggerFactory.getLogger(StrateServiceImpl.class);

    private final StrateRepository strateRepository;

    private final StrateMapper strateMapper;

    public StrateServiceImpl(StrateRepository strateRepository, StrateMapper strateMapper) {
        this.strateRepository = strateRepository;
        this.strateMapper = strateMapper;
    }

    /**
     * Save a strate.
     *
     * @param strateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StrateDTO save(StrateDTO strateDTO) {
        log.debug("Request to save Strate : {}", strateDTO);

        Strate strate = strateMapper.toEntity(strateDTO);
        strate = strateRepository.save(strate);
        return strateMapper.toDto(strate);
    }

    /**
     * Get all the strates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StrateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Strates");
        return strateRepository.findAll(pageable)
            .map(strateMapper::toDto);
    }


    /**
     * Get one strate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StrateDTO> findOne(Long id) {
        log.debug("Request to get Strate : {}", id);
        return strateRepository.findById(id)
            .map(strateMapper::toDto);
    }

    /**
     * Delete the strate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Strate : {}", id);
        strateRepository.deleteById(id);
    }
}
