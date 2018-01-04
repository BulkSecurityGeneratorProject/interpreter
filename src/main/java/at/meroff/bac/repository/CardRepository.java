package at.meroff.bac.repository;

import at.meroff.bac.domain.Card;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the Card entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {

    @Query("select c from Card c where c.field.id = (:fieldId)")
    Set<Card> findByField_Id(@Param("fieldId") Long fieldId);

}
