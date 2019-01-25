package fr.olympp.repository;

import fr.olympp.domain.Strate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Strate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrateRepository extends JpaRepository<Strate, Long>, JpaSpecificationExecutor<Strate> {

}
