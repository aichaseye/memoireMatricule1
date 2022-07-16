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
import sn.seye.gestionmatricule.mefpai.domain.Observation;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Apte;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Asuduite;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Ponctualite;
import sn.seye.gestionmatricule.mefpai.repository.ObservationRepository;

/**
 * Integration tests for the {@link ObservationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObservationResourceIT {

    private static final Asuduite DEFAULT_ASUDUITE = Asuduite.OUI;
    private static final Asuduite UPDATED_ASUDUITE = Asuduite.NON;

    private static final Ponctualite DEFAULT_PONCTUALITE = Ponctualite.OUI;
    private static final Ponctualite UPDATED_PONCTUALITE = Ponctualite.NON;

    private static final Apte DEFAULT_APTE = Apte.OUI;
    private static final Apte UPDATED_APTE = Apte.NON;

    private static final String ENTITY_API_URL = "/api/observations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObservationMockMvc;

    private Observation observation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Observation createEntity(EntityManager em) {
        Observation observation = new Observation().asuduite(DEFAULT_ASUDUITE).ponctualite(DEFAULT_PONCTUALITE).apte(DEFAULT_APTE);
        return observation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Observation createUpdatedEntity(EntityManager em) {
        Observation observation = new Observation().asuduite(UPDATED_ASUDUITE).ponctualite(UPDATED_PONCTUALITE).apte(UPDATED_APTE);
        return observation;
    }

    @BeforeEach
    public void initTest() {
        observation = createEntity(em);
    }

    @Test
    @Transactional
    void createObservation() throws Exception {
        int databaseSizeBeforeCreate = observationRepository.findAll().size();
        // Create the Observation
        restObservationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(observation)))
            .andExpect(status().isCreated());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate + 1);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getAsuduite()).isEqualTo(DEFAULT_ASUDUITE);
        assertThat(testObservation.getPonctualite()).isEqualTo(DEFAULT_PONCTUALITE);
        assertThat(testObservation.getApte()).isEqualTo(DEFAULT_APTE);
    }

    @Test
    @Transactional
    void createObservationWithExistingId() throws Exception {
        // Create the Observation with an existing ID
        observation.setId(1L);

        int databaseSizeBeforeCreate = observationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(observation)))
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllObservations() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList
        restObservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observation.getId().intValue())))
            .andExpect(jsonPath("$.[*].asuduite").value(hasItem(DEFAULT_ASUDUITE.toString())))
            .andExpect(jsonPath("$.[*].ponctualite").value(hasItem(DEFAULT_PONCTUALITE.toString())))
            .andExpect(jsonPath("$.[*].apte").value(hasItem(DEFAULT_APTE.toString())));
    }

    @Test
    @Transactional
    void getObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get the observation
        restObservationMockMvc
            .perform(get(ENTITY_API_URL_ID, observation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(observation.getId().intValue()))
            .andExpect(jsonPath("$.asuduite").value(DEFAULT_ASUDUITE.toString()))
            .andExpect(jsonPath("$.ponctualite").value(DEFAULT_PONCTUALITE.toString()))
            .andExpect(jsonPath("$.apte").value(DEFAULT_APTE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingObservation() throws Exception {
        // Get the observation
        restObservationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Update the observation
        Observation updatedObservation = observationRepository.findById(observation.getId()).get();
        // Disconnect from session so that the updates on updatedObservation are not directly saved in db
        em.detach(updatedObservation);
        updatedObservation.asuduite(UPDATED_ASUDUITE).ponctualite(UPDATED_PONCTUALITE).apte(UPDATED_APTE);

        restObservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedObservation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedObservation))
            )
            .andExpect(status().isOk());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getAsuduite()).isEqualTo(UPDATED_ASUDUITE);
        assertThat(testObservation.getPonctualite()).isEqualTo(UPDATED_PONCTUALITE);
        assertThat(testObservation.getApte()).isEqualTo(UPDATED_APTE);
    }

    @Test
    @Transactional
    void putNonExistingObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, observation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(observation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObservationWithPatch() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Update the observation using partial update
        Observation partialUpdatedObservation = new Observation();
        partialUpdatedObservation.setId(observation.getId());

        partialUpdatedObservation.ponctualite(UPDATED_PONCTUALITE).apte(UPDATED_APTE);

        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObservation))
            )
            .andExpect(status().isOk());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getAsuduite()).isEqualTo(DEFAULT_ASUDUITE);
        assertThat(testObservation.getPonctualite()).isEqualTo(UPDATED_PONCTUALITE);
        assertThat(testObservation.getApte()).isEqualTo(UPDATED_APTE);
    }

    @Test
    @Transactional
    void fullUpdateObservationWithPatch() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Update the observation using partial update
        Observation partialUpdatedObservation = new Observation();
        partialUpdatedObservation.setId(observation.getId());

        partialUpdatedObservation.asuduite(UPDATED_ASUDUITE).ponctualite(UPDATED_PONCTUALITE).apte(UPDATED_APTE);

        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObservation))
            )
            .andExpect(status().isOk());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getAsuduite()).isEqualTo(UPDATED_ASUDUITE);
        assertThat(testObservation.getPonctualite()).isEqualTo(UPDATED_PONCTUALITE);
        assertThat(testObservation.getApte()).isEqualTo(UPDATED_APTE);
    }

    @Test
    @Transactional
    void patchNonExistingObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, observation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(observation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(observation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(observation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeDelete = observationRepository.findAll().size();

        // Delete the observation
        restObservationMockMvc
            .perform(delete(ENTITY_API_URL_ID, observation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
