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

import fr.olympp.domain.Floraison;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.FloraisonRepository;
import fr.olympp.service.dto.FloraisonCriteria;
import fr.olympp.service.dto.FloraisonDTO;
import fr.olympp.service.mapper.FloraisonMapper;

/**
 * Service for executing complex queries for Floraison entities in the database.
 * The main input is a {@link FloraisonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FloraisonDTO} or a {@link Page} of {@link FloraisonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FloraisonQueryService extends QueryService<Floraison> {

    private final Logger log = LoggerFactory.getLogger(FloraisonQueryService.class);

    private final FloraisonRepository floraisonRepository;

    private final FloraisonMapper floraisonMapper;

    public FloraisonQueryService(FloraisonRepository floraisonRepository, FloraisonMapper floraisonMapper) {
        this.floraisonRepository = floraisonRepository;
        this.floraisonMapper = floraisonMapper;
    }

    /**
     * Return a {@link List} of {@link FloraisonDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FloraisonDTO> findByCriteria(FloraisonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Floraison> specification = createSpecification(criteria);
        return floraisonMapper.toDto(floraisonRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FloraisonDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FloraisonDTO> findByCriteria(FloraisonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Floraison> specification = createSpecification(criteria);
        return floraisonRepository.findAll(specification, page)
            .map(floraisonMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FloraisonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Floraison> specification = createSpecification(criteria);
        return floraisonRepository.count(specification);
    }

    /**
     * Function to convert FloraisonCriteria to a {@link Specification}
     */
    private Specification<Floraison> createSpecification(FloraisonCriteria criteria) {
        Specification<Floraison> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Floraison_.id));
            }
            if (criteria.getPlanteId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanteId(),
                    root -> root.join(Floraison_.plante, JoinType.LEFT).get(Plante_.id)));
            }
            if (criteria.getMoisId() != null) {
                specification = specification.and(buildSpecification(criteria.getMoisId(),
                    root -> root.join(Floraison_.mois, JoinType.LEFT).get(Mois_.id)));
            }
        }
        return specification;
    }
}
