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

import at.meroff.bac.domain.Connection;
import at.meroff.bac.domain.*; // for static metamodels
import at.meroff.bac.repository.ConnectionRepository;
import at.meroff.bac.service.dto.ConnectionCriteria;

import at.meroff.bac.service.dto.ConnectionDTO;
import at.meroff.bac.service.mapper.ConnectionMapper;

/**
 * Service for executing complex queries for Connection entities in the database.
 * The main input is a {@link ConnectionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConnectionDTO} or a {@link Page} of {@link ConnectionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConnectionQueryService extends QueryService<Connection> {

    private final Logger log = LoggerFactory.getLogger(ConnectionQueryService.class);


    private final ConnectionRepository connectionRepository;

    private final ConnectionMapper connectionMapper;

    public ConnectionQueryService(ConnectionRepository connectionRepository, ConnectionMapper connectionMapper) {
        this.connectionRepository = connectionRepository;
        this.connectionMapper = connectionMapper;
    }

    /**
     * Return a {@link List} of {@link ConnectionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConnectionDTO> findByCriteria(ConnectionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Connection> specification = createSpecification(criteria);
        return connectionMapper.toDto(connectionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConnectionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnectionDTO> findByCriteria(ConnectionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Connection> specification = createSpecification(criteria);
        final Page<Connection> result = connectionRepository.findAll(specification, page);
        return result.map(connectionMapper::toDto);
    }

    /**
     * Function to convert ConnectionCriteria to a {@link Specifications}
     */
    private Specifications<Connection> createSpecification(ConnectionCriteria criteria) {
        Specifications<Connection> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Connection_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUuid(), Connection_.uuid));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Connection_.name));
            }
            if (criteria.getEndPoint1Uuid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndPoint1Uuid(), Connection_.endPoint1Uuid));
            }
            if (criteria.getEndPoint1X() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndPoint1X(), Connection_.endPoint1X));
            }
            if (criteria.getEndPoint1Y() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndPoint1Y(), Connection_.endPoint1Y));
            }
            if (criteria.getEndPoint1Angle() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndPoint1Angle(), Connection_.endPoint1Angle));
            }
            if (criteria.getDirected1() != null) {
                specification = specification.and(buildSpecification(criteria.getDirected1(), Connection_.directed1));
            }
            if (criteria.getEndPoint2Uuid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndPoint2Uuid(), Connection_.endPoint2Uuid));
            }
            if (criteria.getEndPoint2X() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndPoint2X(), Connection_.endPoint2X));
            }
            if (criteria.getEndPoint2Y() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndPoint2Y(), Connection_.endPoint2Y));
            }
            if (criteria.getEndPoint2Angle() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndPoint2Angle(), Connection_.endPoint2Angle));
            }
            if (criteria.getDirected2() != null) {
                specification = specification.and(buildSpecification(criteria.getDirected2(), Connection_.directed2));
            }
        }
        return specification;
    }

}
