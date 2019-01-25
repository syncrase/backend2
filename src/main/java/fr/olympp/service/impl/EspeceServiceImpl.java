package fr.olympp.service.impl;

import fr.olympp.service.EspeceService;
import fr.olympp.domain.Espece;
import fr.olympp.repository.EspeceRepository;
import fr.olympp.service.dto.EspeceDTO;
import fr.olympp.service.mapper.EspeceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Espece.
 */
@Service
@Transactional
public class EspeceServiceImpl implements EspeceService {

    private final Logger log = LoggerFactory.getLogger(EspeceServiceImpl.class);

    private final EspeceRepository especeRepository;

    private final EspeceMapper especeMapper;

    public EspeceServiceImpl(EspeceRepository especeRepository, EspeceMapper especeMapper) {
        this.especeRepository = especeRepository;
        this.especeMapper = especeMapper;
    }

    /**
     * Save a espece.
     *
     * @param especeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EspeceDTO save(EspeceDTO especeDTO) {
        log.debug("Request to save Espece : {}", especeDTO);

        Espece espece = especeMapper.toEntity(especeDTO);
        espece = especeRepository.save(espece);
        return especeMapper.toDto(espece);
    }

    /**
     * Get all the especes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EspeceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Especes");
        return especeRepository.findAll(pageable)
            .map(especeMapper::toDto);
    }


    /**
     * Get one espece by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EspeceDTO> findOne(Long id) {
        log.debug("Request to get Espece : {}", id);
        return especeRepository.findById(id)
            .map(especeMapper::toDto);
    }

    /**
     * Delete the espece by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Espece : {}", id);
        especeRepository.deleteById(id);
    }
}
