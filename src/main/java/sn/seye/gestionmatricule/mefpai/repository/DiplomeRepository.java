package sn.seye.gestionmatricule.mefpai.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.Diplome;

/**
 * Spring Data SQL repository for the Diplome entity.
 */
@Repository
public interface DiplomeRepository extends JpaRepository<Diplome, Long> {
    default Optional<Diplome> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Diplome> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Diplome> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct diplome from Diplome diplome left join fetch diplome.etablissement left join fetch diplome.apprenant",
        countQuery = "select count(distinct diplome) from Diplome diplome"
    )
    Page<Diplome> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct diplome from Diplome diplome left join fetch diplome.etablissement left join fetch diplome.apprenant")
    List<Diplome> findAllWithToOneRelationships();

    @Query(
        "select diplome from Diplome diplome left join fetch diplome.etablissement left join fetch diplome.apprenant where diplome.id =:id"
    )
    Optional<Diplome> findOneWithToOneRelationships(@Param("id") Long id);
}
