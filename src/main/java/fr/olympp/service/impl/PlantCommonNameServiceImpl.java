package fr.olympp.service.impl;

import fr.olympp.service.PlantCommonNameService;
import fr.olympp.domain.PlantCommonName;
import fr.olympp.repository.PlantCommonNameRepository;
import fr.olympp.service.dto.PlantCommonNameDTO;
import fr.olympp.service.mapper.PlantCommonNameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PlantCommonName.
 */
@Service
@Transactional
public class PlantCommonNameServiceImpl implements PlantCommonNameService {

    private final Logger log = LoggerFactory.getLogger(PlantCommonNameServiceImpl.class);

    private final PlantCommonNameRepository plantCommonNameRepository;

    private final PlantCommonNameMapper plantCommonNameMapper;

    public PlantCommonNameServiceImpl(PlantCommonNameRepository plantCommonNameRepository, PlantCommonNameMapper plantCommonNameMapper) {
        this.plantCommonNameRepository = plantCommonNameRepository;
        this.plantCommonNameMapper = plantCommonNameMapper;
    }

    /**
     * Save a plantCommonName.
     *
     * @param plantCommonNameDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PlantCommonNameDTO save(PlantCommonNameDTO plantCommonNameDTO) {
        log.debug("Request to save PlantCommonName : {}", plantCommonNameDTO);

        PlantCommonName plantCommonName = plantCommonNameMapper.toEntity(plantCommonNameDTO);
        plantCommonName = plantCommonNameRepository.save(plantCommonName);
        return plantCommonNameMapper.toDto(plantCommonName);
    }

    /**
     * Get all the plantCommonNames.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlantCommonNameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlantCommonNames");
        return plantCommonNameRepository.findAll(pageable)
            .map(plantCommonNameMapper::toDto);
    }


    /**
     * Get one plantCommonName by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlantCommonNameDTO> findOne(Long id) {
        log.debug("Request to get PlantCommonName : {}", id);
        return plantCommonNameRepository.findById(id)
            .map(plantCommonNameMapper::toDto);
    }

    /**
     * Delete the plantCommonName by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlantCommonName : {}", id);
        plantCommonNameRepository.deleteById(id);
    }
}
