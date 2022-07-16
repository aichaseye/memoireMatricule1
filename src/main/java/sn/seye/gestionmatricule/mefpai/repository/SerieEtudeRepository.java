package sn.seye.gestionmatricule.mefpai.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.SerieEtude;

/**
 * Spring Data SQL repository for the SerieEtude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SerieEtudeRepository extends JpaRepository<SerieEtude, Long> {}
