package fr.olympp.service.impl;

import fr.olympp.service.VitesseCroissanceService;
import fr.olympp.domain.VitesseCroissance;
import fr.olympp.repository.VitesseCroissanceRepository;
import fr.olympp.service.dto.VitesseCroissanceDTO;
import fr.olympp.service.mapper.VitesseCroissanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing VitesseCroissance.
 */
@Service
@Transactional
public class VitesseCroissanceServiceImpl implements VitesseCroissanceService {

    private final Logger log = LoggerFactory.getLogger(VitesseCroissanceServiceImpl.class);

    private final VitesseCroissanceRepository vitesseCroissanceRepository;

    private final VitesseCroissanceMapper vitesseCroissanceMapper;

    public VitesseCroissanceServiceImpl(VitesseCroissanceRepository vitesseCroissanceRepository, VitesseCroissanceMapper vitesseCroissanceMapper) {
        this.vitesseCroissanceRepository = vitesseCroissanceRepository;
        this.vitesseCroissanceMapper = vitesseCroissanceMapper;
    }

    /**
     * Save a vitesseCroissance.
     *
     * @param vitesseCroissanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VitesseCroissanceDTO save(VitesseCroissanceDTO vitesseCroissanceDTO) {
        log.debug("Request to save VitesseCroissance : {}", vitesseCroissanceDTO);

        VitesseCroissance vitesseCroissance = vitesseCroissanceMapper.toEntity(vitesseCroissanceDTO);
        vitesseCroissance = vitesseCroissanceRepository.save(vitesseCroissance);
        return vitesseCroissanceMapper.toDto(vitesseCroissance);
    }

    /**
     * Get all the vitesseCroissances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VitesseCroissanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VitesseCroissances");
        return vitesseCroissanceRepository.findAll(pageable)
            .map(vitesseCroissanceMapper::toDto);
    }


    /**
     * Get one vitesseCroissance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VitesseCroissanceDTO> findOne(Long id) {
        log.debug("Request to get VitesseCroissance : {}", id);
        return vitesseCroissanceRepository.findById(id)
            .map(vitesseCroissanceMapper::toDto);
    }

    /**
     * Delete the vitesseCroissance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VitesseCroissance : {}", id);
        vitesseCroissanceRepository.deleteById(id);
    }
}
