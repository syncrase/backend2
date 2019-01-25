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

import fr.olympp.domain.TypeFeuillage;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.TypeFeuillageRepository;
import fr.olympp.service.dto.TypeFeuillageCriteria;
import fr.olympp.service.dto.TypeFeuillageDTO;
import fr.olympp.service.mapper.TypeFeuillageMapper;

/**
 * Service for executing complex queries for TypeFeuillage entities in the database.
 * The main input is a {@link TypeFeuillageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeFeuillageDTO} or a {@link Page} of {@link TypeFeuillageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeFeuillageQueryService extends QueryService<TypeFeuillage> {

    private final Logger log = LoggerFactory.getLogger(TypeFeuillageQueryService.class);

    private final TypeFeuillageRepository typeFeuillageRepository;

    private final TypeFeuillageMapper typeFeuillageMapper;

    public TypeFeuillageQueryService(TypeFeuillageRepository typeFeuillageRepository, TypeFeuillageMapper typeFeuillageMapper) {
        this.typeFeuillageRepository = typeFeuillageRepository;
        this.typeFeuillageMapper = typeFeuillageMapper;
    }

    /**
     * Return a {@link List} of {@link TypeFeuillageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeFeuillageDTO> findByCriteria(TypeFeuillageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeFeuillage> specification = createSpecification(criteria);
        return typeFeuillageMapper.toDto(typeFeuillageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypeFeuillageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeFeuillageDTO> findByCriteria(TypeFeuillageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeFeuillage> specification = createSpecification(criteria);
        return typeFeuillageRepository.findAll(specification, page)
            .map(typeFeuillageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeFeuillageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeFeuillage> specification = createSpecification(criteria);
        return typeFeuillageRepository.count(specification);
    }

    /**
     * Function to convert TypeFeuillageCriteria to a {@link Specification}
     */
    private Specification<TypeFeuillage> createSpecification(TypeFeuillageCriteria criteria) {
        Specification<TypeFeuillage> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TypeFeuillage_.id));
            }
            if (criteria.getTypeFeuillage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeFeuillage(), TypeFeuillage_.typeFeuillage));
            }
        }
        return specification;
    }
}
