package at.meroff.bac.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import at.meroff.bac.domain.Field;
import at.meroff.bac.domain.*; // for static metamodels
import at.meroff.bac.repository.FieldRepository;
import at.meroff.bac.service.dto.FieldCriteria;

import at.meroff.bac.service.dto.FieldDTO;
import at.meroff.bac.service.mapper.FieldMapper;
import at.meroff.bac.domain.enumeration.LayoutType;

/**
 * Service for executing complex queries for Field entities in the database.
 * The main input is a {@link FieldCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FieldDTO} or a {@link Page} of {@link FieldDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FieldQueryService extends QueryService<Field> {

    private final Logger log = LoggerFactory.getLogger(FieldQueryService.class);


    private final FieldRepository fieldRepository;

    private final FieldMapper fieldMapper;

    public FieldQueryService(FieldRepository fieldRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    /**
     * Return a {@link List} of {@link FieldDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FieldDTO> findByCriteria(FieldCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Field> specification = createSpecification(criteria);
        return fieldMapper.toDto(fieldRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FieldDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FieldDTO> findByCriteria(FieldCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Field> specification = createSpecification(criteria);
        final Page<Field> result = fieldRepository.findAll(specification, page);
        return result.map(fieldMapper::toDto);
    }

    /**
     * Function to convert FieldCriteria to a {@link Specifications}
     */
    private Specifications<Field> createSpecification(FieldCriteria criteria) {
        Specifications<Field> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Field_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Field_.description));
            }
            if (criteria.getLayoutType() != null) {
                specification = specification.and(buildSpecification(criteria.getLayoutType(), Field_.layoutType));
            }
            if (criteria.getCardId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCardId(), Field_.cards, Card_.id));
            }
        }
        return specification;
    }

}
