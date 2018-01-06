package at.meroff.bac.service;

import at.meroff.bac.domain.Field;
import at.meroff.bac.repository.FieldRepository;
import at.meroff.bac.service.dto.FieldDTO;
import at.meroff.bac.service.mapper.FieldMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Field.
 */
@Service
@Transactional
public class FieldService {

    private final Logger log = LoggerFactory.getLogger(FieldService.class);

    private final FieldRepository fieldRepository;

    private final FieldMapper fieldMapper;

    public FieldService(FieldRepository fieldRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    /**
     * Save a field.
     *
     * @param fieldDTO the entity to save
     * @return the persisted entity
     */
    public FieldDTO save(FieldDTO fieldDTO) {
        log.debug("Request to save Field : {}", fieldDTO);
        Field field = fieldMapper.toEntity(fieldDTO);
        field = fieldRepository.save(field);
        return fieldMapper.toDto(field);
    }

    /**
     * Get all the fields.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<FieldDTO> findAll() {
        log.debug("Request to get all Fields");
        return fieldRepository.findAll().stream()
            .map(fieldMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one field by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FieldDTO findOne(Long id) {
        log.debug("Request to get Field : {}", id);
        Field field = fieldRepository.findOne(id);
        return fieldMapper.toDto(field);
    }

    /**
     * Delete the field by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Field : {}", id);
        fieldRepository.delete(id);
    }
}
