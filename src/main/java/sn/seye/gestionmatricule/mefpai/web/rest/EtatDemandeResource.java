package sn.seye.gestionmatricule.mefpai.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.seye.gestionmatricule.mefpai.domain.EtatDemande;
import sn.seye.gestionmatricule.mefpai.repository.EtatDemandeRepository;
import sn.seye.gestionmatricule.mefpai.service.EtatDemandeService;
import sn.seye.gestionmatricule.mefpai.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.seye.gestionmatricule.mefpai.domain.EtatDemande}.
 */
@RestController
@RequestMapping("/api")
public class EtatDemandeResource {

    private final Logger log = LoggerFactory.getLogger(EtatDemandeResource.class);

    private static final String ENTITY_NAME = "etatDemande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatDemandeService etatDemandeService;

    private final EtatDemandeRepository etatDemandeRepository;

    public EtatDemandeResource(EtatDemandeService etatDemandeService, EtatDemandeRepository etatDemandeRepository) {
        this.etatDemandeService = etatDemandeService;
        this.etatDemandeRepository = etatDemandeRepository;
    }

    /**
     * {@code POST  /etat-demandes} : Create a new etatDemande.
     *
     * @param etatDemande the etatDemande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatDemande, or with status {@code 400 (Bad Request)} if the etatDemande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-demandes")
    public ResponseEntity<EtatDemande> createEtatDemande(@Valid @RequestBody EtatDemande etatDemande) throws URISyntaxException {
        log.debug("REST request to save EtatDemande : {}", etatDemande);
        if (etatDemande.getId() != null) {
            throw new BadRequestAlertException("A new etatDemande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatDemande result = etatDemandeService.save(etatDemande);
        return ResponseEntity
            .created(new URI("/api/etat-demandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-demandes/:id} : Updates an existing etatDemande.
     *
     * @param id the id of the etatDemande to save.
     * @param etatDemande the etatDemande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatDemande,
     * or with status {@code 400 (Bad Request)} if the etatDemande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatDemande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-demandes/{id}")
    public ResponseEntity<EtatDemande> updateEtatDemande(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EtatDemande etatDemande
    ) throws URISyntaxException {
        log.debug("REST request to update EtatDemande : {}, {}", id, etatDemande);
        if (etatDemande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etatDemande.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etatDemandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtatDemande result = etatDemandeService.save(etatDemande);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatDemande.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etat-demandes/:id} : Partial updates given fields of an existing etatDemande, field will ignore if it is null
     *
     * @param id the id of the etatDemande to save.
     * @param etatDemande the etatDemande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatDemande,
     * or with status {@code 400 (Bad Request)} if the etatDemande is not valid,
     * or with status {@code 404 (Not Found)} if the etatDemande is not found,
     * or with status {@code 500 (Internal Server Error)} if the etatDemande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etat-demandes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EtatDemande> partialUpdateEtatDemande(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EtatDemande etatDemande
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtatDemande partially : {}, {}", id, etatDemande);
        if (etatDemande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etatDemande.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etatDemandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtatDemande> result = etatDemandeService.partialUpdate(etatDemande);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatDemande.getId().toString())
        );
    }

    /**
     * {@code GET  /etat-demandes} : get all the etatDemandes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatDemandes in body.
     */
    @GetMapping("/etat-demandes")
    public ResponseEntity<List<EtatDemande>> getAllEtatDemandes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of EtatDemandes");
        Page<EtatDemande> page = etatDemandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-demandes/:id} : get the "id" etatDemande.
     *
     * @param id the id of the etatDemande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatDemande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-demandes/{id}")
    public ResponseEntity<EtatDemande> getEtatDemande(@PathVariable Long id) {
        log.debug("REST request to get EtatDemande : {}", id);
        Optional<EtatDemande> etatDemande = etatDemandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatDemande);
    }

    /**
     * {@code DELETE  /etat-demandes/:id} : delete the "id" etatDemande.
     *
     * @param id the id of the etatDemande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-demandes/{id}")
    public ResponseEntity<Void> deleteEtatDemande(@PathVariable Long id) {
        log.debug("REST request to delete EtatDemande : {}", id);
        etatDemandeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
