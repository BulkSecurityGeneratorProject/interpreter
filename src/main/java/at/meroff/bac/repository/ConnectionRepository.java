package at.meroff.bac.repository;

import at.meroff.bac.domain.Connection;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the Connection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long>, JpaSpecificationExecutor<Connection> {

    @Query("select c from Connection c where c.field.id = (:fieldId)")
    Set<Connection> findByField_Id(@Param("fieldId") Long fieldId);
}
