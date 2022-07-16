package sn.seye.gestionmatricule.mefpai.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.domain.Attestation;
import sn.seye.gestionmatricule.mefpai.repository.AttestationRepository;
import sn.seye.gestionmatricule.mefpai.service.AttestationService;

/**
 * Service Implementation for managing {@link Attestation}.
 */
@Service
@Transactional
public class AttestationServiceImpl implements AttestationService {

    private final Logger log = LoggerFactory.getLogger(AttestationServiceImpl.class);

    private final AttestationRepository attestationRepository;

    public AttestationServiceImpl(AttestationRepository attestationRepository) {
        this.attestationRepository = attestationRepository;
    }

    @Override
    public Attestation save(Attestation attestation) {
        log.debug("Request to save Attestation : {}", attestation);
        return attestationRepository.save(attestation);
    }

    @Override
    public Optional<Attestation> partialUpdate(Attestation attestation) {
        log.debug("Request to partially update Attestation : {}", attestation);

        return attestationRepository
            .findById(attestation.getId())
            .map(existingAttestation -> {
                if (attestation.getPhoto() != null) {
                    existingAttestation.setPhoto(attestation.getPhoto());
                }
                if (attestation.getPhotoContentType() != null) {
                    existingAttestation.setPhotoContentType(attestation.getPhotoContentType());
                }

                return existingAttestation;
            })
            .map(attestationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Attestation> findAll(Pageable pageable) {
        log.debug("Request to get all Attestations");
        return attestationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Attestation> findOne(Long id) {
        log.debug("Request to get Attestation : {}", id);
        return attestationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attestation : {}", id);
        attestationRepository.deleteById(id);
    }
}
