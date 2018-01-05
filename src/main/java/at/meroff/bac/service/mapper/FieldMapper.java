package at.meroff.bac.service.mapper;

import at.meroff.bac.domain.*;
import at.meroff.bac.service.dto.FieldDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Field and its DTO FieldDTO.
 */
@Mapper(componentModel = "spring", uses = {CardMapper.class})
public interface FieldMapper extends EntityMapper<FieldDTO, Field> {

    FieldDTO toDto(Field field);

    @Mapping(target = "cards", ignore = true)
    Field toEntity(FieldDTO fieldDTO);

    default Field fromId(Long id) {
        if (id == null) {
            return null;
        }
        Field field = new Field();
        field.setId(id);
        return field;
    }
}
