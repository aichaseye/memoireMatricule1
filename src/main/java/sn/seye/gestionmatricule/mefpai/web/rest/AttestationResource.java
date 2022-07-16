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
import sn.seye.gestionmatricule.mefpai.domain.Attestation;
import sn.seye.gestionmatricule.mefpai.repository.AttestationRepository;
import sn.seye.gestionmatricule.mefpai.service.AttestationService;
import sn.seye.gestionmatricule.mefpai.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.seye.gestionmatricule.mefpai.domain.Attestation}.
 */
@RestController
@RequestMapping("/api")
public class AttestationResource {

    private final Logger log = LoggerFactory.getLogger(AttestationResource.class);

    private static final String ENTITY_NAME = "attestation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttestationService attestationService;

    private final AttestationRepository attestationRepository;

    public AttestationResource(AttestationService attestationService, AttestationRepository attestationRepository) {
        this.attestationService = attestationService;
        this.attestationRepository = attestationRepository;
    }

    /**
     * {@code POST  /attestations} : Create a new attestation.
     *
     * @param attestation the attestation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attestation, or with status {@code 400 (Bad Request)} if the attestation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attestations")
    public ResponseEntity<Attestation> createAttestation(@Valid @RequestBody Attestation attestation) throws URISyntaxException {
        log.debug("REST request to save Attestation : {}", attestation);
        if (attestation.getId() != null) {
            throw new BadRequestAlertException("A new attestation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attestation result = attestationService.save(attestation);
        return ResponseEntity
            .created(new URI("/api/attestations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attestations/:id} : Updates an existing attestation.
     *
     * @param id the id of the attestation to save.
     * @param attestation the attestation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attestation,
     * or with status {@code 400 (Bad Request)} if the attestation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attestation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attestations/{id}")
    public ResponseEntity<Attestation> updateAttestation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Attestation attestation
    ) throws URISyntaxException {
        log.debug("REST request to update Attestation : {}, {}", id, attestation);
        if (attestation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attestation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attestationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Attestation result = attestationService.save(attestation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attestation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attestations/:id} : Partial updates given fields of an existing attestation, field will ignore if it is null
     *
     * @param id the id of the attestation to save.
     * @param attestation the attestation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attestation,
     * or with status {@code 400 (Bad Request)} if the attestation is not valid,
     * or with status {@code 404 (Not Found)} if the attestation is not found,
     * or with status {@code 500 (Internal Server Error)} if the attestation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attestations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Attestation> partialUpdateAttestation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Attestation attestation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Attestation partially : {}, {}", id, attestation);
        if (attestation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attestation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attestationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Attestation> result = attestationService.partialUpdate(attestation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attestation.getId().toString())
        );
    }

    /**
     * {@code GET  /attestations} : get all the attestations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attestations in body.
     */
    @GetMapping("/attestations")
    public ResponseEntity<List<Attestation>> getAllAttestations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Attestations");
        Page<Attestation> page = attestationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attestations/:id} : get the "id" attestation.
     *
     * @param id the id of the attestation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attestation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attestations/{id}")
    public ResponseEntity<Attestation> getAttestation(@PathVariable Long id) {
        log.debug("REST request to get Attestation : {}", id);
        Optional<Attestation> attestation = attestationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attestation);
    }

    /**
     * {@code DELETE  /attestations/:id} : delete the "id" attestation.
     *
     * @param id the id of the attestation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attestations/{id}")
    public ResponseEntity<Void> deleteAttestation(@PathVariable Long id) {
        log.debug("REST request to delete Attestation : {}", id);
        attestationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
