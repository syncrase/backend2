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

import fr.olympp.domain.TypeRacine;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.TypeRacineRepository;
import fr.olympp.service.dto.TypeRacineCriteria;
import fr.olympp.service.dto.TypeRacineDTO;
import fr.olympp.service.mapper.TypeRacineMapper;

/**
 * Service for executing complex queries for TypeRacine entities in the database.
 * The main input is a {@link TypeRacineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeRacineDTO} or a {@link Page} of {@link TypeRacineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeRacineQueryService extends QueryService<TypeRacine> {

    private final Logger log = LoggerFactory.getLogger(TypeRacineQueryService.class);

    private final TypeRacineRepository typeRacineRepository;

    private final TypeRacineMapper typeRacineMapper;

    public TypeRacineQueryService(TypeRacineRepository typeRacineRepository, TypeRacineMapper typeRacineMapper) {
        this.typeRacineRepository = typeRacineRepository;
        this.typeRacineMapper = typeRacineMapper;
    }

    /**
     * Return a {@link List} of {@link TypeRacineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeRacineDTO> findByCriteria(TypeRacineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeRacine> specification = createSpecification(criteria);
        return typeRacineMapper.toDto(typeRacineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypeRacineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeRacineDTO> findByCriteria(TypeRacineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeRacine> specification = createSpecification(criteria);
        return typeRacineRepository.findAll(specification, page)
            .map(typeRacineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeRacineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeRacine> specification = createSpecification(criteria);
        return typeRacineRepository.count(specification);
    }

    /**
     * Function to convert TypeRacineCriteria to a {@link Specification}
     */
    private Specification<TypeRacine> createSpecification(TypeRacineCriteria criteria) {
        Specification<TypeRacine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TypeRacine_.id));
            }
            if (criteria.getTypeRacine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeRacine(), TypeRacine_.typeRacine));
            }
        }
        return specification;
    }
}
