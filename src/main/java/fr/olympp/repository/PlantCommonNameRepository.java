package fr.olympp.repository;

import fr.olympp.domain.PlantCommonName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlantCommonName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantCommonNameRepository extends JpaRepository<PlantCommonName, Long>, JpaSpecificationExecutor<PlantCommonName> {

}
