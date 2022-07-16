package sn.seye.gestionmatricule.mefpai.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.domain.DemandeMatApp;
import sn.seye.gestionmatricule.mefpai.repository.DemandeMatAppRepository;
import sn.seye.gestionmatricule.mefpai.service.DemandeMatAppService;

/**
 * Service Implementation for managing {@link DemandeMatApp}.
 */
@Service
@Transactional
public class DemandeMatAppServiceImpl implements DemandeMatAppService {

    private final Logger log = LoggerFactory.getLogger(DemandeMatAppServiceImpl.class);

    private final DemandeMatAppRepository demandeMatAppRepository;

    public DemandeMatAppServiceImpl(DemandeMatAppRepository demandeMatAppRepository) {
        this.demandeMatAppRepository = demandeMatAppRepository;
    }

    @Override
    public DemandeMatApp save(DemandeMatApp demandeMatApp) {
        log.debug("Request to save DemandeMatApp : {}", demandeMatApp);
        return demandeMatAppRepository.save(demandeMatApp);
    }

    @Override
    public Optional<DemandeMatApp> partialUpdate(DemandeMatApp demandeMatApp) {
        log.debug("Request to partially update DemandeMatApp : {}", demandeMatApp);

        return demandeMatAppRepository
            .findById(demandeMatApp.getId())
            .map(existingDemandeMatApp -> {
                if (demandeMatApp.getNom() != null) {
                    existingDemandeMatApp.setNom(demandeMatApp.getNom());
                }
                if (demandeMatApp.getPrenom() != null) {
                    existingDemandeMatApp.setPrenom(demandeMatApp.getPrenom());
                }
                if (demandeMatApp.getEmail() != null) {
                    existingDemandeMatApp.setEmail(demandeMatApp.getEmail());
                }
                if (demandeMatApp.getTelephone() != null) {
                    existingDemandeMatApp.setTelephone(demandeMatApp.getTelephone());
                }
                if (demandeMatApp.getDateNaissance() != null) {
                    existingDemandeMatApp.setDateNaissance(demandeMatApp.getDateNaissance());
                }
                if (demandeMatApp.getSexe() != null) {
                    existingDemandeMatApp.setSexe(demandeMatApp.getSexe());
                }
                if (demandeMatApp.getMatriculeApp() != null) {
                    existingDemandeMatApp.setMatriculeApp(demandeMatApp.getMatriculeApp());
                }

                return existingDemandeMatApp;
            })
            .map(demandeMatAppRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandeMatApp> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeMatApps");
        return demandeMatAppRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeMatApp> findOne(Long id) {
        log.debug("Request to get DemandeMatApp : {}", id);
        return demandeMatAppRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandeMatApp : {}", id);
        demandeMatAppRepository.deleteById(id);
    }
}
