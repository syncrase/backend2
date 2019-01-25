package fr.olympp.repository;

import fr.olympp.domain.Ordre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ordre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdreRepository extends JpaRepository<Ordre, Long>, JpaSpecificationExecutor<Ordre> {

}
