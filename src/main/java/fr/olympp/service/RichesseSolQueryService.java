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

import fr.olympp.domain.RichesseSol;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.RichesseSolRepository;
import fr.olympp.service.dto.RichesseSolCriteria;
import fr.olympp.service.dto.RichesseSolDTO;
import fr.olympp.service.mapper.RichesseSolMapper;

/**
 * Service for executing complex queries for RichesseSol entities in the database.
 * The main input is a {@link RichesseSolCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RichesseSolDTO} or a {@link Page} of {@link RichesseSolDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RichesseSolQueryService extends QueryService<RichesseSol> {

    private final Logger log = LoggerFactory.getLogger(RichesseSolQueryService.class);

    private final RichesseSolRepository richesseSolRepository;

    private final RichesseSolMapper richesseSolMapper;

    public RichesseSolQueryService(RichesseSolRepository richesseSolRepository, RichesseSolMapper richesseSolMapper) {
        this.richesseSolRepository = richesseSolRepository;
        this.richesseSolMapper = richesseSolMapper;
    }

    /**
     * Return a {@link List} of {@link RichesseSolDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RichesseSolDTO> findByCriteria(RichesseSolCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RichesseSol> specification = createSpecification(criteria);
        return richesseSolMapper.toDto(richesseSolRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RichesseSolDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RichesseSolDTO> findByCriteria(RichesseSolCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RichesseSol> specification = createSpecification(criteria);
        return richesseSolRepository.findAll(specification, page)
            .map(richesseSolMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RichesseSolCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RichesseSol> specification = createSpecification(criteria);
        return richesseSolRepository.count(specification);
    }

    /**
     * Function to convert RichesseSolCriteria to a {@link Specification}
     */
    private Specification<RichesseSol> createSpecification(RichesseSolCriteria criteria) {
        Specification<RichesseSol> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RichesseSol_.id));
            }
            if (criteria.getRichesseSol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRichesseSol(), RichesseSol_.richesseSol));
            }
        }
        return specification;
    }
}
