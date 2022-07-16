package sn.seye.gestionmatricule.mefpai.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.seye.gestionmatricule.mefpai.domain.DemandeDossier;

/**
 * Service Interface for managing {@link DemandeDossier}.
 */
public interface DemandeDossierService {
    /**
     * Save a demandeDossier.
     *
     * @param demandeDossier the entity to save.
     * @return the persisted entity.
     */
    DemandeDossier save(DemandeDossier demandeDossier);

    /**
     * Partially updates a demandeDossier.
     *
     * @param demandeDossier the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandeDossier> partialUpdate(DemandeDossier demandeDossier);

    /**
     * Get all the demandeDossiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandeDossier> findAll(Pageable pageable);

    /**
     * Get the "id" demandeDossier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandeDossier> findOne(Long id);

    /**
     * Delete the "id" demandeDossier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
