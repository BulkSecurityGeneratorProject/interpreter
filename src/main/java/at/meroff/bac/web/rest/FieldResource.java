package at.meroff.bac.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.bac.service.FieldService;
import at.meroff.bac.web.rest.errors.BadRequestAlertException;
import at.meroff.bac.web.rest.util.HeaderUtil;
import at.meroff.bac.service.dto.FieldDTO;
import at.meroff.bac.service.dto.FieldCriteria;
import at.meroff.bac.service.FieldQueryService;
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
 * REST controller for managing Field.
 */
@RestController
@RequestMapping("/api")
public class FieldResource {

    private final Logger log = LoggerFactory.getLogger(FieldResource.class);

    private static final String ENTITY_NAME = "field";

    private final FieldService fieldService;

    private final FieldQueryService fieldQueryService;

    public FieldResource(FieldService fieldService, FieldQueryService fieldQueryService) {
        this.fieldService = fieldService;
        this.fieldQueryService = fieldQueryService;
    }

    /**
     * POST  /fields : Create a new field.
     *
     * @param fieldDTO the fieldDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fieldDTO, or with status 400 (Bad Request) if the field has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fields")
    @Timed
    public ResponseEntity<FieldDTO> createField(@RequestBody FieldDTO fieldDTO) throws URISyntaxException {
        log.debug("REST request to save Field : {}", fieldDTO);
        if (fieldDTO.getId() != null) {
            throw new BadRequestAlertException("A new field cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldDTO result = fieldService.save(fieldDTO);
        return ResponseEntity.created(new URI("/api/fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fields : Updates an existing field.
     *
     * @param fieldDTO the fieldDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fieldDTO,
     * or with status 400 (Bad Request) if the fieldDTO is not valid,
     * or with status 500 (Internal Server Error) if the fieldDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fields")
    @Timed
    public ResponseEntity<FieldDTO> updateField(@RequestBody FieldDTO fieldDTO) throws URISyntaxException {
        log.debug("REST request to update Field : {}", fieldDTO);
        if (fieldDTO.getId() == null) {
            return createField(fieldDTO);
        }
        FieldDTO result = fieldService.save(fieldDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fieldDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fields : get all the fields.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of fields in body
     */
    @GetMapping("/fields")
    @Timed
    public ResponseEntity<List<FieldDTO>> getAllFields(FieldCriteria criteria) {
        log.debug("REST request to get Fields by criteria: {}", criteria);
        List<FieldDTO> entityList = fieldQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /fields/:id : get the "id" field.
     *
     * @param id the id of the fieldDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fieldDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fields/{id}")
    @Timed
    public ResponseEntity<FieldDTO> getField(@PathVariable Long id) {
        log.debug("REST request to get Field : {}", id);
        FieldDTO fieldDTO = fieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fieldDTO));
    }

    /**
     * DELETE  /fields/:id : delete the "id" field.
     *
     * @param id the id of the fieldDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fields/{id}")
    @Timed
    public ResponseEntity<Void> deleteField(@PathVariable Long id) {
        log.debug("REST request to delete Field : {}", id);
        fieldService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
