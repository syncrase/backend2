package fr.olympp.service.impl;

import fr.olympp.service.PlanteService;
import fr.olympp.domain.Plante;
import fr.olympp.repository.PlanteRepository;
import fr.olympp.service.dto.PlanteDTO;
import fr.olympp.service.mapper.PlanteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Plante.
 */
@Service
@Transactional
public class PlanteServiceImpl implements PlanteService {

    private final Logger log = LoggerFactory.getLogger(PlanteServiceImpl.class);

    private final PlanteRepository planteRepository;

    private final PlanteMapper planteMapper;

    public PlanteServiceImpl(PlanteRepository planteRepository, PlanteMapper planteMapper) {
        this.planteRepository = planteRepository;
        this.planteMapper = planteMapper;
    }

    /**
     * Save a plante.
     *
     * @param planteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PlanteDTO save(PlanteDTO planteDTO) {
        log.debug("Request to save Plante : {}", planteDTO);

        Plante plante = planteMapper.toEntity(planteDTO);
        plante = planteRepository.save(plante);
        return planteMapper.toDto(plante);
    }

    /**
     * Get all the plantes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlanteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Plantes");
        return planteRepository.findAll(pageable)
            .map(planteMapper::toDto);
    }

    /**
     * Get all the Plante with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PlanteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return planteRepository.findAllWithEagerRelationships(pageable).map(planteMapper::toDto);
    }
    

    /**
     * Get one plante by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlanteDTO> findOne(Long id) {
        log.debug("Request to get Plante : {}", id);
        return planteRepository.findOneWithEagerRelationships(id)
            .map(planteMapper::toDto);
    }

    /**
     * Delete the plante by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plante : {}", id);
        planteRepository.deleteById(id);
    }
}
