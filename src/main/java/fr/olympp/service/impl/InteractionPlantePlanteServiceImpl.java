package fr.olympp.service.impl;

import fr.olympp.service.InteractionPlantePlanteService;
import fr.olympp.domain.InteractionPlantePlante;
import fr.olympp.repository.InteractionPlantePlanteRepository;
import fr.olympp.service.dto.InteractionPlantePlanteDTO;
import fr.olympp.service.mapper.InteractionPlantePlanteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing InteractionPlantePlante.
 */
@Service
@Transactional
public class InteractionPlantePlanteServiceImpl implements InteractionPlantePlanteService {

    private final Logger log = LoggerFactory.getLogger(InteractionPlantePlanteServiceImpl.class);

    private final InteractionPlantePlanteRepository interactionPlantePlanteRepository;

    private final InteractionPlantePlanteMapper interactionPlantePlanteMapper;

    public InteractionPlantePlanteServiceImpl(InteractionPlantePlanteRepository interactionPlantePlanteRepository, InteractionPlantePlanteMapper interactionPlantePlanteMapper) {
        this.interactionPlantePlanteRepository = interactionPlantePlanteRepository;
        this.interactionPlantePlanteMapper = interactionPlantePlanteMapper;
    }

    /**
     * Save a interactionPlantePlante.
     *
     * @param interactionPlantePlanteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InteractionPlantePlanteDTO save(InteractionPlantePlanteDTO interactionPlantePlanteDTO) {
        log.debug("Request to save InteractionPlantePlante : {}", interactionPlantePlanteDTO);

        InteractionPlantePlante interactionPlantePlante = interactionPlantePlanteMapper.toEntity(interactionPlantePlanteDTO);
        interactionPlantePlante = interactionPlantePlanteRepository.save(interactionPlantePlante);
        return interactionPlantePlanteMapper.toDto(interactionPlantePlante);
    }

    /**
     * Get all the interactionPlantePlantes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InteractionPlantePlanteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InteractionPlantePlantes");
        return interactionPlantePlanteRepository.findAll(pageable)
            .map(interactionPlantePlanteMapper::toDto);
    }


    /**
     * Get one interactionPlantePlante by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InteractionPlantePlanteDTO> findOne(Long id) {
        log.debug("Request to get InteractionPlantePlante : {}", id);
        return interactionPlantePlanteRepository.findById(id)
            .map(interactionPlantePlanteMapper::toDto);
    }

    /**
     * Delete the interactionPlantePlante by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InteractionPlantePlante : {}", id);
        interactionPlantePlanteRepository.deleteById(id);
    }
}
