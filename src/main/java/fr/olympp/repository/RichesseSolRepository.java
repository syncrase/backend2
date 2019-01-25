package fr.olympp.repository;

import fr.olympp.domain.RichesseSol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RichesseSol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RichesseSolRepository extends JpaRepository<RichesseSol, Long>, JpaSpecificationExecutor<RichesseSol> {

}
