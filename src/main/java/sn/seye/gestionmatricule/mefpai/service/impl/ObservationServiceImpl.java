package sn.seye.gestionmatricule.mefpai.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.domain.Observation;
import sn.seye.gestionmatricule.mefpai.repository.ObservationRepository;
import sn.seye.gestionmatricule.mefpai.service.ObservationService;

/**
 * Service Implementation for managing {@link Observation}.
 */
@Service
@Transactional
public class ObservationServiceImpl implements ObservationService {

    private final Logger log = LoggerFactory.getLogger(ObservationServiceImpl.class);

    private final ObservationRepository observationRepository;

    public ObservationServiceImpl(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @Override
    public Observation save(Observation observation) {
        log.debug("Request to save Observation : {}", observation);
        return observationRepository.save(observation);
    }

    @Override
    public Optional<Observation> partialUpdate(Observation observation) {
        log.debug("Request to partially update Observation : {}", observation);

        return observationRepository
            .findById(observation.getId())
            .map(existingObservation -> {
                if (observation.getAsuduite() != null) {
                    existingObservation.setAsuduite(observation.getAsuduite());
                }
                if (observation.getPonctualite() != null) {
                    existingObservation.setPonctualite(observation.getPonctualite());
                }
                if (observation.getApte() != null) {
                    existingObservation.setApte(observation.getApte());
                }

                return existingObservation;
            })
            .map(observationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Observation> findAll(Pageable pageable) {
        log.debug("Request to get all Observations");
        return observationRepository.findAll(pageable);
    }

    public Page<Observation> findAllWithEagerRelationships(Pageable pageable) {
        return observationRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Observation> findOne(Long id) {
        log.debug("Request to get Observation : {}", id);
        return observationRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Observation : {}", id);
        observationRepository.deleteById(id);
    }
}
