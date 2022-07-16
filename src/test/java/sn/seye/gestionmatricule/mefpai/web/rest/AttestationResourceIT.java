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
import org.springframework.util.Base64Utils;
import sn.seye.gestionmatricule.mefpai.IntegrationTest;
import sn.seye.gestionmatricule.mefpai.domain.Attestation;
import sn.seye.gestionmatricule.mefpai.repository.AttestationRepository;

/**
 * Integration tests for the {@link AttestationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttestationResourceIT {

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/attestations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttestationRepository attestationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttestationMockMvc;

    private Attestation attestation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attestation createEntity(EntityManager em) {
        Attestation attestation = new Attestation().photo(DEFAULT_PHOTO).photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return attestation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attestation createUpdatedEntity(EntityManager em) {
        Attestation attestation = new Attestation().photo(UPDATED_PHOTO).photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        return attestation;
    }

    @BeforeEach
    public void initTest() {
        attestation = createEntity(em);
    }

    @Test
    @Transactional
    void createAttestation() throws Exception {
        int databaseSizeBeforeCreate = attestationRepository.findAll().size();
        // Create the Attestation
        restAttestationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attestation)))
            .andExpect(status().isCreated());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeCreate + 1);
        Attestation testAttestation = attestationList.get(attestationList.size() - 1);
        assertThat(testAttestation.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testAttestation.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createAttestationWithExistingId() throws Exception {
        // Create the Attestation with an existing ID
        attestation.setId(1L);

        int databaseSizeBeforeCreate = attestationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttestationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attestation)))
            .andExpect(status().isBadRequest());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttestations() throws Exception {
        // Initialize the database
        attestationRepository.saveAndFlush(attestation);

        // Get all the attestationList
        restAttestationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attestation.getId().intValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    void getAttestation() throws Exception {
        // Initialize the database
        attestationRepository.saveAndFlush(attestation);

        // Get the attestation
        restAttestationMockMvc
            .perform(get(ENTITY_API_URL_ID, attestation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attestation.getId().intValue()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    void getNonExistingAttestation() throws Exception {
        // Get the attestation
        restAttestationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttestation() throws Exception {
        // Initialize the database
        attestationRepository.saveAndFlush(attestation);

        int databaseSizeBeforeUpdate = attestationRepository.findAll().size();

        // Update the attestation
        Attestation updatedAttestation = attestationRepository.findById(attestation.getId()).get();
        // Disconnect from session so that the updates on updatedAttestation are not directly saved in db
        em.detach(updatedAttestation);
        updatedAttestation.photo(UPDATED_PHOTO).photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restAttestationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAttestation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAttestation))
            )
            .andExpect(status().isOk());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeUpdate);
        Attestation testAttestation = attestationList.get(attestationList.size() - 1);
        assertThat(testAttestation.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testAttestation.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingAttestation() throws Exception {
        int databaseSizeBeforeUpdate = attestationRepository.findAll().size();
        attestation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttestationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attestation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attestation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttestation() throws Exception {
        int databaseSizeBeforeUpdate = attestationRepository.findAll().size();
        attestation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttestationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attestation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttestation() throws Exception {
        int databaseSizeBeforeUpdate = attestationRepository.findAll().size();
        attestation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttestationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attestation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttestationWithPatch() throws Exception {
        // Initialize the database
        attestationRepository.saveAndFlush(attestation);

        int databaseSizeBeforeUpdate = attestationRepository.findAll().size();

        // Update the attestation using partial update
        Attestation partialUpdatedAttestation = new Attestation();
        partialUpdatedAttestation.setId(attestation.getId());

        partialUpdatedAttestation.photo(UPDATED_PHOTO).photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restAttestationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttestation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttestation))
            )
            .andExpect(status().isOk());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeUpdate);
        Attestation testAttestation = attestationList.get(attestationList.size() - 1);
        assertThat(testAttestation.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testAttestation.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateAttestationWithPatch() throws Exception {
        // Initialize the database
        attestationRepository.saveAndFlush(attestation);

        int databaseSizeBeforeUpdate = attestationRepository.findAll().size();

        // Update the attestation using partial update
        Attestation partialUpdatedAttestation = new Attestation();
        partialUpdatedAttestation.setId(attestation.getId());

        partialUpdatedAttestation.photo(UPDATED_PHOTO).photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restAttestationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttestation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttestation))
            )
            .andExpect(status().isOk());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeUpdate);
        Attestation testAttestation = attestationList.get(attestationList.size() - 1);
        assertThat(testAttestation.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testAttestation.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingAttestation() throws Exception {
        int databaseSizeBeforeUpdate = attestationRepository.findAll().size();
        attestation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttestationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attestation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attestation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttestation() throws Exception {
        int databaseSizeBeforeUpdate = attestationRepository.findAll().size();
        attestation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttestationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attestation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttestation() throws Exception {
        int databaseSizeBeforeUpdate = attestationRepository.findAll().size();
        attestation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttestationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(attestation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attestation in the database
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttestation() throws Exception {
        // Initialize the database
        attestationRepository.saveAndFlush(attestation);

        int databaseSizeBeforeDelete = attestationRepository.findAll().size();

        // Delete the attestation
        restAttestationMockMvc
            .perform(delete(ENTITY_API_URL_ID, attestation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attestation> attestationList = attestationRepository.findAll();
        assertThat(attestationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
