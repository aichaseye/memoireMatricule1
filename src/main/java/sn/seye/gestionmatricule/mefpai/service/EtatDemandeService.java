package sn.seye.gestionmatricule.mefpai.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.seye.gestionmatricule.mefpai.domain.EtatDemande;

/**
 * Service Interface for managing {@link EtatDemande}.
 */
public interface EtatDemandeService {
    /**
     * Save a etatDemande.
     *
     * @param etatDemande the entity to save.
     * @return the persisted entity.
     */
    EtatDemande save(EtatDemande etatDemande);

    /**
     * Partially updates a etatDemande.
     *
     * @param etatDemande the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EtatDemande> partialUpdate(EtatDemande etatDemande);

    /**
     * Get all the etatDemandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtatDemande> findAll(Pageable pageable);

    /**
     * Get the "id" etatDemande.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtatDemande> findOne(Long id);

    /**
     * Delete the "id" etatDemande.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
