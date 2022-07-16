package sn.seye.gestionmatricule.mefpai.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.Localite;

/**
 * Spring Data SQL repository for the Localite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocaliteRepository extends JpaRepository<Localite, Long> {}
