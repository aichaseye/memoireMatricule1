package sn.seye.gestionmatricule.mefpai.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.domain.Programme;
import sn.seye.gestionmatricule.mefpai.repository.ProgrammeRepository;
import sn.seye.gestionmatricule.mefpai.service.ProgrammeService;

/**
 * Service Implementation for managing {@link Programme}.
 */
@Service
@Transactional
public class ProgrammeServiceImpl implements ProgrammeService {

    private final Logger log = LoggerFactory.getLogger(ProgrammeServiceImpl.class);

    private final ProgrammeRepository programmeRepository;

    public ProgrammeServiceImpl(ProgrammeRepository programmeRepository) {
        this.programmeRepository = programmeRepository;
    }

    @Override
    public Programme save(Programme programme) {
        log.debug("Request to save Programme : {}", programme);
        return programmeRepository.save(programme);
    }

    @Override
    public Optional<Programme> partialUpdate(Programme programme) {
        log.debug("Request to partially update Programme : {}", programme);

        return programmeRepository
            .findById(programme.getId())
            .map(existingProgramme -> {
                if (programme.getNomModule() != null) {
                    existingProgramme.setNomModule(programme.getNomModule());
                }
                if (programme.getCoef() != null) {
                    existingProgramme.setCoef(programme.getCoef());
                }
                if (programme.getNote() != null) {
                    existingProgramme.setNote(programme.getNote());
                }

                return existingProgramme;
            })
            .map(programmeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Programme> findAll(Pageable pageable) {
        log.debug("Request to get all Programmes");
        return programmeRepository.findAll(pageable);
    }

    public Page<Programme> findAllWithEagerRelationships(Pageable pageable) {
        return programmeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Programme> findOne(Long id) {
        log.debug("Request to get Programme : {}", id);
        return programmeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Programme : {}", id);
        programmeRepository.deleteById(id);
    }
}
