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

import fr.olympp.domain.Ordre;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.OrdreRepository;
import fr.olympp.service.dto.OrdreCriteria;
import fr.olympp.service.dto.OrdreDTO;
import fr.olympp.service.mapper.OrdreMapper;

/**
 * Service for executing complex queries for Ordre entities in the database.
 * The main input is a {@link OrdreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdreDTO} or a {@link Page} of {@link OrdreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdreQueryService extends QueryService<Ordre> {

    private final Logger log = LoggerFactory.getLogger(OrdreQueryService.class);

    private final OrdreRepository ordreRepository;

    private final OrdreMapper ordreMapper;

    public OrdreQueryService(OrdreRepository ordreRepository, OrdreMapper ordreMapper) {
        this.ordreRepository = ordreRepository;
        this.ordreMapper = ordreMapper;
    }

    /**
     * Return a {@link List} of {@link OrdreDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdreDTO> findByCriteria(OrdreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ordre> specification = createSpecification(criteria);
        return ordreMapper.toDto(ordreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdreDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdreDTO> findByCriteria(OrdreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ordre> specification = createSpecification(criteria);
        return ordreRepository.findAll(specification, page)
            .map(ordreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ordre> specification = createSpecification(criteria);
        return ordreRepository.count(specification);
    }

    /**
     * Function to convert OrdreCriteria to a {@link Specification}
     */
    private Specification<Ordre> createSpecification(OrdreCriteria criteria) {
        Specification<Ordre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Ordre_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Ordre_.name));
            }
        }
        return specification;
    }
}
