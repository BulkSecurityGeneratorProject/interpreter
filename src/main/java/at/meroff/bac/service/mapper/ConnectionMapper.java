package at.meroff.bac.service.mapper;

import at.meroff.bac.domain.*;
import at.meroff.bac.service.dto.ConnectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Connection and its DTO ConnectionDTO.
 */
@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public interface ConnectionMapper extends EntityMapper<ConnectionDTO, Connection> {

    @Mapping(source = "field.id", target = "fieldId")
    ConnectionDTO toDto(Connection connection);

    @Mapping(source = "fieldId", target = "field")
    Connection toEntity(ConnectionDTO connectionDTO);

    default Connection fromId(Long id) {
        if (id == null) {
            return null;
        }
        Connection connection = new Connection();
        connection.setId(id);
        return connection;
    }
}
