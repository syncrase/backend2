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

import fr.olympp.domain.Famille;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.FamilleRepository;
import fr.olympp.service.dto.FamilleCriteria;
import fr.olympp.service.dto.FamilleDTO;
import fr.olympp.service.mapper.FamilleMapper;

/**
 * Service for executing complex queries for Famille entities in the database.
 * The main input is a {@link FamilleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FamilleDTO} or a {@link Page} of {@link FamilleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FamilleQueryService extends QueryService<Famille> {

    private final Logger log = LoggerFactory.getLogger(FamilleQueryService.class);

    private final FamilleRepository familleRepository;

    private final FamilleMapper familleMapper;

    public FamilleQueryService(FamilleRepository familleRepository, FamilleMapper familleMapper) {
        this.familleRepository = familleRepository;
        this.familleMapper = familleMapper;
    }

    /**
     * Return a {@link List} of {@link FamilleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FamilleDTO> findByCriteria(FamilleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Famille> specification = createSpecification(criteria);
        return familleMapper.toDto(familleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FamilleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FamilleDTO> findByCriteria(FamilleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Famille> specification = createSpecification(criteria);
        return familleRepository.findAll(specification, page)
            .map(familleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FamilleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Famille> specification = createSpecification(criteria);
        return familleRepository.count(specification);
    }

    /**
     * Function to convert FamilleCriteria to a {@link Specification}
     */
    private Specification<Famille> createSpecification(FamilleCriteria criteria) {
        Specification<Famille> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Famille_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Famille_.name));
            }
        }
        return specification;
    }
}
