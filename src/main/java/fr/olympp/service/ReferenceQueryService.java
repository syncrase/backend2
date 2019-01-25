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

import fr.olympp.domain.Reference;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.ReferenceRepository;
import fr.olympp.service.dto.ReferenceCriteria;
import fr.olympp.service.dto.ReferenceDTO;
import fr.olympp.service.mapper.ReferenceMapper;

/**
 * Service for executing complex queries for Reference entities in the database.
 * The main input is a {@link ReferenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReferenceDTO} or a {@link Page} of {@link ReferenceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReferenceQueryService extends QueryService<Reference> {

    private final Logger log = LoggerFactory.getLogger(ReferenceQueryService.class);

    private final ReferenceRepository referenceRepository;

    private final ReferenceMapper referenceMapper;

    public ReferenceQueryService(ReferenceRepository referenceRepository, ReferenceMapper referenceMapper) {
        this.referenceRepository = referenceRepository;
        this.referenceMapper = referenceMapper;
    }

    /**
     * Return a {@link List} of {@link ReferenceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReferenceDTO> findByCriteria(ReferenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reference> specification = createSpecification(criteria);
        return referenceMapper.toDto(referenceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReferenceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferenceDTO> findByCriteria(ReferenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reference> specification = createSpecification(criteria);
        return referenceRepository.findAll(specification, page)
            .map(referenceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReferenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reference> specification = createSpecification(criteria);
        return referenceRepository.count(specification);
    }

    /**
     * Function to convert ReferenceCriteria to a {@link Specification}
     */
    private Specification<Reference> createSpecification(ReferenceCriteria criteria) {
        Specification<Reference> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Reference_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Reference_.description));
            }
            if (criteria.getLivreId() != null) {
                specification = specification.and(buildSpecification(criteria.getLivreId(),
                    root -> root.join(Reference_.livre, JoinType.LEFT).get(Livre_.id)));
            }
            if (criteria.getPageWebId() != null) {
                specification = specification.and(buildSpecification(criteria.getPageWebId(),
                    root -> root.join(Reference_.pageWeb, JoinType.LEFT).get(PageWeb_.id)));
            }
            if (criteria.getInteractionPlantePlanteId() != null) {
                specification = specification.and(buildSpecification(criteria.getInteractionPlantePlanteId(),
                    root -> root.join(Reference_.interactionPlantePlante, JoinType.LEFT).get(InteractionPlantePlante_.id)));
            }
        }
        return specification;
    }
}
