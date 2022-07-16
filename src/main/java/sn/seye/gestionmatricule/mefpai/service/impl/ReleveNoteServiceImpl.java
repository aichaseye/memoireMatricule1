package sn.seye.gestionmatricule.mefpai.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.domain.ReleveNote;
import sn.seye.gestionmatricule.mefpai.repository.ReleveNoteRepository;
import sn.seye.gestionmatricule.mefpai.service.ReleveNoteService;

/**
 * Service Implementation for managing {@link ReleveNote}.
 */
@Service
@Transactional
public class ReleveNoteServiceImpl implements ReleveNoteService {

    private final Logger log = LoggerFactory.getLogger(ReleveNoteServiceImpl.class);

    private final ReleveNoteRepository releveNoteRepository;

    public ReleveNoteServiceImpl(ReleveNoteRepository releveNoteRepository) {
        this.releveNoteRepository = releveNoteRepository;
    }

    @Override
    public ReleveNote save(ReleveNote releveNote) {
        log.debug("Request to save ReleveNote : {}", releveNote);
        return releveNoteRepository.save(releveNote);
    }

    @Override
    public Optional<ReleveNote> partialUpdate(ReleveNote releveNote) {
        log.debug("Request to partially update ReleveNote : {}", releveNote);

        return releveNoteRepository
            .findById(releveNote.getId())
            .map(existingReleveNote -> {
                if (releveNote.getAnnee() != null) {
                    existingReleveNote.setAnnee(releveNote.getAnnee());
                }
                if (releveNote.getMoyenne() != null) {
                    existingReleveNote.setMoyenne(releveNote.getMoyenne());
                }
                if (releveNote.getSerie() != null) {
                    existingReleveNote.setSerie(releveNote.getSerie());
                }
                if (releveNote.getFiliere() != null) {
                    existingReleveNote.setFiliere(releveNote.getFiliere());
                }
                if (releveNote.getNiveau() != null) {
                    existingReleveNote.setNiveau(releveNote.getNiveau());
                }
                if (releveNote.getMoyenneGenerale() != null) {
                    existingReleveNote.setMoyenneGenerale(releveNote.getMoyenneGenerale());
                }
                if (releveNote.getRang() != null) {
                    existingReleveNote.setRang(releveNote.getRang());
                }
                if (releveNote.getNoteConduite() != null) {
                    existingReleveNote.setNoteConduite(releveNote.getNoteConduite());
                }
                if (releveNote.getMatriculeRel() != null) {
                    existingReleveNote.setMatriculeRel(releveNote.getMatriculeRel());
                }

                return existingReleveNote;
            })
            .map(releveNoteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReleveNote> findAll(Pageable pageable) {
        log.debug("Request to get all ReleveNotes");
        return releveNoteRepository.findAll(pageable);
    }

    public Page<ReleveNote> findAllWithEagerRelationships(Pageable pageable) {
        return releveNoteRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReleveNote> findOne(Long id) {
        log.debug("Request to get ReleveNote : {}", id);
        return releveNoteRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReleveNote : {}", id);
        releveNoteRepository.deleteById(id);
    }
}
