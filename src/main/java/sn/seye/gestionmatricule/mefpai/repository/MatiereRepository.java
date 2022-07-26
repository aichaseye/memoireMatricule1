package sn.seye.gestionmatricule.mefpai.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.Matiere;

/**
 * Spring Data SQL repository for the Matiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {}
