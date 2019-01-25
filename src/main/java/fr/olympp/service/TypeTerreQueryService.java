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

import fr.olympp.domain.TypeTerre;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.TypeTerreRepository;
import fr.olympp.service.dto.TypeTerreCriteria;
import fr.olympp.service.dto.TypeTerreDTO;
import fr.olympp.service.mapper.TypeTerreMapper;

/**
 * Service for executing complex queries for TypeTerre entities in the database.
 * The main input is a {@link TypeTerreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeTerreDTO} or a {@link Page} of {@link TypeTerreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeTerreQueryService extends QueryService<TypeTerre> {

    private final Logger log = LoggerFactory.getLogger(TypeTerreQueryService.class);

    private final TypeTerreRepository typeTerreRepository;

    private final TypeTerreMapper typeTerreMapper;

    public TypeTerreQueryService(TypeTerreRepository typeTerreRepository, TypeTerreMapper typeTerreMapper) {
        this.typeTerreRepository = typeTerreRepository;
        this.typeTerreMapper = typeTerreMapper;
    }

    /**
     * Return a {@link List} of {@link TypeTerreDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeTerreDTO> findByCriteria(TypeTerreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeTerre> specification = createSpecification(criteria);
        return typeTerreMapper.toDto(typeTerreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypeTerreDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeTerreDTO> findByCriteria(TypeTerreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeTerre> specification = createSpecification(criteria);
        return typeTerreRepository.findAll(specification, page)
            .map(typeTerreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeTerreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeTerre> specification = createSpecification(criteria);
        return typeTerreRepository.count(specification);
    }

    /**
     * Function to convert TypeTerreCriteria to a {@link Specification}
     */
    private Specification<TypeTerre> createSpecification(TypeTerreCriteria criteria) {
        Specification<TypeTerre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TypeTerre_.id));
            }
            if (criteria.getTypeTerre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeTerre(), TypeTerre_.typeTerre));
            }
        }
        return specification;
    }
}
