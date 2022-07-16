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
import org.springframework.util.Base64Utils;
import sn.seye.gestionmatricule.mefpai.IntegrationTest;
import sn.seye.gestionmatricule.mefpai.domain.DemandeDossier;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Filiere;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Niveau;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.NomDiplome;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.NomSemestre;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Serie;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.TypeDossier;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.TypeReleve;
import sn.seye.gestionmatricule.mefpai.repository.DemandeDossierRepository;

/**
 * Integration tests for the {@link DemandeDossierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeDossierResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final TypeDossier DEFAULT_TYPE_DOSSIER = TypeDossier.Diplome;
    private static final TypeDossier UPDATED_TYPE_DOSSIER = TypeDossier.Attestation;

    private static final LocalDate DEFAULT_ANNEE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNEE = LocalDate.now(ZoneId.systemDefault());

    private static final Serie DEFAULT_SERIE = Serie.STEG;
    private static final Serie UPDATED_SERIE = Serie.STIDD_E;

    private static final Filiere DEFAULT_FILIERE = Filiere.Agricultre;
    private static final Filiere UPDATED_FILIERE = Filiere.Peche;

    private static final NomSemestre DEFAULT_NOM_SEMESTRE = NomSemestre.Semestre1;
    private static final NomSemestre UPDATED_NOM_SEMESTRE = NomSemestre.Semestre2;

    private static final Niveau DEFAULT_NIVEAU = Niveau.CAP1;
    private static final Niveau UPDATED_NIVEAU = Niveau.CAP2;

    private static final TypeReleve DEFAULT_TYPE_RELEVE = TypeReleve.Admis;
    private static final TypeReleve UPDATED_TYPE_RELEVE = TypeReleve.Ajourne;

    private static final NomDiplome DEFAULT_NOM_DIPLOME = NomDiplome.CAP;
    private static final NomDiplome UPDATED_NOM_DIPLOME = NomDiplome.BTS;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/demande-dossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeDossierRepository demandeDossierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeDossierMockMvc;

    private DemandeDossier demandeDossier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeDossier createEntity(EntityManager em) {
        DemandeDossier demandeDossier = new DemandeDossier()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .email(DEFAULT_EMAIL)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .matricule(DEFAULT_MATRICULE)
            .typeDossier(DEFAULT_TYPE_DOSSIER)
            .annee(DEFAULT_ANNEE)
            .serie(DEFAULT_SERIE)
            .filiere(DEFAULT_FILIERE)
            .nomSemestre(DEFAULT_NOM_SEMESTRE)
            .niveau(DEFAULT_NIVEAU)
            .typeReleve(DEFAULT_TYPE_RELEVE)
            .nomDiplome(DEFAULT_NOM_DIPLOME)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return demandeDossier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeDossier createUpdatedEntity(EntityManager em) {
        DemandeDossier demandeDossier = new DemandeDossier()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .matricule(UPDATED_MATRICULE)
            .typeDossier(UPDATED_TYPE_DOSSIER)
            .annee(UPDATED_ANNEE)
            .serie(UPDATED_SERIE)
            .filiere(UPDATED_FILIERE)
            .nomSemestre(UPDATED_NOM_SEMESTRE)
            .niveau(UPDATED_NIVEAU)
            .typeReleve(UPDATED_TYPE_RELEVE)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        return demandeDossier;
    }

    @BeforeEach
    public void initTest() {
        demandeDossier = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeDossier() throws Exception {
        int databaseSizeBeforeCreate = demandeDossierRepository.findAll().size();
        // Create the DemandeDossier
        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeDossier testDemandeDossier = demandeDossierList.get(demandeDossierList.size() - 1);
        assertThat(testDemandeDossier.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDemandeDossier.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDemandeDossier.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandeDossier.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testDemandeDossier.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testDemandeDossier.getTypeDossier()).isEqualTo(DEFAULT_TYPE_DOSSIER);
        assertThat(testDemandeDossier.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testDemandeDossier.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testDemandeDossier.getFiliere()).isEqualTo(DEFAULT_FILIERE);
        assertThat(testDemandeDossier.getNomSemestre()).isEqualTo(DEFAULT_NOM_SEMESTRE);
        assertThat(testDemandeDossier.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testDemandeDossier.getTypeReleve()).isEqualTo(DEFAULT_TYPE_RELEVE);
        assertThat(testDemandeDossier.getNomDiplome()).isEqualTo(DEFAULT_NOM_DIPLOME);
        assertThat(testDemandeDossier.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testDemandeDossier.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createDemandeDossierWithExistingId() throws Exception {
        // Create the DemandeDossier with an existing ID
        demandeDossier.setId(1L);

        int databaseSizeBeforeCreate = demandeDossierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setNom(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setPrenom(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setEmail(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setDateNaissance(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setMatricule(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeDossierIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setTypeDossier(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setAnnee(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFiliereIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setFiliere(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomSemestreIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setNomSemestre(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNiveauIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setNiveau(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeReleveIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setTypeReleve(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomDiplomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeDossierRepository.findAll().size();
        // set the field null
        demandeDossier.setNomDiplome(null);

        // Create the DemandeDossier, which fails.

        restDemandeDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandeDossiers() throws Exception {
        // Initialize the database
        demandeDossierRepository.saveAndFlush(demandeDossier);

        // Get all the demandeDossierList
        restDemandeDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeDossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].typeDossier").value(hasItem(DEFAULT_TYPE_DOSSIER.toString())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE.toString())))
            .andExpect(jsonPath("$.[*].filiere").value(hasItem(DEFAULT_FILIERE.toString())))
            .andExpect(jsonPath("$.[*].nomSemestre").value(hasItem(DEFAULT_NOM_SEMESTRE.toString())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())))
            .andExpect(jsonPath("$.[*].typeReleve").value(hasItem(DEFAULT_TYPE_RELEVE.toString())))
            .andExpect(jsonPath("$.[*].nomDiplome").value(hasItem(DEFAULT_NOM_DIPLOME.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    void getDemandeDossier() throws Exception {
        // Initialize the database
        demandeDossierRepository.saveAndFlush(demandeDossier);

        // Get the demandeDossier
        restDemandeDossierMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeDossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeDossier.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.typeDossier").value(DEFAULT_TYPE_DOSSIER.toString()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE.toString()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE.toString()))
            .andExpect(jsonPath("$.filiere").value(DEFAULT_FILIERE.toString()))
            .andExpect(jsonPath("$.nomSemestre").value(DEFAULT_NOM_SEMESTRE.toString()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU.toString()))
            .andExpect(jsonPath("$.typeReleve").value(DEFAULT_TYPE_RELEVE.toString()))
            .andExpect(jsonPath("$.nomDiplome").value(DEFAULT_NOM_DIPLOME.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    void getNonExistingDemandeDossier() throws Exception {
        // Get the demandeDossier
        restDemandeDossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandeDossier() throws Exception {
        // Initialize the database
        demandeDossierRepository.saveAndFlush(demandeDossier);

        int databaseSizeBeforeUpdate = demandeDossierRepository.findAll().size();

        // Update the demandeDossier
        DemandeDossier updatedDemandeDossier = demandeDossierRepository.findById(demandeDossier.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeDossier are not directly saved in db
        em.detach(updatedDemandeDossier);
        updatedDemandeDossier
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .matricule(UPDATED_MATRICULE)
            .typeDossier(UPDATED_TYPE_DOSSIER)
            .annee(UPDATED_ANNEE)
            .serie(UPDATED_SERIE)
            .filiere(UPDATED_FILIERE)
            .nomSemestre(UPDATED_NOM_SEMESTRE)
            .niveau(UPDATED_NIVEAU)
            .typeReleve(UPDATED_TYPE_RELEVE)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restDemandeDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeDossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandeDossier))
            )
            .andExpect(status().isOk());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeUpdate);
        DemandeDossier testDemandeDossier = demandeDossierList.get(demandeDossierList.size() - 1);
        assertThat(testDemandeDossier.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeDossier.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeDossier.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeDossier.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testDemandeDossier.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testDemandeDossier.getTypeDossier()).isEqualTo(UPDATED_TYPE_DOSSIER);
        assertThat(testDemandeDossier.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testDemandeDossier.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testDemandeDossier.getFiliere()).isEqualTo(UPDATED_FILIERE);
        assertThat(testDemandeDossier.getNomSemestre()).isEqualTo(UPDATED_NOM_SEMESTRE);
        assertThat(testDemandeDossier.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testDemandeDossier.getTypeReleve()).isEqualTo(UPDATED_TYPE_RELEVE);
        assertThat(testDemandeDossier.getNomDiplome()).isEqualTo(UPDATED_NOM_DIPLOME);
        assertThat(testDemandeDossier.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testDemandeDossier.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingDemandeDossier() throws Exception {
        int databaseSizeBeforeUpdate = demandeDossierRepository.findAll().size();
        demandeDossier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeDossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeDossier() throws Exception {
        int databaseSizeBeforeUpdate = demandeDossierRepository.findAll().size();
        demandeDossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeDossier() throws Exception {
        int databaseSizeBeforeUpdate = demandeDossierRepository.findAll().size();
        demandeDossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeDossierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeDossierWithPatch() throws Exception {
        // Initialize the database
        demandeDossierRepository.saveAndFlush(demandeDossier);

        int databaseSizeBeforeUpdate = demandeDossierRepository.findAll().size();

        // Update the demandeDossier using partial update
        DemandeDossier partialUpdatedDemandeDossier = new DemandeDossier();
        partialUpdatedDemandeDossier.setId(demandeDossier.getId());

        partialUpdatedDemandeDossier
            .email(UPDATED_EMAIL)
            .matricule(UPDATED_MATRICULE)
            .typeDossier(UPDATED_TYPE_DOSSIER)
            .filiere(UPDATED_FILIERE)
            .niveau(UPDATED_NIVEAU)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restDemandeDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeDossier))
            )
            .andExpect(status().isOk());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeUpdate);
        DemandeDossier testDemandeDossier = demandeDossierList.get(demandeDossierList.size() - 1);
        assertThat(testDemandeDossier.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDemandeDossier.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDemandeDossier.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeDossier.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testDemandeDossier.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testDemandeDossier.getTypeDossier()).isEqualTo(UPDATED_TYPE_DOSSIER);
        assertThat(testDemandeDossier.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testDemandeDossier.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testDemandeDossier.getFiliere()).isEqualTo(UPDATED_FILIERE);
        assertThat(testDemandeDossier.getNomSemestre()).isEqualTo(DEFAULT_NOM_SEMESTRE);
        assertThat(testDemandeDossier.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testDemandeDossier.getTypeReleve()).isEqualTo(DEFAULT_TYPE_RELEVE);
        assertThat(testDemandeDossier.getNomDiplome()).isEqualTo(DEFAULT_NOM_DIPLOME);
        assertThat(testDemandeDossier.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testDemandeDossier.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateDemandeDossierWithPatch() throws Exception {
        // Initialize the database
        demandeDossierRepository.saveAndFlush(demandeDossier);

        int databaseSizeBeforeUpdate = demandeDossierRepository.findAll().size();

        // Update the demandeDossier using partial update
        DemandeDossier partialUpdatedDemandeDossier = new DemandeDossier();
        partialUpdatedDemandeDossier.setId(demandeDossier.getId());

        partialUpdatedDemandeDossier
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .matricule(UPDATED_MATRICULE)
            .typeDossier(UPDATED_TYPE_DOSSIER)
            .annee(UPDATED_ANNEE)
            .serie(UPDATED_SERIE)
            .filiere(UPDATED_FILIERE)
            .nomSemestre(UPDATED_NOM_SEMESTRE)
            .niveau(UPDATED_NIVEAU)
            .typeReleve(UPDATED_TYPE_RELEVE)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restDemandeDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeDossier))
            )
            .andExpect(status().isOk());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeUpdate);
        DemandeDossier testDemandeDossier = demandeDossierList.get(demandeDossierList.size() - 1);
        assertThat(testDemandeDossier.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeDossier.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeDossier.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeDossier.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testDemandeDossier.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testDemandeDossier.getTypeDossier()).isEqualTo(UPDATED_TYPE_DOSSIER);
        assertThat(testDemandeDossier.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testDemandeDossier.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testDemandeDossier.getFiliere()).isEqualTo(UPDATED_FILIERE);
        assertThat(testDemandeDossier.getNomSemestre()).isEqualTo(UPDATED_NOM_SEMESTRE);
        assertThat(testDemandeDossier.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testDemandeDossier.getTypeReleve()).isEqualTo(UPDATED_TYPE_RELEVE);
        assertThat(testDemandeDossier.getNomDiplome()).isEqualTo(UPDATED_NOM_DIPLOME);
        assertThat(testDemandeDossier.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testDemandeDossier.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeDossier() throws Exception {
        int databaseSizeBeforeUpdate = demandeDossierRepository.findAll().size();
        demandeDossier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeDossier() throws Exception {
        int databaseSizeBeforeUpdate = demandeDossierRepository.findAll().size();
        demandeDossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeDossier() throws Exception {
        int databaseSizeBeforeUpdate = demandeDossierRepository.findAll().size();
        demandeDossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeDossierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(demandeDossier))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeDossier in the database
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeDossier() throws Exception {
        // Initialize the database
        demandeDossierRepository.saveAndFlush(demandeDossier);

        int databaseSizeBeforeDelete = demandeDossierRepository.findAll().size();

        // Delete the demandeDossier
        restDemandeDossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeDossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeDossier> demandeDossierList = demandeDossierRepository.findAll();
        assertThat(demandeDossierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
