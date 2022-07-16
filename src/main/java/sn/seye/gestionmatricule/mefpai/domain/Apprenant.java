package sn.seye.gestionmatricule.mefpai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Nationalite;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Sexe;

/**
 * A Apprenant.
 */
@Entity
@Table(name = "apprenant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Apprenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom_complet_app", nullable = false)
    private String nomCompletApp;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "matricule_app")
    private String matriculeApp;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nationalite", nullable = false)
    private Nationalite nationalite;

    @OneToMany(mappedBy = "apprenant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apprenant", "classe" }, allowSetters = true)
    private Set<Inscription> inscriptions = new HashSet<>();

    @OneToMany(mappedBy = "apprenant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apprenant", "filiereEtude", "serieEtude", "demandeDossier", "programmes" }, allowSetters = true)
    private Set<ReleveNote> releveNotes = new HashSet<>();

    @OneToMany(mappedBy = "apprenant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apprenant", "demandeDossier" }, allowSetters = true)
    private Set<Bulletin> bulletins = new HashSet<>();

    @OneToMany(mappedBy = "apprenant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etablissement", "apprenant", "etatDemande" }, allowSetters = true)
    private Set<Diplome> diplomes = new HashSet<>();

    @OneToMany(mappedBy = "apprenant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apprenant" }, allowSetters = true)
    private Set<Observation> observations = new HashSet<>();

    @OneToMany(mappedBy = "apprenant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demandeDossier", "apprenant" }, allowSetters = true)
    private Set<CarteScolaire> carteScolaires = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "bons",
            "apprenants",
            "personnel",
            "classes",
            "diplomes",
            "demandeMatEtabs",
            "localite",
            "inspection",
            "filiereEtude",
            "serieEtude",
        },
        allowSetters = true
    )
    private Etablissement etablissement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "classes", "apprenants" }, allowSetters = true)
    private NiveauEtude niveauEtude;

    @ManyToOne
    @JsonIgnoreProperties(value = { "apprenants" }, allowSetters = true)
    private DemandeMatApp demandeMatApp;

    @ManyToOne
    @JsonIgnoreProperties(value = { "apprenants", "bulletins", "releveNotes", "carteScolaires", "etatDemande" }, allowSetters = true)
    private DemandeDossier demandeDossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Apprenant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCompletApp() {
        return this.nomCompletApp;
    }

    public Apprenant nomCompletApp(String nomCompletApp) {
        this.setNomCompletApp(nomCompletApp);
        return this;
    }

    public void setNomCompletApp(String nomCompletApp) {
        this.nomCompletApp = nomCompletApp;
    }

    public String getEmail() {
        return this.email;
    }

    public Apprenant email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Apprenant telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public Apprenant dateNaissance(LocalDate dateNaissance) {
        this.setDateNaissance(dateNaissance);
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Apprenant photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Apprenant photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Apprenant adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMatriculeApp() {
        return this.matriculeApp;
    }

    public Apprenant matriculeApp(String matriculeApp) {
        this.setMatriculeApp(matriculeApp);
        return this;
    }

    public void setMatriculeApp(String matriculeApp) {
        this.matriculeApp = matriculeApp;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Apprenant sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Nationalite getNationalite() {
        return this.nationalite;
    }

    public Apprenant nationalite(Nationalite nationalite) {
        this.setNationalite(nationalite);
        return this;
    }

    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    public Set<Inscription> getInscriptions() {
        return this.inscriptions;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        if (this.inscriptions != null) {
            this.inscriptions.forEach(i -> i.setApprenant(null));
        }
        if (inscriptions != null) {
            inscriptions.forEach(i -> i.setApprenant(this));
        }
        this.inscriptions = inscriptions;
    }

    public Apprenant inscriptions(Set<Inscription> inscriptions) {
        this.setInscriptions(inscriptions);
        return this;
    }

    public Apprenant addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setApprenant(this);
        return this;
    }

    public Apprenant removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setApprenant(null);
        return this;
    }

    public Set<ReleveNote> getReleveNotes() {
        return this.releveNotes;
    }

    public void setReleveNotes(Set<ReleveNote> releveNotes) {
        if (this.releveNotes != null) {
            this.releveNotes.forEach(i -> i.setApprenant(null));
        }
        if (releveNotes != null) {
            releveNotes.forEach(i -> i.setApprenant(this));
        }
        this.releveNotes = releveNotes;
    }

    public Apprenant releveNotes(Set<ReleveNote> releveNotes) {
        this.setReleveNotes(releveNotes);
        return this;
    }

    public Apprenant addReleveNote(ReleveNote releveNote) {
        this.releveNotes.add(releveNote);
        releveNote.setApprenant(this);
        return this;
    }

    public Apprenant removeReleveNote(ReleveNote releveNote) {
        this.releveNotes.remove(releveNote);
        releveNote.setApprenant(null);
        return this;
    }

    public Set<Bulletin> getBulletins() {
        return this.bulletins;
    }

    public void setBulletins(Set<Bulletin> bulletins) {
        if (this.bulletins != null) {
            this.bulletins.forEach(i -> i.setApprenant(null));
        }
        if (bulletins != null) {
            bulletins.forEach(i -> i.setApprenant(this));
        }
        this.bulletins = bulletins;
    }

    public Apprenant bulletins(Set<Bulletin> bulletins) {
        this.setBulletins(bulletins);
        return this;
    }

    public Apprenant addBulletin(Bulletin bulletin) {
        this.bulletins.add(bulletin);
        bulletin.setApprenant(this);
        return this;
    }

    public Apprenant removeBulletin(Bulletin bulletin) {
        this.bulletins.remove(bulletin);
        bulletin.setApprenant(null);
        return this;
    }

    public Set<Diplome> getDiplomes() {
        return this.diplomes;
    }

    public void setDiplomes(Set<Diplome> diplomes) {
        if (this.diplomes != null) {
            this.diplomes.forEach(i -> i.setApprenant(null));
        }
        if (diplomes != null) {
            diplomes.forEach(i -> i.setApprenant(this));
        }
        this.diplomes = diplomes;
    }

    public Apprenant diplomes(Set<Diplome> diplomes) {
        this.setDiplomes(diplomes);
        return this;
    }

    public Apprenant addDiplome(Diplome diplome) {
        this.diplomes.add(diplome);
        diplome.setApprenant(this);
        return this;
    }

    public Apprenant removeDiplome(Diplome diplome) {
        this.diplomes.remove(diplome);
        diplome.setApprenant(null);
        return this;
    }

    public Set<Observation> getObservations() {
        return this.observations;
    }

    public void setObservations(Set<Observation> observations) {
        if (this.observations != null) {
            this.observations.forEach(i -> i.setApprenant(null));
        }
        if (observations != null) {
            observations.forEach(i -> i.setApprenant(this));
        }
        this.observations = observations;
    }

    public Apprenant observations(Set<Observation> observations) {
        this.setObservations(observations);
        return this;
    }

    public Apprenant addObservation(Observation observation) {
        this.observations.add(observation);
        observation.setApprenant(this);
        return this;
    }

    public Apprenant removeObservation(Observation observation) {
        this.observations.remove(observation);
        observation.setApprenant(null);
        return this;
    }

    public Set<CarteScolaire> getCarteScolaires() {
        return this.carteScolaires;
    }

    public void setCarteScolaires(Set<CarteScolaire> carteScolaires) {
        if (this.carteScolaires != null) {
            this.carteScolaires.forEach(i -> i.setApprenant(null));
        }
        if (carteScolaires != null) {
            carteScolaires.forEach(i -> i.setApprenant(this));
        }
        this.carteScolaires = carteScolaires;
    }

    public Apprenant carteScolaires(Set<CarteScolaire> carteScolaires) {
        this.setCarteScolaires(carteScolaires);
        return this;
    }

    public Apprenant addCarteScolaire(CarteScolaire carteScolaire) {
        this.carteScolaires.add(carteScolaire);
        carteScolaire.setApprenant(this);
        return this;
    }

    public Apprenant removeCarteScolaire(CarteScolaire carteScolaire) {
        this.carteScolaires.remove(carteScolaire);
        carteScolaire.setApprenant(null);
        return this;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Apprenant etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Apprenant niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public DemandeMatApp getDemandeMatApp() {
        return this.demandeMatApp;
    }

    public void setDemandeMatApp(DemandeMatApp demandeMatApp) {
        this.demandeMatApp = demandeMatApp;
    }

    public Apprenant demandeMatApp(DemandeMatApp demandeMatApp) {
        this.setDemandeMatApp(demandeMatApp);
        return this;
    }

    public DemandeDossier getDemandeDossier() {
        return this.demandeDossier;
    }

    public void setDemandeDossier(DemandeDossier demandeDossier) {
        this.demandeDossier = demandeDossier;
    }

    public Apprenant demandeDossier(DemandeDossier demandeDossier) {
        this.setDemandeDossier(demandeDossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Apprenant)) {
            return false;
        }
        return id != null && id.equals(((Apprenant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apprenant{" +
            "id=" + getId() +
            ", nomCompletApp='" + getNomCompletApp() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", matriculeApp='" + getMatriculeApp() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", nationalite='" + getNationalite() + "'" +
            "}";
    }
}
