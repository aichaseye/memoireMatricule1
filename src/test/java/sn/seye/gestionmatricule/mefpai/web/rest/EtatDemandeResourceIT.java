package sn.seye.gestionmatricule.mefpai.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sn.seye.gestionmatricule.mefpai.IntegrationTest;
import sn.seye.gestionmatricule.mefpai.domain.EtatDemande;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Status;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.TypeDossier;
import sn.seye.gestionmatricule.mefpai.repository.EtatDemandeRepository;

/**
 * Integration tests for the {@link EtatDemandeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtatDemandeResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final TypeDossier DEFAULT_TYPE_DOSSIER = TypeDossier.Diplome;
    private static final TypeDossier UPDATED_TYPE_DOSSIER = TypeDossier.Attestation;

    private static final Status DEFAULT_STATUS = Status.Disponible;
    private static final Status UPDATED_STATUS = Status.Non_disponible;

    private static final String ENTITY_API_URL = "/api/etat-demandes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtatDemandeRepository etatDemandeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtatDemandeMockMvc;

    private EtatDemande etatDemande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatDemande createEntity(EntityManager em) {
        EtatDemande etatDemande = new EtatDemande().matricule(DEFAULT_MATRICULE).typeDossier(DEFAULT_TYPE_DOSSIER).status(DEFAULT_STATUS);
        return etatDemande;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatDemande createUpdatedEntity(EntityManager em) {
        EtatDemande etatDemande = new EtatDemande().matricule(UPDATED_MATRICULE).typeDossier(UPDATED_TYPE_DOSSIER).status(UPDATED_STATUS);
        return etatDemande;
    }

    @BeforeEach
    public void initTest() {
        etatDemande = createEntity(em);
    }

    @Test
    @Transactional
    void createEtatDemande() throws Exception {
        int databaseSizeBeforeCreate = etatDemandeRepository.findAll().size();
        // Create the EtatDemande
        restEtatDemandeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatDemande)))
            .andExpect(status().isCreated());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeCreate + 1);
        EtatDemande testEtatDemande = etatDemandeList.get(etatDemandeList.size() - 1);
        assertThat(testEtatDemande.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEtatDemande.getTypeDossier()).isEqualTo(DEFAULT_TYPE_DOSSIER);
        assertThat(testEtatDemande.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createEtatDemandeWithExistingId() throws Exception {
        // Create the EtatDemande with an existing ID
        etatDemande.setId(1L);

        int databaseSizeBeforeCreate = etatDemandeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatDemandeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatDemande)))
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = etatDemandeRepository.findAll().size();
        // set the field null
        etatDemande.setMatricule(null);

        // Create the EtatDemande, which fails.

        restEtatDemandeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatDemande)))
            .andExpect(status().isBadRequest());

        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeDossierIsRequired() throws Exception {
        int databaseSizeBeforeTest = etatDemandeRepository.findAll().size();
        // set the field null
        etatDemande.setTypeDossier(null);

        // Create the EtatDemande, which fails.

        restEtatDemandeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatDemande)))
            .andExpect(status().isBadRequest());

        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = etatDemandeRepository.findAll().size();
        // set the field null
        etatDemande.setStatus(null);

        // Create the EtatDemande, which fails.

        restEtatDemandeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatDemande)))
            .andExpect(status().isBadRequest());

        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEtatDemandes() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        // Get all the etatDemandeList
        restEtatDemandeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatDemande.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].typeDossier").value(hasItem(DEFAULT_TYPE_DOSSIER.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getEtatDemande() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        // Get the etatDemande
        restEtatDemandeMockMvc
            .perform(get(ENTITY_API_URL_ID, etatDemande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatDemande.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.typeDossier").value(DEFAULT_TYPE_DOSSIER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEtatDemande() throws Exception {
        // Get the etatDemande
        restEtatDemandeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEtatDemande() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();

        // Update the etatDemande
        EtatDemande updatedEtatDemande = etatDemandeRepository.findById(etatDemande.getId()).get();
        // Disconnect from session so that the updates on updatedEtatDemande are not directly saved in db
        em.detach(updatedEtatDemande);
        updatedEtatDemande.matricule(UPDATED_MATRICULE).typeDossier(UPDATED_TYPE_DOSSIER).status(UPDATED_STATUS);

        restEtatDemandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtatDemande.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtatDemande))
            )
            .andExpect(status().isOk());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
        EtatDemande testEtatDemande = etatDemandeList.get(etatDemandeList.size() - 1);
        assertThat(testEtatDemande.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEtatDemande.getTypeDossier()).isEqualTo(UPDATED_TYPE_DOSSIER);
        assertThat(testEtatDemande.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etatDemande.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatDemande)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtatDemandeWithPatch() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();

        // Update the etatDemande using partial update
        EtatDemande partialUpdatedEtatDemande = new EtatDemande();
        partialUpdatedEtatDemande.setId(etatDemande.getId());

        partialUpdatedEtatDemande.status(UPDATED_STATUS);

        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtatDemande.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtatDemande))
            )
            .andExpect(status().isOk());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
        EtatDemande testEtatDemande = etatDemandeList.get(etatDemandeList.size() - 1);
        assertThat(testEtatDemande.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEtatDemande.getTypeDossier()).isEqualTo(DEFAULT_TYPE_DOSSIER);
        assertThat(testEtatDemande.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateEtatDemandeWithPatch() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();

        // Update the etatDemande using partial update
        EtatDemande partialUpdatedEtatDemande = new EtatDemande();
        partialUpdatedEtatDemande.setId(etatDemande.getId());

        partialUpdatedEtatDemande.matricule(UPDATED_MATRICULE).typeDossier(UPDATED_TYPE_DOSSIER).status(UPDATED_STATUS);

        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtatDemande.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtatDemande))
            )
            .andExpect(status().isOk());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
        EtatDemande testEtatDemande = etatDemandeList.get(etatDemandeList.size() - 1);
        assertThat(testEtatDemande.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEtatDemande.getTypeDossier()).isEqualTo(UPDATED_TYPE_DOSSIER);
        assertThat(testEtatDemande.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etatDemande.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtatDemande() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        int databaseSizeBeforeDelete = etatDemandeRepository.findAll().size();

        // Delete the etatDemande
        restEtatDemandeMockMvc
            .perform(delete(ENTITY_API_URL_ID, etatDemande.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
