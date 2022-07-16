package sn.seye.gestionmatricule.mefpai.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.seye.gestionmatricule.mefpai.domain.Bulletin;

/**
 * Spring Data SQL repository for the Bulletin entity.
 */
@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {
    default Optional<Bulletin> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Bulletin> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Bulletin> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct bulletin from Bulletin bulletin left join fetch bulletin.apprenant",
        countQuery = "select count(distinct bulletin) from Bulletin bulletin"
    )
    Page<Bulletin> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct bulletin from Bulletin bulletin left join fetch bulletin.apprenant")
    List<Bulletin> findAllWithToOneRelationships();

    @Query("select bulletin from Bulletin bulletin left join fetch bulletin.apprenant where bulletin.id =:id")
    Optional<Bulletin> findOneWithToOneRelationships(@Param("id") Long id);
}
