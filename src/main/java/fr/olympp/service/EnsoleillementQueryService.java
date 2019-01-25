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

import fr.olympp.domain.Ensoleillement;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.EnsoleillementRepository;
import fr.olympp.service.dto.EnsoleillementCriteria;
import fr.olympp.service.dto.EnsoleillementDTO;
import fr.olympp.service.mapper.EnsoleillementMapper;

/**
 * Service for executing complex queries for Ensoleillement entities in the database.
 * The main input is a {@link EnsoleillementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnsoleillementDTO} or a {@link Page} of {@link EnsoleillementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnsoleillementQueryService extends QueryService<Ensoleillement> {

    private final Logger log = LoggerFactory.getLogger(EnsoleillementQueryService.class);

    private final EnsoleillementRepository ensoleillementRepository;

    private final EnsoleillementMapper ensoleillementMapper;

    public EnsoleillementQueryService(EnsoleillementRepository ensoleillementRepository, EnsoleillementMapper ensoleillementMapper) {
        this.ensoleillementRepository = ensoleillementRepository;
        this.ensoleillementMapper = ensoleillementMapper;
    }

    /**
     * Return a {@link List} of {@link EnsoleillementDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnsoleillementDTO> findByCriteria(EnsoleillementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ensoleillement> specification = createSpecification(criteria);
        return ensoleillementMapper.toDto(ensoleillementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnsoleillementDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnsoleillementDTO> findByCriteria(EnsoleillementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ensoleillement> specification = createSpecification(criteria);
        return ensoleillementRepository.findAll(specification, page)
            .map(ensoleillementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnsoleillementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ensoleillement> specification = createSpecification(criteria);
        return ensoleillementRepository.count(specification);
    }

    /**
     * Function to convert EnsoleillementCriteria to a {@link Specification}
     */
    private Specification<Ensoleillement> createSpecification(EnsoleillementCriteria criteria) {
        Specification<Ensoleillement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Ensoleillement_.id));
            }
            if (criteria.getEnsoleillement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnsoleillement(), Ensoleillement_.ensoleillement));
            }
        }
        return specification;
    }
}
