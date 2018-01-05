package at.meroff.bac.service.mapper;

import at.meroff.bac.domain.Field;
import at.meroff.bac.service.dto.FieldDTO;
import at.meroff.bac.service.dto.FieldDTOSmall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Field and its DTO FieldDTO.
 */
@Mapper(componentModel = "spring", uses = {CardMapper.class})
public interface FieldSmallMapper extends EntityMapper<FieldDTOSmall, Field> {

    FieldDTOSmall toDto(Field field);

    @Mapping(target = "cards", ignore = true)
    Field toEntity(FieldDTOSmall fieldDTO);

    default Field fromId(Long id) {
        if (id == null) {
            return null;
        }
        Field field = new Field();
        field.setId(id);
        return field;
    }
}
