package sn.seye.gestionmatricule.mefpai.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.domain.DemandeDossier;
import sn.seye.gestionmatricule.mefpai.repository.DemandeDossierRepository;
import sn.seye.gestionmatricule.mefpai.service.DemandeDossierService;

/**
 * Service Implementation for managing {@link DemandeDossier}.
 */
@Service
@Transactional
public class DemandeDossierServiceImpl implements DemandeDossierService {

    private final Logger log = LoggerFactory.getLogger(DemandeDossierServiceImpl.class);

    private final DemandeDossierRepository demandeDossierRepository;

    public DemandeDossierServiceImpl(DemandeDossierRepository demandeDossierRepository) {
        this.demandeDossierRepository = demandeDossierRepository;
    }

    @Override
    public DemandeDossier save(DemandeDossier demandeDossier) {
        log.debug("Request to save DemandeDossier : {}", demandeDossier);
        return demandeDossierRepository.save(demandeDossier);
    }

    @Override
    public Optional<DemandeDossier> partialUpdate(DemandeDossier demandeDossier) {
        log.debug("Request to partially update DemandeDossier : {}", demandeDossier);

        return demandeDossierRepository
            .findById(demandeDossier.getId())
            .map(existingDemandeDossier -> {
                if (demandeDossier.getNom() != null) {
                    existingDemandeDossier.setNom(demandeDossier.getNom());
                }
                if (demandeDossier.getPrenom() != null) {
                    existingDemandeDossier.setPrenom(demandeDossier.getPrenom());
                }
                if (demandeDossier.getEmail() != null) {
                    existingDemandeDossier.setEmail(demandeDossier.getEmail());
                }
                if (demandeDossier.getDateNaissance() != null) {
                    existingDemandeDossier.setDateNaissance(demandeDossier.getDateNaissance());
                }
                if (demandeDossier.getMatricule() != null) {
                    existingDemandeDossier.setMatricule(demandeDossier.getMatricule());
                }
                if (demandeDossier.getTypeDossier() != null) {
                    existingDemandeDossier.setTypeDossier(demandeDossier.getTypeDossier());
                }
                if (demandeDossier.getAnnee() != null) {
                    existingDemandeDossier.setAnnee(demandeDossier.getAnnee());
                }
                if (demandeDossier.getSerie() != null) {
                    existingDemandeDossier.setSerie(demandeDossier.getSerie());
                }
                if (demandeDossier.getFiliere() != null) {
                    existingDemandeDossier.setFiliere(demandeDossier.getFiliere());
                }
                if (demandeDossier.getNomSemestre() != null) {
                    existingDemandeDossier.setNomSemestre(demandeDossier.getNomSemestre());
                }
                if (demandeDossier.getNiveau() != null) {
                    existingDemandeDossier.setNiveau(demandeDossier.getNiveau());
                }
                if (demandeDossier.getTypeReleve() != null) {
                    existingDemandeDossier.setTypeReleve(demandeDossier.getTypeReleve());
                }
                if (demandeDossier.getNomDiplome() != null) {
                    existingDemandeDossier.setNomDiplome(demandeDossier.getNomDiplome());
                }
                if (demandeDossier.getPhoto() != null) {
                    existingDemandeDossier.setPhoto(demandeDossier.getPhoto());
                }
                if (demandeDossier.getPhotoContentType() != null) {
                    existingDemandeDossier.setPhotoContentType(demandeDossier.getPhotoContentType());
                }

                return existingDemandeDossier;
            })
            .map(demandeDossierRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandeDossier> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeDossiers");
        return demandeDossierRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeDossier> findOne(Long id) {
        log.debug("Request to get DemandeDossier : {}", id);
        return demandeDossierRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandeDossier : {}", id);
        demandeDossierRepository.deleteById(id);
    }
}
