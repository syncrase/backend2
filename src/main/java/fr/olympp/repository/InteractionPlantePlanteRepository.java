package fr.olympp.repository;

import fr.olympp.domain.InteractionPlantePlante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InteractionPlantePlante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InteractionPlantePlanteRepository extends JpaRepository<InteractionPlantePlante, Long>, JpaSpecificationExecutor<InteractionPlantePlante> {

}
