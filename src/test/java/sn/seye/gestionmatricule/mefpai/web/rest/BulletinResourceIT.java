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
import sn.seye.gestionmatricule.mefpai.domain.Bulletin;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Filiere;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Niveau;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.NomSemestre;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Serie;
import sn.seye.gestionmatricule.mefpai.repository.BulletinRepository;

/**
 * Integration tests for the {@link BulletinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BulletinResourceIT {

    private static final NomSemestre DEFAULT_NOMSEMESTRE = NomSemestre.Semestre1;
    private static final NomSemestre UPDATED_NOMSEMESTRE = NomSemestre.Semestre2;

    private static final LocalDate DEFAULT_ANNEE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNEE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_MOYENNE = 1F;
    private static final Float UPDATED_MOYENNE = 2F;

    private static final Serie DEFAULT_SERIE = Serie.STEG;
    private static final Serie UPDATED_SERIE = Serie.STIDD_E;

    private static final Filiere DEFAULT_FILIERE = Filiere.Agricultre;
    private static final Filiere UPDATED_FILIERE = Filiere.Peche;

    private static final Niveau DEFAULT_NIVEAU = Niveau.CAP1;
    private static final Niveau UPDATED_NIVEAU = Niveau.CAP2;

    private static final Float DEFAULT_MOYENNE_GENERALE = 1F;
    private static final Float UPDATED_MOYENNE_GENERALE = 2F;

    private static final String DEFAULT_RANG = "AAAAAAAAAA";
    private static final String UPDATED_RANG = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTE_CONDUITE = 1;
    private static final Integer UPDATED_NOTE_CONDUITE = 2;

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bulletins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BulletinRepository bulletinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBulletinMockMvc;

    private Bulletin bulletin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bulletin createEntity(EntityManager em) {
        Bulletin bulletin = new Bulletin()
            .nomsemestre(DEFAULT_NOMSEMESTRE)
            .annee(DEFAULT_ANNEE)
            .moyenne(DEFAULT_MOYENNE)
            .serie(DEFAULT_SERIE)
            .filiere(DEFAULT_FILIERE)
            .niveau(DEFAULT_NIVEAU)
            .moyenneGenerale(DEFAULT_MOYENNE_GENERALE)
            .rang(DEFAULT_RANG)
            .noteConduite(DEFAULT_NOTE_CONDUITE)
            .matricule(DEFAULT_MATRICULE);
        return bulletin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bulletin createUpdatedEntity(EntityManager em) {
        Bulletin bulletin = new Bulletin()
            .nomsemestre(UPDATED_NOMSEMESTRE)
            .annee(UPDATED_ANNEE)
            .moyenne(UPDATED_MOYENNE)
            .serie(UPDATED_SERIE)
            .filiere(UPDATED_FILIERE)
            .niveau(UPDATED_NIVEAU)
            .moyenneGenerale(UPDATED_MOYENNE_GENERALE)
            .rang(UPDATED_RANG)
            .noteConduite(UPDATED_NOTE_CONDUITE)
            .matricule(UPDATED_MATRICULE);
        return bulletin;
    }

    @BeforeEach
    public void initTest() {
        bulletin = createEntity(em);
    }

    @Test
    @Transactional
    void createBulletin() throws Exception {
        int databaseSizeBeforeCreate = bulletinRepository.findAll().size();
        // Create the Bulletin
        restBulletinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isCreated());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeCreate + 1);
        Bulletin testBulletin = bulletinList.get(bulletinList.size() - 1);
        assertThat(testBulletin.getNomsemestre()).isEqualTo(DEFAULT_NOMSEMESTRE);
        assertThat(testBulletin.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testBulletin.getMoyenne()).isEqualTo(DEFAULT_MOYENNE);
        assertThat(testBulletin.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testBulletin.getFiliere()).isEqualTo(DEFAULT_FILIERE);
        assertThat(testBulletin.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testBulletin.getMoyenneGenerale()).isEqualTo(DEFAULT_MOYENNE_GENERALE);
        assertThat(testBulletin.getRang()).isEqualTo(DEFAULT_RANG);
        assertThat(testBulletin.getNoteConduite()).isEqualTo(DEFAULT_NOTE_CONDUITE);
        assertThat(testBulletin.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    @Transactional
    void createBulletinWithExistingId() throws Exception {
        // Create the Bulletin with an existing ID
        bulletin.setId(1L);

        int databaseSizeBeforeCreate = bulletinRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBulletinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isBadRequest());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bulletinRepository.findAll().size();
        // set the field null
        bulletin.setAnnee(null);

        // Create the Bulletin, which fails.

        restBulletinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isBadRequest());

        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBulletins() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        // Get all the bulletinList
        restBulletinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bulletin.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomsemestre").value(hasItem(DEFAULT_NOMSEMESTRE.toString())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())))
            .andExpect(jsonPath("$.[*].moyenne").value(hasItem(DEFAULT_MOYENNE.doubleValue())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE.toString())))
            .andExpect(jsonPath("$.[*].filiere").value(hasItem(DEFAULT_FILIERE.toString())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())))
            .andExpect(jsonPath("$.[*].moyenneGenerale").value(hasItem(DEFAULT_MOYENNE_GENERALE.doubleValue())))
            .andExpect(jsonPath("$.[*].rang").value(hasItem(DEFAULT_RANG)))
            .andExpect(jsonPath("$.[*].noteConduite").value(hasItem(DEFAULT_NOTE_CONDUITE)))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)));
    }

    @Test
    @Transactional
    void getBulletin() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        // Get the bulletin
        restBulletinMockMvc
            .perform(get(ENTITY_API_URL_ID, bulletin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bulletin.getId().intValue()))
            .andExpect(jsonPath("$.nomsemestre").value(DEFAULT_NOMSEMESTRE.toString()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE.toString()))
            .andExpect(jsonPath("$.moyenne").value(DEFAULT_MOYENNE.doubleValue()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE.toString()))
            .andExpect(jsonPath("$.filiere").value(DEFAULT_FILIERE.toString()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU.toString()))
            .andExpect(jsonPath("$.moyenneGenerale").value(DEFAULT_MOYENNE_GENERALE.doubleValue()))
            .andExpect(jsonPath("$.rang").value(DEFAULT_RANG))
            .andExpect(jsonPath("$.noteConduite").value(DEFAULT_NOTE_CONDUITE))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE));
    }

    @Test
    @Transactional
    void getNonExistingBulletin() throws Exception {
        // Get the bulletin
        restBulletinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBulletin() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();

        // Update the bulletin
        Bulletin updatedBulletin = bulletinRepository.findById(bulletin.getId()).get();
        // Disconnect from session so that the updates on updatedBulletin are not directly saved in db
        em.detach(updatedBulletin);
        updatedBulletin
            .nomsemestre(UPDATED_NOMSEMESTRE)
            .annee(UPDATED_ANNEE)
            .moyenne(UPDATED_MOYENNE)
            .serie(UPDATED_SERIE)
            .filiere(UPDATED_FILIERE)
            .niveau(UPDATED_NIVEAU)
            .moyenneGenerale(UPDATED_MOYENNE_GENERALE)
            .rang(UPDATED_RANG)
            .noteConduite(UPDATED_NOTE_CONDUITE)
            .matricule(UPDATED_MATRICULE);

        restBulletinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBulletin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBulletin))
            )
            .andExpect(status().isOk());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
        Bulletin testBulletin = bulletinList.get(bulletinList.size() - 1);
        assertThat(testBulletin.getNomsemestre()).isEqualTo(UPDATED_NOMSEMESTRE);
        assertThat(testBulletin.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testBulletin.getMoyenne()).isEqualTo(UPDATED_MOYENNE);
        assertThat(testBulletin.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testBulletin.getFiliere()).isEqualTo(UPDATED_FILIERE);
        assertThat(testBulletin.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testBulletin.getMoyenneGenerale()).isEqualTo(UPDATED_MOYENNE_GENERALE);
        assertThat(testBulletin.getRang()).isEqualTo(UPDATED_RANG);
        assertThat(testBulletin.getNoteConduite()).isEqualTo(UPDATED_NOTE_CONDUITE);
        assertThat(testBulletin.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    void putNonExistingBulletin() throws Exception {
        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();
        bulletin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBulletinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bulletin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bulletin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBulletin() throws Exception {
        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();
        bulletin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBulletinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bulletin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBulletin() throws Exception {
        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();
        bulletin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBulletinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBulletinWithPatch() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();

        // Update the bulletin using partial update
        Bulletin partialUpdatedBulletin = new Bulletin();
        partialUpdatedBulletin.setId(bulletin.getId());

        partialUpdatedBulletin
            .moyenne(UPDATED_MOYENNE)
            .filiere(UPDATED_FILIERE)
            .moyenneGenerale(UPDATED_MOYENNE_GENERALE)
            .noteConduite(UPDATED_NOTE_CONDUITE);

        restBulletinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBulletin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBulletin))
            )
            .andExpect(status().isOk());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
        Bulletin testBulletin = bulletinList.get(bulletinList.size() - 1);
        assertThat(testBulletin.getNomsemestre()).isEqualTo(DEFAULT_NOMSEMESTRE);
        assertThat(testBulletin.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testBulletin.getMoyenne()).isEqualTo(UPDATED_MOYENNE);
        assertThat(testBulletin.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testBulletin.getFiliere()).isEqualTo(UPDATED_FILIERE);
        assertThat(testBulletin.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testBulletin.getMoyenneGenerale()).isEqualTo(UPDATED_MOYENNE_GENERALE);
        assertThat(testBulletin.getRang()).isEqualTo(DEFAULT_RANG);
        assertThat(testBulletin.getNoteConduite()).isEqualTo(UPDATED_NOTE_CONDUITE);
        assertThat(testBulletin.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    @Transactional
    void fullUpdateBulletinWithPatch() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();

        // Update the bulletin using partial update
        Bulletin partialUpdatedBulletin = new Bulletin();
        partialUpdatedBulletin.setId(bulletin.getId());

        partialUpdatedBulletin
            .nomsemestre(UPDATED_NOMSEMESTRE)
            .annee(UPDATED_ANNEE)
            .moyenne(UPDATED_MOYENNE)
            .serie(UPDATED_SERIE)
            .filiere(UPDATED_FILIERE)
            .niveau(UPDATED_NIVEAU)
            .moyenneGenerale(UPDATED_MOYENNE_GENERALE)
            .rang(UPDATED_RANG)
            .noteConduite(UPDATED_NOTE_CONDUITE)
            .matricule(UPDATED_MATRICULE);

        restBulletinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBulletin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBulletin))
            )
            .andExpect(status().isOk());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
        Bulletin testBulletin = bulletinList.get(bulletinList.size() - 1);
        assertThat(testBulletin.getNomsemestre()).isEqualTo(UPDATED_NOMSEMESTRE);
        assertThat(testBulletin.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testBulletin.getMoyenne()).isEqualTo(UPDATED_MOYENNE);
        assertThat(testBulletin.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testBulletin.getFiliere()).isEqualTo(UPDATED_FILIERE);
        assertThat(testBulletin.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testBulletin.getMoyenneGenerale()).isEqualTo(UPDATED_MOYENNE_GENERALE);
        assertThat(testBulletin.getRang()).isEqualTo(UPDATED_RANG);
        assertThat(testBulletin.getNoteConduite()).isEqualTo(UPDATED_NOTE_CONDUITE);
        assertThat(testBulletin.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    void patchNonExistingBulletin() throws Exception {
        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();
        bulletin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBulletinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bulletin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bulletin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBulletin() throws Exception {
        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();
        bulletin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBulletinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bulletin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBulletin() throws Exception {
        int databaseSizeBeforeUpdate = bulletinRepository.findAll().size();
        bulletin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBulletinMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bulletin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bulletin in the database
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBulletin() throws Exception {
        // Initialize the database
        bulletinRepository.saveAndFlush(bulletin);

        int databaseSizeBeforeDelete = bulletinRepository.findAll().size();

        // Delete the bulletin
        restBulletinMockMvc
            .perform(delete(ENTITY_API_URL_ID, bulletin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bulletin> bulletinList = bulletinRepository.findAll();
        assertThat(bulletinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
