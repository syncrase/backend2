package fr.olympp.repository;

import fr.olympp.domain.Espece;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Espece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspeceRepository extends JpaRepository<Espece, Long>, JpaSpecificationExecutor<Espece> {

}
