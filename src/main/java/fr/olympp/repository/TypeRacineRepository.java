package fr.olympp.repository;

import fr.olympp.domain.TypeRacine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeRacine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRacineRepository extends JpaRepository<TypeRacine, Long>, JpaSpecificationExecutor<TypeRacine> {

}
