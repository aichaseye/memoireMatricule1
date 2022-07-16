package sn.seye.gestionmatricule.mefpai.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
import sn.seye.gestionmatricule.mefpai.domain.DemandeMatApp;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Sexe;
import sn.seye.gestionmatricule.mefpai.repository.DemandeMatAppRepository;

/**
 * Integration tests for the {@link DemandeMatAppResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeMatAppResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Sexe DEFAULT_SEXE = Sexe.Masclin;
    private static final Sexe UPDATED_SEXE = Sexe.Feminin;

    private static final String DEFAULT_MATRICULE_APP = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE_APP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/demande-mat-apps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeMatAppRepository demandeMatAppRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeMatAppMockMvc;

    private DemandeMatApp demandeMatApp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeMatApp createEntity(EntityManager em) {
        DemandeMatApp demandeMatApp = new DemandeMatApp()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .sexe(DEFAULT_SEXE)
            .matriculeApp(DEFAULT_MATRICULE_APP);
        return demandeMatApp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeMatApp createUpdatedEntity(EntityManager em) {
        DemandeMatApp demandeMatApp = new DemandeMatApp()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .sexe(UPDATED_SEXE)
            .matriculeApp(UPDATED_MATRICULE_APP);
        return demandeMatApp;
    }

    @BeforeEach
    public void initTest() {
        demandeMatApp = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeMatApp() throws Exception {
        int databaseSizeBeforeCreate = demandeMatAppRepository.findAll().size();
        // Create the DemandeMatApp
        restDemandeMatAppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeMatApp)))
            .andExpect(status().isCreated());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeMatApp testDemandeMatApp = demandeMatAppList.get(demandeMatAppList.size() - 1);
        assertThat(testDemandeMatApp.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDemandeMatApp.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDemandeMatApp.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandeMatApp.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDemandeMatApp.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testDemandeMatApp.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testDemandeMatApp.getMatriculeApp()).isEqualTo(DEFAULT_MATRICULE_APP);
    }

    @Test
    @Transactional
    void createDemandeMatAppWithExistingId() throws Exception {
        // Create the DemandeMatApp with an existing ID
        demandeMatApp.setId(1L);

        int databaseSizeBeforeCreate = demandeMatAppRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeMatAppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeMatApp)))
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatAppRepository.findAll().size();
        // set the field null
        demandeMatApp.setNom(null);

        // Create the DemandeMatApp, which fails.

        restDemandeMatAppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeMatApp)))
            .andExpect(status().isBadRequest());

        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatAppRepository.findAll().size();
        // set the field null
        demandeMatApp.setPrenom(null);

        // Create the DemandeMatApp, which fails.

        restDemandeMatAppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeMatApp)))
            .andExpect(status().isBadRequest());

        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatAppRepository.findAll().size();
        // set the field null
        demandeMatApp.setEmail(null);

        // Create the DemandeMatApp, which fails.

        restDemandeMatAppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeMatApp)))
            .andExpect(status().isBadRequest());

        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatAppRepository.findAll().size();
        // set the field null
        demandeMatApp.setTelephone(null);

        // Create the DemandeMatApp, which fails.

        restDemandeMatAppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeMatApp)))
            .andExpect(status().isBadRequest());

        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatAppRepository.findAll().size();
        // set the field null
        demandeMatApp.setDateNaissance(null);

        // Create the DemandeMatApp, which fails.

        restDemandeMatAppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeMatApp)))
            .andExpect(status().isBadRequest());

        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatAppRepository.findAll().size();
        // set the field null
        demandeMatApp.setSexe(null);

        // Create the DemandeMatApp, which fails.

        restDemandeMatAppMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeMatApp)))
            .andExpect(status().isBadRequest());

        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandeMatApps() throws Exception {
        // Initialize the database
        demandeMatAppRepository.saveAndFlush(demandeMatApp);

        // Get all the demandeMatAppList
        restDemandeMatAppMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeMatApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].matriculeApp").value(hasItem(DEFAULT_MATRICULE_APP)));
    }

    @Test
    @Transactional
    void getDemandeMatApp() throws Exception {
        // Initialize the database
        demandeMatAppRepository.saveAndFlush(demandeMatApp);

        // Get the demandeMatApp
        restDemandeMatAppMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeMatApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeMatApp.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.matriculeApp").value(DEFAULT_MATRICULE_APP));
    }

    @Test
    @Transactional
    void getNonExistingDemandeMatApp() throws Exception {
        // Get the demandeMatApp
        restDemandeMatAppMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandeMatApp() throws Exception {
        // Initialize the database
        demandeMatAppRepository.saveAndFlush(demandeMatApp);

        int databaseSizeBeforeUpdate = demandeMatAppRepository.findAll().size();

        // Update the demandeMatApp
        DemandeMatApp updatedDemandeMatApp = demandeMatAppRepository.findById(demandeMatApp.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeMatApp are not directly saved in db
        em.detach(updatedDemandeMatApp);
        updatedDemandeMatApp
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .sexe(UPDATED_SEXE)
            .matriculeApp(UPDATED_MATRICULE_APP);

        restDemandeMatAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeMatApp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandeMatApp))
            )
            .andExpect(status().isOk());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeUpdate);
        DemandeMatApp testDemandeMatApp = demandeMatAppList.get(demandeMatAppList.size() - 1);
        assertThat(testDemandeMatApp.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeMatApp.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeMatApp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeMatApp.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDemandeMatApp.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testDemandeMatApp.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDemandeMatApp.getMatriculeApp()).isEqualTo(UPDATED_MATRICULE_APP);
    }

    @Test
    @Transactional
    void putNonExistingDemandeMatApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatAppRepository.findAll().size();
        demandeMatApp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeMatAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeMatApp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatApp))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeMatApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatAppRepository.findAll().size();
        demandeMatApp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatApp))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeMatApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatAppRepository.findAll().size();
        demandeMatApp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatAppMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeMatApp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeMatAppWithPatch() throws Exception {
        // Initialize the database
        demandeMatAppRepository.saveAndFlush(demandeMatApp);

        int databaseSizeBeforeUpdate = demandeMatAppRepository.findAll().size();

        // Update the demandeMatApp using partial update
        DemandeMatApp partialUpdatedDemandeMatApp = new DemandeMatApp();
        partialUpdatedDemandeMatApp.setId(demandeMatApp.getId());

        partialUpdatedDemandeMatApp.dateNaissance(UPDATED_DATE_NAISSANCE).matriculeApp(UPDATED_MATRICULE_APP);

        restDemandeMatAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeMatApp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeMatApp))
            )
            .andExpect(status().isOk());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeUpdate);
        DemandeMatApp testDemandeMatApp = demandeMatAppList.get(demandeMatAppList.size() - 1);
        assertThat(testDemandeMatApp.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDemandeMatApp.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDemandeMatApp.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandeMatApp.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDemandeMatApp.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testDemandeMatApp.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testDemandeMatApp.getMatriculeApp()).isEqualTo(UPDATED_MATRICULE_APP);
    }

    @Test
    @Transactional
    void fullUpdateDemandeMatAppWithPatch() throws Exception {
        // Initialize the database
        demandeMatAppRepository.saveAndFlush(demandeMatApp);

        int databaseSizeBeforeUpdate = demandeMatAppRepository.findAll().size();

        // Update the demandeMatApp using partial update
        DemandeMatApp partialUpdatedDemandeMatApp = new DemandeMatApp();
        partialUpdatedDemandeMatApp.setId(demandeMatApp.getId());

        partialUpdatedDemandeMatApp
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .sexe(UPDATED_SEXE)
            .matriculeApp(UPDATED_MATRICULE_APP);

        restDemandeMatAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeMatApp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeMatApp))
            )
            .andExpect(status().isOk());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeUpdate);
        DemandeMatApp testDemandeMatApp = demandeMatAppList.get(demandeMatAppList.size() - 1);
        assertThat(testDemandeMatApp.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeMatApp.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeMatApp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeMatApp.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDemandeMatApp.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testDemandeMatApp.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDemandeMatApp.getMatriculeApp()).isEqualTo(UPDATED_MATRICULE_APP);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeMatApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatAppRepository.findAll().size();
        demandeMatApp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeMatAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeMatApp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatApp))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeMatApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatAppRepository.findAll().size();
        demandeMatApp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatApp))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeMatApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatAppRepository.findAll().size();
        demandeMatApp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatAppMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(demandeMatApp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeMatApp in the database
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeMatApp() throws Exception {
        // Initialize the database
        demandeMatAppRepository.saveAndFlush(demandeMatApp);

        int databaseSizeBeforeDelete = demandeMatAppRepository.findAll().size();

        // Delete the demandeMatApp
        restDemandeMatAppMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeMatApp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeMatApp> demandeMatAppList = demandeMatAppRepository.findAll();
        assertThat(demandeMatAppList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
