package at.meroff.bac.service;

import at.meroff.bac.domain.Connection;
import at.meroff.bac.repository.ConnectionRepository;
import at.meroff.bac.service.dto.ConnectionDTO;
import at.meroff.bac.service.mapper.ConnectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Connection.
 */
@Service
@Transactional
public class ConnectionService {

    private final Logger log = LoggerFactory.getLogger(ConnectionService.class);

    private final ConnectionRepository connectionRepository;

    private final ConnectionMapper connectionMapper;

    public ConnectionService(ConnectionRepository connectionRepository, ConnectionMapper connectionMapper) {
        this.connectionRepository = connectionRepository;
        this.connectionMapper = connectionMapper;
    }

    /**
     * Save a connection.
     *
     * @param connectionDTO the entity to save
     * @return the persisted entity
     */
    public ConnectionDTO save(ConnectionDTO connectionDTO) {
        log.debug("Request to save Connection : {}", connectionDTO);
        Connection connection = connectionMapper.toEntity(connectionDTO);
        connection = connectionRepository.save(connection);
        return connectionMapper.toDto(connection);
    }

    /**
     * Get all the connections.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ConnectionDTO> findAll() {
        log.debug("Request to get all Connections");
        return connectionRepository.findAll().stream()
            .map(connectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one connection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ConnectionDTO findOne(Long id) {
        log.debug("Request to get Connection : {}", id);
        Connection connection = connectionRepository.findOne(id);
        return connectionMapper.toDto(connection);
    }

    /**
     * Delete the connection by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Connection : {}", id);
        connectionRepository.delete(id);
    }

    /**
     * Get all the cards.
     *
     * @return the list of entities by field id
     */
    @Transactional(readOnly = true)
    public List<ConnectionDTO> findByField_Id(Long fieldId) {
        log.debug("Request to get all Cards by Field Id: {}", fieldId);
        return connectionRepository.findByField_Id(fieldId).stream()
            .map(connectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
