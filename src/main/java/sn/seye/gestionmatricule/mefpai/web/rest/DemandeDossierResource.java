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
import sn.seye.gestionmatricule.mefpai.domain.DemandeDossier;
import sn.seye.gestionmatricule.mefpai.repository.DemandeDossierRepository;
import sn.seye.gestionmatricule.mefpai.service.DemandeDossierService;
import sn.seye.gestionmatricule.mefpai.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.seye.gestionmatricule.mefpai.domain.DemandeDossier}.
 */
@RestController
@RequestMapping("/api")
public class DemandeDossierResource {

    private final Logger log = LoggerFactory.getLogger(DemandeDossierResource.class);

    private static final String ENTITY_NAME = "demandeDossier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeDossierService demandeDossierService;

    private final DemandeDossierRepository demandeDossierRepository;

    public DemandeDossierResource(DemandeDossierService demandeDossierService, DemandeDossierRepository demandeDossierRepository) {
        this.demandeDossierService = demandeDossierService;
        this.demandeDossierRepository = demandeDossierRepository;
    }

    /**
     * {@code POST  /demande-dossiers} : Create a new demandeDossier.
     *
     * @param demandeDossier the demandeDossier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeDossier, or with status {@code 400 (Bad Request)} if the demandeDossier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-dossiers")
    public ResponseEntity<DemandeDossier> createDemandeDossier(@Valid @RequestBody DemandeDossier demandeDossier)
        throws URISyntaxException {
        log.debug("REST request to save DemandeDossier : {}", demandeDossier);
        if (demandeDossier.getId() != null) {
            throw new BadRequestAlertException("A new demandeDossier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeDossier result = demandeDossierService.save(demandeDossier);
        return ResponseEntity
            .created(new URI("/api/demande-dossiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-dossiers/:id} : Updates an existing demandeDossier.
     *
     * @param id the id of the demandeDossier to save.
     * @param demandeDossier the demandeDossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeDossier,
     * or with status {@code 400 (Bad Request)} if the demandeDossier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeDossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-dossiers/{id}")
    public ResponseEntity<DemandeDossier> updateDemandeDossier(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemandeDossier demandeDossier
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeDossier : {}, {}", id, demandeDossier);
        if (demandeDossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeDossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeDossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeDossier result = demandeDossierService.save(demandeDossier);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeDossier.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-dossiers/:id} : Partial updates given fields of an existing demandeDossier, field will ignore if it is null
     *
     * @param id the id of the demandeDossier to save.
     * @param demandeDossier the demandeDossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeDossier,
     * or with status {@code 400 (Bad Request)} if the demandeDossier is not valid,
     * or with status {@code 404 (Not Found)} if the demandeDossier is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeDossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demande-dossiers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeDossier> partialUpdateDemandeDossier(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemandeDossier demandeDossier
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeDossier partially : {}, {}", id, demandeDossier);
        if (demandeDossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeDossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeDossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeDossier> result = demandeDossierService.partialUpdate(demandeDossier);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeDossier.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-dossiers} : get all the demandeDossiers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeDossiers in body.
     */
    @GetMapping("/demande-dossiers")
    public ResponseEntity<List<DemandeDossier>> getAllDemandeDossiers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DemandeDossiers");
        Page<DemandeDossier> page = demandeDossierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-dossiers/:id} : get the "id" demandeDossier.
     *
     * @param id the id of the demandeDossier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeDossier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-dossiers/{id}")
    public ResponseEntity<DemandeDossier> getDemandeDossier(@PathVariable Long id) {
        log.debug("REST request to get DemandeDossier : {}", id);
        Optional<DemandeDossier> demandeDossier = demandeDossierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeDossier);
    }

    /**
     * {@code DELETE  /demande-dossiers/:id} : delete the "id" demandeDossier.
     *
     * @param id the id of the demandeDossier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-dossiers/{id}")
    public ResponseEntity<Void> deleteDemandeDossier(@PathVariable Long id) {
        log.debug("REST request to delete DemandeDossier : {}", id);
        demandeDossierService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
