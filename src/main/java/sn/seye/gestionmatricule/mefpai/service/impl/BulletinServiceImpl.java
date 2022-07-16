package sn.seye.gestionmatricule.mefpai.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.domain.Bulletin;
import sn.seye.gestionmatricule.mefpai.repository.BulletinRepository;
import sn.seye.gestionmatricule.mefpai.service.BulletinService;

/**
 * Service Implementation for managing {@link Bulletin}.
 */
@Service
@Transactional
public class BulletinServiceImpl implements BulletinService {

    private final Logger log = LoggerFactory.getLogger(BulletinServiceImpl.class);

    private final BulletinRepository bulletinRepository;

    public BulletinServiceImpl(BulletinRepository bulletinRepository) {
        this.bulletinRepository = bulletinRepository;
    }

    @Override
    public Bulletin save(Bulletin bulletin) {
        log.debug("Request to save Bulletin : {}", bulletin);
        return bulletinRepository.save(bulletin);
    }

    @Override
    public Optional<Bulletin> partialUpdate(Bulletin bulletin) {
        log.debug("Request to partially update Bulletin : {}", bulletin);

        return bulletinRepository
            .findById(bulletin.getId())
            .map(existingBulletin -> {
                if (bulletin.getNomsemestre() != null) {
                    existingBulletin.setNomsemestre(bulletin.getNomsemestre());
                }
                if (bulletin.getAnnee() != null) {
                    existingBulletin.setAnnee(bulletin.getAnnee());
                }
                if (bulletin.getMoyenne() != null) {
                    existingBulletin.setMoyenne(bulletin.getMoyenne());
                }
                if (bulletin.getSerie() != null) {
                    existingBulletin.setSerie(bulletin.getSerie());
                }
                if (bulletin.getFiliere() != null) {
                    existingBulletin.setFiliere(bulletin.getFiliere());
                }
                if (bulletin.getNiveau() != null) {
                    existingBulletin.setNiveau(bulletin.getNiveau());
                }
                if (bulletin.getMoyenneGenerale() != null) {
                    existingBulletin.setMoyenneGenerale(bulletin.getMoyenneGenerale());
                }
                if (bulletin.getRang() != null) {
                    existingBulletin.setRang(bulletin.getRang());
                }
                if (bulletin.getNoteConduite() != null) {
                    existingBulletin.setNoteConduite(bulletin.getNoteConduite());
                }
                if (bulletin.getMatricule() != null) {
                    existingBulletin.setMatricule(bulletin.getMatricule());
                }

                return existingBulletin;
            })
            .map(bulletinRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bulletin> findAll(Pageable pageable) {
        log.debug("Request to get all Bulletins");
        return bulletinRepository.findAll(pageable);
    }

    public Page<Bulletin> findAllWithEagerRelationships(Pageable pageable) {
        return bulletinRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bulletin> findOne(Long id) {
        log.debug("Request to get Bulletin : {}", id);
        return bulletinRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bulletin : {}", id);
        bulletinRepository.deleteById(id);
    }
}
