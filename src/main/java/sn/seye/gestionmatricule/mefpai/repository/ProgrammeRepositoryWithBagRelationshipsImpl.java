package sn.seye.gestionmatricule.mefpai.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import sn.seye.gestionmatricule.mefpai.domain.Programme;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProgrammeRepositoryWithBagRelationshipsImpl implements ProgrammeRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Programme> fetchBagRelationships(Optional<Programme> programme) {
        return programme.map(this::fetchReleveNotes);
    }

    @Override
    public Page<Programme> fetchBagRelationships(Page<Programme> programmes) {
        return new PageImpl<>(fetchBagRelationships(programmes.getContent()), programmes.getPageable(), programmes.getTotalElements());
    }

    @Override
    public List<Programme> fetchBagRelationships(List<Programme> programmes) {
        return Optional.of(programmes).map(this::fetchReleveNotes).get();
    }

    Programme fetchReleveNotes(Programme result) {
        return entityManager
            .createQuery(
                "select programme from Programme programme left join fetch programme.releveNotes where programme is :programme",
                Programme.class
            )
            .setParameter("programme", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Programme> fetchReleveNotes(List<Programme> programmes) {
        return entityManager
            .createQuery(
                "select distinct programme from Programme programme left join fetch programme.releveNotes where programme in :programmes",
                Programme.class
            )
            .setParameter("programmes", programmes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
