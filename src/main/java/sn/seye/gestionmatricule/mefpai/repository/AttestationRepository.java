package sn.seye.gestionmatricule.mefpai.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.Attestation;

/**
 * Spring Data SQL repository for the Attestation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttestationRepository extends JpaRepository<Attestation, Long> {}
