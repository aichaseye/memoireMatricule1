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
import sn.seye.gestionmatricule.mefpai.domain.CarteScolaire;
import sn.seye.gestionmatricule.mefpai.repository.CarteScolaireRepository;

/**
 * Integration tests for the {@link CarteScolaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarteScolaireResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_ANNEE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNEE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_LONGEUR = 1;
    private static final Integer UPDATED_LONGEUR = 2;

    private static final Integer DEFAULT_LARGEUR = 1;
    private static final Integer UPDATED_LARGEUR = 2;

    private static final String ENTITY_API_URL = "/api/carte-scolaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarteScolaireRepository carteScolaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarteScolaireMockMvc;

    private CarteScolaire carteScolaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteScolaire createEntity(EntityManager em) {
        CarteScolaire carteScolaire = new CarteScolaire()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .annee(DEFAULT_ANNEE)
            .longeur(DEFAULT_LONGEUR)
            .largeur(DEFAULT_LARGEUR);
        return carteScolaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteScolaire createUpdatedEntity(EntityManager em) {
        CarteScolaire carteScolaire = new CarteScolaire()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .annee(UPDATED_ANNEE)
            .longeur(UPDATED_LONGEUR)
            .largeur(UPDATED_LARGEUR);
        return carteScolaire;
    }

    @BeforeEach
    public void initTest() {
        carteScolaire = createEntity(em);
    }

    @Test
    @Transactional
    void createCarteScolaire() throws Exception {
        int databaseSizeBeforeCreate = carteScolaireRepository.findAll().size();
        // Create the CarteScolaire
        restCarteScolaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteScolaire)))
            .andExpect(status().isCreated());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeCreate + 1);
        CarteScolaire testCarteScolaire = carteScolaireList.get(carteScolaireList.size() - 1);
        assertThat(testCarteScolaire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCarteScolaire.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCarteScolaire.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCarteScolaire.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testCarteScolaire.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testCarteScolaire.getLongeur()).isEqualTo(DEFAULT_LONGEUR);
        assertThat(testCarteScolaire.getLargeur()).isEqualTo(DEFAULT_LARGEUR);
    }

    @Test
    @Transactional
    void createCarteScolaireWithExistingId() throws Exception {
        // Create the CarteScolaire with an existing ID
        carteScolaire.setId(1L);

        int databaseSizeBeforeCreate = carteScolaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteScolaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteScolaire)))
            .andExpect(status().isBadRequest());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteScolaireRepository.findAll().size();
        // set the field null
        carteScolaire.setNom(null);

        // Create the CarteScolaire, which fails.

        restCarteScolaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteScolaire)))
            .andExpect(status().isBadRequest());

        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteScolaireRepository.findAll().size();
        // set the field null
        carteScolaire.setPrenom(null);

        // Create the CarteScolaire, which fails.

        restCarteScolaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteScolaire)))
            .andExpect(status().isBadRequest());

        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteScolaireRepository.findAll().size();
        // set the field null
        carteScolaire.setAnnee(null);

        // Create the CarteScolaire, which fails.

        restCarteScolaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteScolaire)))
            .andExpect(status().isBadRequest());

        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarteScolaires() throws Exception {
        // Initialize the database
        carteScolaireRepository.saveAndFlush(carteScolaire);

        // Get all the carteScolaireList
        restCarteScolaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteScolaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())))
            .andExpect(jsonPath("$.[*].longeur").value(hasItem(DEFAULT_LONGEUR)))
            .andExpect(jsonPath("$.[*].largeur").value(hasItem(DEFAULT_LARGEUR)));
    }

    @Test
    @Transactional
    void getCarteScolaire() throws Exception {
        // Initialize the database
        carteScolaireRepository.saveAndFlush(carteScolaire);

        // Get the carteScolaire
        restCarteScolaireMockMvc
            .perform(get(ENTITY_API_URL_ID, carteScolaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carteScolaire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE.toString()))
            .andExpect(jsonPath("$.longeur").value(DEFAULT_LONGEUR))
            .andExpect(jsonPath("$.largeur").value(DEFAULT_LARGEUR));
    }

    @Test
    @Transactional
    void getNonExistingCarteScolaire() throws Exception {
        // Get the carteScolaire
        restCarteScolaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCarteScolaire() throws Exception {
        // Initialize the database
        carteScolaireRepository.saveAndFlush(carteScolaire);

        int databaseSizeBeforeUpdate = carteScolaireRepository.findAll().size();

        // Update the carteScolaire
        CarteScolaire updatedCarteScolaire = carteScolaireRepository.findById(carteScolaire.getId()).get();
        // Disconnect from session so that the updates on updatedCarteScolaire are not directly saved in db
        em.detach(updatedCarteScolaire);
        updatedCarteScolaire
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .annee(UPDATED_ANNEE)
            .longeur(UPDATED_LONGEUR)
            .largeur(UPDATED_LARGEUR);

        restCarteScolaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCarteScolaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCarteScolaire))
            )
            .andExpect(status().isOk());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeUpdate);
        CarteScolaire testCarteScolaire = carteScolaireList.get(carteScolaireList.size() - 1);
        assertThat(testCarteScolaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCarteScolaire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCarteScolaire.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCarteScolaire.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCarteScolaire.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testCarteScolaire.getLongeur()).isEqualTo(UPDATED_LONGEUR);
        assertThat(testCarteScolaire.getLargeur()).isEqualTo(UPDATED_LARGEUR);
    }

    @Test
    @Transactional
    void putNonExistingCarteScolaire() throws Exception {
        int databaseSizeBeforeUpdate = carteScolaireRepository.findAll().size();
        carteScolaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteScolaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carteScolaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteScolaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarteScolaire() throws Exception {
        int databaseSizeBeforeUpdate = carteScolaireRepository.findAll().size();
        carteScolaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteScolaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteScolaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarteScolaire() throws Exception {
        int databaseSizeBeforeUpdate = carteScolaireRepository.findAll().size();
        carteScolaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteScolaireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteScolaire)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarteScolaireWithPatch() throws Exception {
        // Initialize the database
        carteScolaireRepository.saveAndFlush(carteScolaire);

        int databaseSizeBeforeUpdate = carteScolaireRepository.findAll().size();

        // Update the carteScolaire using partial update
        CarteScolaire partialUpdatedCarteScolaire = new CarteScolaire();
        partialUpdatedCarteScolaire.setId(carteScolaire.getId());

        partialUpdatedCarteScolaire
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .largeur(UPDATED_LARGEUR);

        restCarteScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarteScolaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarteScolaire))
            )
            .andExpect(status().isOk());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeUpdate);
        CarteScolaire testCarteScolaire = carteScolaireList.get(carteScolaireList.size() - 1);
        assertThat(testCarteScolaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCarteScolaire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCarteScolaire.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCarteScolaire.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCarteScolaire.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testCarteScolaire.getLongeur()).isEqualTo(DEFAULT_LONGEUR);
        assertThat(testCarteScolaire.getLargeur()).isEqualTo(UPDATED_LARGEUR);
    }

    @Test
    @Transactional
    void fullUpdateCarteScolaireWithPatch() throws Exception {
        // Initialize the database
        carteScolaireRepository.saveAndFlush(carteScolaire);

        int databaseSizeBeforeUpdate = carteScolaireRepository.findAll().size();

        // Update the carteScolaire using partial update
        CarteScolaire partialUpdatedCarteScolaire = new CarteScolaire();
        partialUpdatedCarteScolaire.setId(carteScolaire.getId());

        partialUpdatedCarteScolaire
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .annee(UPDATED_ANNEE)
            .longeur(UPDATED_LONGEUR)
            .largeur(UPDATED_LARGEUR);

        restCarteScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarteScolaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarteScolaire))
            )
            .andExpect(status().isOk());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeUpdate);
        CarteScolaire testCarteScolaire = carteScolaireList.get(carteScolaireList.size() - 1);
        assertThat(testCarteScolaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCarteScolaire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCarteScolaire.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCarteScolaire.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCarteScolaire.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testCarteScolaire.getLongeur()).isEqualTo(UPDATED_LONGEUR);
        assertThat(testCarteScolaire.getLargeur()).isEqualTo(UPDATED_LARGEUR);
    }

    @Test
    @Transactional
    void patchNonExistingCarteScolaire() throws Exception {
        int databaseSizeBeforeUpdate = carteScolaireRepository.findAll().size();
        carteScolaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carteScolaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteScolaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarteScolaire() throws Exception {
        int databaseSizeBeforeUpdate = carteScolaireRepository.findAll().size();
        carteScolaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteScolaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarteScolaire() throws Exception {
        int databaseSizeBeforeUpdate = carteScolaireRepository.findAll().size();
        carteScolaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carteScolaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarteScolaire in the database
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarteScolaire() throws Exception {
        // Initialize the database
        carteScolaireRepository.saveAndFlush(carteScolaire);

        int databaseSizeBeforeDelete = carteScolaireRepository.findAll().size();

        // Delete the carteScolaire
        restCarteScolaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, carteScolaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarteScolaire> carteScolaireList = carteScolaireRepository.findAll();
        assertThat(carteScolaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
