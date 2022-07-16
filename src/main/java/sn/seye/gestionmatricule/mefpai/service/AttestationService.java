package sn.seye.gestionmatricule.mefpai.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.seye.gestionmatricule.mefpai.domain.Attestation;

/**
 * Service Interface for managing {@link Attestation}.
 */
public interface AttestationService {
    /**
     * Save a attestation.
     *
     * @param attestation the entity to save.
     * @return the persisted entity.
     */
    Attestation save(Attestation attestation);

    /**
     * Partially updates a attestation.
     *
     * @param attestation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Attestation> partialUpdate(Attestation attestation);

    /**
     * Get all the attestations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Attestation> findAll(Pageable pageable);

    /**
     * Get the "id" attestation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Attestation> findOne(Long id);

    /**
     * Delete the "id" attestation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
