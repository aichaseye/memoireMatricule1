package sn.seye.gestionmatricule.mefpai.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.domain.EtatDemande;
import sn.seye.gestionmatricule.mefpai.repository.EtatDemandeRepository;
import sn.seye.gestionmatricule.mefpai.service.EtatDemandeService;

/**
 * Service Implementation for managing {@link EtatDemande}.
 */
@Service
@Transactional
public class EtatDemandeServiceImpl implements EtatDemandeService {

    private final Logger log = LoggerFactory.getLogger(EtatDemandeServiceImpl.class);

    private final EtatDemandeRepository etatDemandeRepository;

    public EtatDemandeServiceImpl(EtatDemandeRepository etatDemandeRepository) {
        this.etatDemandeRepository = etatDemandeRepository;
    }

    @Override
    public EtatDemande save(EtatDemande etatDemande) {
        log.debug("Request to save EtatDemande : {}", etatDemande);
        return etatDemandeRepository.save(etatDemande);
    }

    @Override
    public Optional<EtatDemande> partialUpdate(EtatDemande etatDemande) {
        log.debug("Request to partially update EtatDemande : {}", etatDemande);

        return etatDemandeRepository
            .findById(etatDemande.getId())
            .map(existingEtatDemande -> {
                if (etatDemande.getMatricule() != null) {
                    existingEtatDemande.setMatricule(etatDemande.getMatricule());
                }
                if (etatDemande.getTypeDossier() != null) {
                    existingEtatDemande.setTypeDossier(etatDemande.getTypeDossier());
                }
                if (etatDemande.getStatus() != null) {
                    existingEtatDemande.setStatus(etatDemande.getStatus());
                }

                return existingEtatDemande;
            })
            .map(etatDemandeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EtatDemande> findAll(Pageable pageable) {
        log.debug("Request to get all EtatDemandes");
        return etatDemandeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EtatDemande> findOne(Long id) {
        log.debug("Request to get EtatDemande : {}", id);
        return etatDemandeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatDemande : {}", id);
        etatDemandeRepository.deleteById(id);
    }
}
