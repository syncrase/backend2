package fr.olympp.repository;

import fr.olympp.domain.Floraison;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Floraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FloraisonRepository extends JpaRepository<Floraison, Long>, JpaSpecificationExecutor<Floraison> {

}
