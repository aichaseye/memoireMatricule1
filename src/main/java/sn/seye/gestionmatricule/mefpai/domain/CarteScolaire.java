package sn.seye.gestionmatricule.mefpai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CarteScolaire.
 */
@Entity
@Table(name = "carte_scolaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarteScolaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Lob
    @Column(name = "photo", nullable = false)
    private byte[] photo;

    @NotNull
    @Column(name = "photo_content_type", nullable = false)
    private String photoContentType;

    @NotNull
    @Column(name = "annee", nullable = false)
    private LocalDate annee;

    @Column(name = "longeur")
    private Integer longeur;

    @Column(name = "largeur")
    private Integer largeur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "apprenants", "bulletins", "releveNotes", "carteScolaires", "etatDemande" }, allowSetters = true)
    private DemandeDossier demandeDossier;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "inscriptions",
            "releveNotes",
            "bulletins",
            "diplomes",
            "observations",
            "carteScolaires",
            "etablissement",
            "niveauEtude",
            "demandeMatApp",
            "demandeDossier",
        },
        allowSetters = true
    )
    private Apprenant apprenant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CarteScolaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public CarteScolaire nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public CarteScolaire prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public CarteScolaire photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public CarteScolaire photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public LocalDate getAnnee() {
        return this.annee;
    }

    public CarteScolaire annee(LocalDate annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(LocalDate annee) {
        this.annee = annee;
    }

    public Integer getLongeur() {
        return this.longeur;
    }

    public CarteScolaire longeur(Integer longeur) {
        this.setLongeur(longeur);
        return this;
    }

    public void setLongeur(Integer longeur) {
        this.longeur = longeur;
    }

    public Integer getLargeur() {
        return this.largeur;
    }

    public CarteScolaire largeur(Integer largeur) {
        this.setLargeur(largeur);
        return this;
    }

    public void setLargeur(Integer largeur) {
        this.largeur = largeur;
    }

    public DemandeDossier getDemandeDossier() {
        return this.demandeDossier;
    }

    public void setDemandeDossier(DemandeDossier demandeDossier) {
        this.demandeDossier = demandeDossier;
    }

    public CarteScolaire demandeDossier(DemandeDossier demandeDossier) {
        this.setDemandeDossier(demandeDossier);
        return this;
    }

    public Apprenant getApprenant() {
        return this.apprenant;
    }

    public void setApprenant(Apprenant apprenant) {
        this.apprenant = apprenant;
    }

    public CarteScolaire apprenant(Apprenant apprenant) {
        this.setApprenant(apprenant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarteScolaire)) {
            return false;
        }
        return id != null && id.equals(((CarteScolaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarteScolaire{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", annee='" + getAnnee() + "'" +
            ", longeur=" + getLongeur() +
            ", largeur=" + getLargeur() +
            "}";
    }
}
