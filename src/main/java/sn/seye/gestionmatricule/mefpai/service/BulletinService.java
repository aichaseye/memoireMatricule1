package sn.seye.gestionmatricule.mefpai.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.seye.gestionmatricule.mefpai.domain.Bulletin;

/**
 * Service Interface for managing {@link Bulletin}.
 */
public interface BulletinService {
    /**
     * Save a bulletin.
     *
     * @param bulletin the entity to save.
     * @return the persisted entity.
     */
    Bulletin save(Bulletin bulletin);

    /**
     * Partially updates a bulletin.
     *
     * @param bulletin the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Bulletin> partialUpdate(Bulletin bulletin);

    /**
     * Get all the bulletins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Bulletin> findAll(Pageable pageable);

    /**
     * Get all the bulletins with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Bulletin> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" bulletin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bulletin> findOne(Long id);

    /**
     * Delete the "id" bulletin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
