package sn.seye.gestionmatricule.mefpai.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.Inspection;

/**
 * Spring Data SQL repository for the Inspection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {}
