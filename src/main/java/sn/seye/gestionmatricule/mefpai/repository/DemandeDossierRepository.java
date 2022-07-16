package sn.seye.gestionmatricule.mefpai.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.DemandeDossier;

/**
 * Spring Data SQL repository for the DemandeDossier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeDossierRepository extends JpaRepository<DemandeDossier, Long> {}
