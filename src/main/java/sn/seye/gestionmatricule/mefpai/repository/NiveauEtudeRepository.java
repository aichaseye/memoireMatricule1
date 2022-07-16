package sn.seye.gestionmatricule.mefpai.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.NiveauEtude;

/**
 * Spring Data SQL repository for the NiveauEtude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NiveauEtudeRepository extends JpaRepository<NiveauEtude, Long> {}
