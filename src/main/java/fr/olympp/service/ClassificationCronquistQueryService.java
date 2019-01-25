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

import fr.olympp.domain.ClassificationCronquist;
import fr.olympp.domain.*; // for static metamodels
import fr.olympp.repository.ClassificationCronquistRepository;
import fr.olympp.service.dto.ClassificationCronquistCriteria;
import fr.olympp.service.dto.ClassificationCronquistDTO;
import fr.olympp.service.mapper.ClassificationCronquistMapper;

/**
 * Service for executing complex queries for ClassificationCronquist entities in the database.
 * The main input is a {@link ClassificationCronquistCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClassificationCronquistDTO} or a {@link Page} of {@link ClassificationCronquistDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassificationCronquistQueryService extends QueryService<ClassificationCronquist> {

    private final Logger log = LoggerFactory.getLogger(ClassificationCronquistQueryService.class);

    private final ClassificationCronquistRepository classificationCronquistRepository;

    private final ClassificationCronquistMapper classificationCronquistMapper;

    public ClassificationCronquistQueryService(ClassificationCronquistRepository classificationCronquistRepository, ClassificationCronquistMapper classificationCronquistMapper) {
        this.classificationCronquistRepository = classificationCronquistRepository;
        this.classificationCronquistMapper = classificationCronquistMapper;
    }

    /**
     * Return a {@link List} of {@link ClassificationCronquistDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassificationCronquistDTO> findByCriteria(ClassificationCronquistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClassificationCronquist> specification = createSpecification(criteria);
        return classificationCronquistMapper.toDto(classificationCronquistRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClassificationCronquistDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassificationCronquistDTO> findByCriteria(ClassificationCronquistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassificationCronquist> specification = createSpecification(criteria);
        return classificationCronquistRepository.findAll(specification, page)
            .map(classificationCronquistMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassificationCronquistCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClassificationCronquist> specification = createSpecification(criteria);
        return classificationCronquistRepository.count(specification);
    }

    /**
     * Function to convert ClassificationCronquistCriteria to a {@link Specification}
     */
    private Specification<ClassificationCronquist> createSpecification(ClassificationCronquistCriteria criteria) {
        Specification<ClassificationCronquist> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassificationCronquist_.id));
            }
            if (criteria.getOrdreId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrdreId(),
                    root -> root.join(ClassificationCronquist_.ordre, JoinType.LEFT).get(Ordre_.id)));
            }
            if (criteria.getFamilleId() != null) {
                specification = specification.and(buildSpecification(criteria.getFamilleId(),
                    root -> root.join(ClassificationCronquist_.famille, JoinType.LEFT).get(Famille_.id)));
            }
            if (criteria.getGenreId() != null) {
                specification = specification.and(buildSpecification(criteria.getGenreId(),
                    root -> root.join(ClassificationCronquist_.genre, JoinType.LEFT).get(Genre_.id)));
            }
            if (criteria.getEspeceId() != null) {
                specification = specification.and(buildSpecification(criteria.getEspeceId(),
                    root -> root.join(ClassificationCronquist_.espece, JoinType.LEFT).get(Espece_.id)));
            }
        }
        return specification;
    }
}
