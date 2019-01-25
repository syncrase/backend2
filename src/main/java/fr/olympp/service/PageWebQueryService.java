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

import fr.olympp.domain.PageWeb;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.PageWebRepository;
import fr.olympp.service.dto.PageWebCriteria;
import fr.olympp.service.dto.PageWebDTO;
import fr.olympp.service.mapper.PageWebMapper;

/**
 * Service for executing complex queries for PageWeb entities in the database.
 * The main input is a {@link PageWebCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PageWebDTO} or a {@link Page} of {@link PageWebDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PageWebQueryService extends QueryService<PageWeb> {

    private final Logger log = LoggerFactory.getLogger(PageWebQueryService.class);

    private final PageWebRepository pageWebRepository;

    private final PageWebMapper pageWebMapper;

    public PageWebQueryService(PageWebRepository pageWebRepository, PageWebMapper pageWebMapper) {
        this.pageWebRepository = pageWebRepository;
        this.pageWebMapper = pageWebMapper;
    }

    /**
     * Return a {@link List} of {@link PageWebDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PageWebDTO> findByCriteria(PageWebCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PageWeb> specification = createSpecification(criteria);
        return pageWebMapper.toDto(pageWebRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PageWebDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PageWebDTO> findByCriteria(PageWebCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PageWeb> specification = createSpecification(criteria);
        return pageWebRepository.findAll(specification, page)
            .map(pageWebMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PageWebCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PageWeb> specification = createSpecification(criteria);
        return pageWebRepository.count(specification);
    }

    /**
     * Function to convert PageWebCriteria to a {@link Specification}
     */
    private Specification<PageWeb> createSpecification(PageWebCriteria criteria) {
        Specification<PageWeb> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PageWeb_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PageWeb_.description));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), PageWeb_.url));
            }
            if (criteria.getReferenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getReferenceId(),
                    root -> root.join(PageWeb_.reference, JoinType.LEFT).get(Reference_.id)));
            }
        }
        return specification;
    }
}
