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

import fr.olympp.domain.Espece;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.EspeceRepository;
import fr.olympp.service.dto.EspeceCriteria;
import fr.olympp.service.dto.EspeceDTO;
import fr.olympp.service.mapper.EspeceMapper;

/**
 * Service for executing complex queries for Espece entities in the database.
 * The main input is a {@link EspeceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EspeceDTO} or a {@link Page} of {@link EspeceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EspeceQueryService extends QueryService<Espece> {

    private final Logger log = LoggerFactory.getLogger(EspeceQueryService.class);

    private final EspeceRepository especeRepository;

    private final EspeceMapper especeMapper;

    public EspeceQueryService(EspeceRepository especeRepository, EspeceMapper especeMapper) {
        this.especeRepository = especeRepository;
        this.especeMapper = especeMapper;
    }

    /**
     * Return a {@link List} of {@link EspeceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EspeceDTO> findByCriteria(EspeceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Espece> specification = createSpecification(criteria);
        return especeMapper.toDto(especeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EspeceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EspeceDTO> findByCriteria(EspeceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Espece> specification = createSpecification(criteria);
        return especeRepository.findAll(specification, page)
            .map(especeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EspeceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Espece> specification = createSpecification(criteria);
        return especeRepository.count(specification);
    }

    /**
     * Function to convert EspeceCriteria to a {@link Specification}
     */
    private Specification<Espece> createSpecification(EspeceCriteria criteria) {
        Specification<Espece> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Espece_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Espece_.name));
            }
        }
        return specification;
    }
}
