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

import at.meroff.bac.domain.Card;
import at.meroff.bac.domain.*; // for static metamodels
import at.meroff.bac.repository.CardRepository;
import at.meroff.bac.service.dto.CardCriteria;

import at.meroff.bac.service.dto.CardDTO;
import at.meroff.bac.service.mapper.CardMapper;
import at.meroff.bac.domain.enumeration.CardType;

/**
 * Service for executing complex queries for Card entities in the database.
 * The main input is a {@link CardCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CardDTO} or a {@link Page} of {@link CardDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CardQueryService extends QueryService<Card> {

    private final Logger log = LoggerFactory.getLogger(CardQueryService.class);


    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    public CardQueryService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    /**
     * Return a {@link List} of {@link CardDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CardDTO> findByCriteria(CardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Card> specification = createSpecification(criteria);
        return cardMapper.toDto(cardRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CardDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CardDTO> findByCriteria(CardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Card> specification = createSpecification(criteria);
        final Page<Card> result = cardRepository.findAll(specification, page);
        return result.map(cardMapper::toDto);
    }

    /**
     * Function to convert CardCriteria to a {@link Specifications}
     */
    private Specifications<Card> createSpecification(CardCriteria criteria) {
        Specifications<Card> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Card_.id));
            }
            if (criteria.getCardId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCardId(), Card_.cardId));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Card_.description));
            }
            if (criteria.getCardType() != null) {
                specification = specification.and(buildSpecification(criteria.getCardType(), Card_.cardType));
            }
            if (criteria.getx1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getx1(), Card_.x1));
            }
            if (criteria.gety1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gety1(), Card_.y1));
            }
            if (criteria.getx2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getx2(), Card_.x2));
            }
            if (criteria.gety2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gety2(), Card_.y2));
            }
            if (criteria.getx3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getx3(), Card_.x3));
            }
            if (criteria.gety3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gety3(), Card_.y3));
            }
            if (criteria.getx4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getx4(), Card_.x4));
            }
            if (criteria.gety4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gety4(), Card_.y4));
            }
            if (criteria.getFieldId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFieldId(), Card_.field, Field_.id));
            }
        }
        return specification;
    }

}
