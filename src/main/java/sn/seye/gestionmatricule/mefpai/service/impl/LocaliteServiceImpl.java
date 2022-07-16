package sn.seye.gestionmatricule.mefpai.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.domain.Localite;
import sn.seye.gestionmatricule.mefpai.repository.LocaliteRepository;
import sn.seye.gestionmatricule.mefpai.service.LocaliteService;

/**
 * Service Implementation for managing {@link Localite}.
 */
@Service
@Transactional
public class LocaliteServiceImpl implements LocaliteService {

    private final Logger log = LoggerFactory.getLogger(LocaliteServiceImpl.class);

    private final LocaliteRepository localiteRepository;

    public LocaliteServiceImpl(LocaliteRepository localiteRepository) {
        this.localiteRepository = localiteRepository;
    }

    @Override
    public Localite save(Localite localite) {
        log.debug("Request to save Localite : {}", localite);
        return localiteRepository.save(localite);
    }

    @Override
    public Optional<Localite> partialUpdate(Localite localite) {
        log.debug("Request to partially update Localite : {}", localite);

        return localiteRepository
            .findById(localite.getId())
            .map(existingLocalite -> {
                if (localite.getRegion() != null) {
                    existingLocalite.setRegion(localite.getRegion());
                }
                if (localite.getDepartement() != null) {
                    existingLocalite.setDepartement(localite.getDepartement());
                }
                if (localite.getCommune() != null) {
                    existingLocalite.setCommune(localite.getCommune());
                }

                return existingLocalite;
            })
            .map(localiteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Localite> findAll(Pageable pageable) {
        log.debug("Request to get all Localites");
        return localiteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Localite> findOne(Long id) {
        log.debug("Request to get Localite : {}", id);
        return localiteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Localite : {}", id);
        localiteRepository.deleteById(id);
    }
}
