package sn.seye.gestionmatricule.mefpai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.seye.gestionmatricule.mefpai.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
