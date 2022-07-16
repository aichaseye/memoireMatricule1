package sn.seye.gestionmatricule.mefpai.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.EtatDemande;

/**
 * Spring Data SQL repository for the EtatDemande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatDemandeRepository extends JpaRepository<EtatDemande, Long> {}
