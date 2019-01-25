package fr.olympp.repository;

import fr.olympp.domain.Ensoleillement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ensoleillement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnsoleillementRepository extends JpaRepository<Ensoleillement, Long>, JpaSpecificationExecutor<Ensoleillement> {

}
