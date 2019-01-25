package fr.olympp.repository;

import fr.olympp.domain.Famille;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Famille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilleRepository extends JpaRepository<Famille, Long>, JpaSpecificationExecutor<Famille> {

}
