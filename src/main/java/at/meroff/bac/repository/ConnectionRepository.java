package at.meroff.bac.repository;

import at.meroff.bac.domain.Connection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Connection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long>, JpaSpecificationExecutor<Connection> {

}
