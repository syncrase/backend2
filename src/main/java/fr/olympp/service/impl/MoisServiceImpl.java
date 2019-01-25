package fr.olympp.service.impl;

import fr.olympp.service.MoisService;
import fr.olympp.domain.Mois;
import fr.olympp.repository.MoisRepository;
import fr.olympp.service.dto.MoisDTO;
import fr.olympp.service.mapper.MoisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Mois.
 */
@Service
@Transactional
public class MoisServiceImpl implements MoisService {

    private final Logger log = LoggerFactory.getLogger(MoisServiceImpl.class);

    private final MoisRepository moisRepository;

    private final MoisMapper moisMapper;

    public MoisServiceImpl(MoisRepository moisRepository, MoisMapper moisMapper) {
        this.moisRepository = moisRepository;
        this.moisMapper = moisMapper;
    }

    /**
     * Save a mois.
     *
     * @param moisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MoisDTO save(MoisDTO moisDTO) {
        log.debug("Request to save Mois : {}", moisDTO);

        Mois mois = moisMapper.toEntity(moisDTO);
        mois = moisRepository.save(mois);
        return moisMapper.toDto(mois);
    }

    /**
     * Get all the mois.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MoisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mois");
        return moisRepository.findAll(pageable)
            .map(moisMapper::toDto);
    }


    /**
     * Get one mois by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MoisDTO> findOne(Long id) {
        log.debug("Request to get Mois : {}", id);
        return moisRepository.findById(id)
            .map(moisMapper::toDto);
    }

    /**
     * Delete the mois by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mois : {}", id);
        moisRepository.deleteById(id);
    }
}
