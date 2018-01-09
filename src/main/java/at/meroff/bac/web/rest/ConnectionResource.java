package at.meroff.bac.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.bac.service.ConnectionService;
import at.meroff.bac.web.rest.errors.BadRequestAlertException;
import at.meroff.bac.web.rest.util.HeaderUtil;
import at.meroff.bac.service.dto.ConnectionDTO;
import at.meroff.bac.service.dto.ConnectionCriteria;
import at.meroff.bac.service.ConnectionQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Connection.
 */
@RestController
@RequestMapping("/api")
public class ConnectionResource {

    private final Logger log = LoggerFactory.getLogger(ConnectionResource.class);

    private static final String ENTITY_NAME = "connection";

    private final ConnectionService connectionService;

    private final ConnectionQueryService connectionQueryService;

    public ConnectionResource(ConnectionService connectionService, ConnectionQueryService connectionQueryService) {
        this.connectionService = connectionService;
        this.connectionQueryService = connectionQueryService;
    }

    /**
     * POST  /connections : Create a new connection.
     *
     * @param connectionDTO the connectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new connectionDTO, or with status 400 (Bad Request) if the connection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/connections")
    @Timed
    public ResponseEntity<ConnectionDTO> createConnection(@RequestBody ConnectionDTO connectionDTO) throws URISyntaxException {
        log.debug("REST request to save Connection : {}", connectionDTO);
        if (connectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new connection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectionDTO result = connectionService.save(connectionDTO);
        return ResponseEntity.created(new URI("/api/connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /connections : Updates an existing connection.
     *
     * @param connectionDTO the connectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated connectionDTO,
     * or with status 400 (Bad Request) if the connectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the connectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/connections")
    @Timed
    public ResponseEntity<ConnectionDTO> updateConnection(@RequestBody ConnectionDTO connectionDTO) throws URISyntaxException {
        log.debug("REST request to update Connection : {}", connectionDTO);
        if (connectionDTO.getId() == null) {
            return createConnection(connectionDTO);
        }
        ConnectionDTO result = connectionService.save(connectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, connectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /connections : get all the connections.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of connections in body
     */
    @GetMapping("/connections")
    @Timed
    public ResponseEntity<List<ConnectionDTO>> getAllConnections(ConnectionCriteria criteria) {
        log.debug("REST request to get Connections by criteria: {}", criteria);
        List<ConnectionDTO> entityList = connectionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /connections/:id : get the "id" connection.
     *
     * @param id the id of the connectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the connectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/connections/{id}")
    @Timed
    public ResponseEntity<ConnectionDTO> getConnection(@PathVariable Long id) {
        log.debug("REST request to get Connection : {}", id);
        ConnectionDTO connectionDTO = connectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(connectionDTO));
    }

    /**
     * DELETE  /connections/:id : delete the "id" connection.
     *
     * @param id the id of the connectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/connections/{id}")
    @Timed
    public ResponseEntity<Void> deleteConnection(@PathVariable Long id) {
        log.debug("REST request to delete Connection : {}", id);
        connectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /cards : get all the cards.
     *
     * @param fieldId the field id
     * @return the ResponseEntity with status 200 (OK) and the list of cards in body
     */
    @GetMapping("/connections/byfieldid/{fieldId}")
    @Timed
    public ResponseEntity<List<ConnectionDTO>> getAllConnectionsByField_Id(@PathVariable Long fieldId) {
        log.debug("REST request to get Connections by Field Id: {}", fieldId);
        List<ConnectionDTO> entityList = connectionService.findByField_Id(fieldId);
        return ResponseEntity.ok().body(entityList);
    }
}
