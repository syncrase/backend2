package fr.olympp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import fr.olympp.domain.Plante;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.PlanteRepository;
import fr.olympp.service.dto.PlanteCriteria;
import fr.olympp.service.dto.PlanteDTO;
import fr.olympp.service.mapper.PlanteMapper;

/**
 * Service for executing complex queries for Plante entities in the database.
 * The main input is a {@link PlanteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanteDTO} or a {@link Page} of {@link PlanteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanteQueryService extends QueryService<Plante> {

    private final Logger log = LoggerFactory.getLogger(PlanteQueryService.class);

    private final PlanteRepository planteRepository;

    private final PlanteMapper planteMapper;

    public PlanteQueryService(PlanteRepository planteRepository, PlanteMapper planteMapper) {
        this.planteRepository = planteRepository;
        this.planteMapper = planteMapper;
    }

    /**
     * Return a {@link List} of {@link PlanteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanteDTO> findByCriteria(PlanteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Plante> specification = createSpecification(criteria);
        return planteMapper.toDto(planteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanteDTO> findByCriteria(PlanteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Plante> specification = createSpecification(criteria);
        return planteRepository.findAll(specification, page)
            .map(planteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Plante> specification = createSpecification(criteria);
        return planteRepository.count(specification);
    }

    /**
     * Function to convert PlanteCriteria to a {@link Specification}
     */
    private Specification<Plante> createSpecification(PlanteCriteria criteria) {
        Specification<Plante> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Plante_.id));
            }
            if (criteria.getPhMin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhMin(), Plante_.phMin));
            }
            if (criteria.getPhMax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhMax(), Plante_.phMax));
            }
            if (criteria.getTempMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTempMin(), Plante_.tempMin));
            }
            if (criteria.getTempMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTempMax(), Plante_.tempMax));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Plante_.description));
            }
            if (criteria.getClassificationCronquistId() != null) {
                specification = specification.and(buildSpecification(criteria.getClassificationCronquistId(),
                    root -> root.join(Plante_.classificationCronquist, JoinType.LEFT).get(ClassificationCronquist_.id)));
            }
            if (criteria.getStrateId() != null) {
                specification = specification.and(buildSpecification(criteria.getStrateId(),
                    root -> root.join(Plante_.strate, JoinType.LEFT).get(Strate_.id)));
            }
            if (criteria.getVitesseCroissanceId() != null) {
                specification = specification.and(buildSpecification(criteria.getVitesseCroissanceId(),
                    root -> root.join(Plante_.vitesseCroissance, JoinType.LEFT).get(VitesseCroissance_.id)));
            }
            if (criteria.getEnsoleillementId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnsoleillementId(),
                    root -> root.join(Plante_.ensoleillement, JoinType.LEFT).get(Ensoleillement_.id)));
            }
            if (criteria.getRichesseSolId() != null) {
                specification = specification.and(buildSpecification(criteria.getRichesseSolId(),
                    root -> root.join(Plante_.richesseSol, JoinType.LEFT).get(RichesseSol_.id)));
            }
            if (criteria.getTypeTerreId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeTerreId(),
                    root -> root.join(Plante_.typeTerre, JoinType.LEFT).get(TypeTerre_.id)));
            }
            if (criteria.getTypeFeuillageId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeFeuillageId(),
                    root -> root.join(Plante_.typeFeuillage, JoinType.LEFT).get(TypeFeuillage_.id)));
            }
            if (criteria.getTypeRacineId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeRacineId(),
                    root -> root.join(Plante_.typeRacine, JoinType.LEFT).get(TypeRacine_.id)));
            }
            if (criteria.getPlantCommonNameId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlantCommonNameId(),
                    root -> root.join(Plante_.plantCommonNames, JoinType.LEFT).get(PlantCommonName_.id)));
            }
        }
        return specification;
    }
}
