package at.meroff.bac.service.mapper;

import at.meroff.bac.domain.*;
import at.meroff.bac.service.dto.ConnectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Connection and its DTO ConnectionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConnectionMapper extends EntityMapper<ConnectionDTO, Connection> {



    default Connection fromId(Long id) {
        if (id == null) {
            return null;
        }
        Connection connection = new Connection();
        connection.setId(id);
        return connection;
    }
}
