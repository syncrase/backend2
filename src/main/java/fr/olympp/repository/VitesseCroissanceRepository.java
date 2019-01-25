package fr.olympp.repository;

import fr.olympp.domain.VitesseCroissance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VitesseCroissance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VitesseCroissanceRepository extends JpaRepository<VitesseCroissance, Long>, JpaSpecificationExecutor<VitesseCroissance> {

}
