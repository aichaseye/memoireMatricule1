package sn.seye.gestionmatricule.mefpai.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import sn.seye.gestionmatricule.mefpai.domain.Programme;

public interface ProgrammeRepositoryWithBagRelationships {
    Optional<Programme> fetchBagRelationships(Optional<Programme> programme);

    List<Programme> fetchBagRelationships(List<Programme> programmes);

    Page<Programme> fetchBagRelationships(Page<Programme> programmes);
}
