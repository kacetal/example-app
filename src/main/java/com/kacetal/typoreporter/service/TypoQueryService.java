package com.kacetal.typoreporter.service;

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

import com.kacetal.typoreporter.domain.Typo;
import com.kacetal.typoreporter.domain.*; // for static metamodels
import com.kacetal.typoreporter.repository.TypoRepository;
import com.kacetal.typoreporter.service.dto.TypoCriteria;
import com.kacetal.typoreporter.service.dto.TypoDTO;
import com.kacetal.typoreporter.service.mapper.TypoMapper;

/**
 * Service for executing complex queries for {@link Typo} entities in the database.
 * The main input is a {@link TypoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypoDTO} or a {@link Page} of {@link TypoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypoQueryService extends QueryService<Typo> {

    private final Logger log = LoggerFactory.getLogger(TypoQueryService.class);

    private final TypoRepository typoRepository;

    private final TypoMapper typoMapper;

    public TypoQueryService(TypoRepository typoRepository, TypoMapper typoMapper) {
        this.typoRepository = typoRepository;
        this.typoMapper = typoMapper;
    }

    /**
     * Return a {@link List} of {@link TypoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypoDTO> findByCriteria(TypoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Typo> specification = createSpecification(criteria);
        return typoMapper.toDto(typoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypoDTO> findByCriteria(TypoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Typo> specification = createSpecification(criteria);
        return typoRepository.findAll(specification, page)
            .map(typoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Typo> specification = createSpecification(criteria);
        return typoRepository.count(specification);
    }

    /**
     * Function to convert {@link TypoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Typo> createSpecification(TypoCriteria criteria) {
        Specification<Typo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Typo_.id));
            }
            if (criteria.getPageURL() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPageURL(), Typo_.pageURL));
            }
            if (criteria.getReporterName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReporterName(), Typo_.reporterName));
            }
            if (criteria.getReporterComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReporterComment(), Typo_.reporterComment));
            }
            if (criteria.getTextBeforeTypo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextBeforeTypo(), Typo_.textBeforeTypo));
            }
            if (criteria.getTextTypo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextTypo(), Typo_.textTypo));
            }
            if (criteria.getTextAfterTypo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextAfterTypo(), Typo_.textAfterTypo));
            }
            if (criteria.getCorrectionStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrectionStatus(), Typo_.correctionStatus));
            }
        }
        return specification;
    }
}
