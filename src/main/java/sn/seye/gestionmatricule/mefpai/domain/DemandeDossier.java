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
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Filiere;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Niveau;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.NomDiplome;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.NomSemestre;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Serie;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.TypeDossier;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.TypeReleve;

/**
 * A DemandeDossier.
 */
@Entity
@Table(name = "demande_dossier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeDossier implements Serializable {

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

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_dossier", nullable = false)
    private TypeDossier typeDossier;

    @NotNull
    @Column(name = "annee", nullable = false)
    private LocalDate annee;

    @Enumerated(EnumType.STRING)
    @Column(name = "serie")
    private Serie serie;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "filiere", nullable = false)
    private Filiere filiere;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nom_semestre", nullable = false)
    private NomSemestre nomSemestre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "niveau", nullable = false)
    private Niveau niveau;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_releve", nullable = false)
    private TypeReleve typeReleve;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nom_diplome", nullable = false)
    private NomDiplome nomDiplome;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @OneToMany(mappedBy = "demandeDossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Apprenant> apprenants = new HashSet<>();

    @OneToMany(mappedBy = "demandeDossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apprenant", "demandeDossier" }, allowSetters = true)
    private Set<Bulletin> bulletins = new HashSet<>();

    @OneToMany(mappedBy = "demandeDossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apprenant", "filiereEtude", "serieEtude", "demandeDossier", "programmes" }, allowSetters = true)
    private Set<ReleveNote> releveNotes = new HashSet<>();

    @OneToMany(mappedBy = "demandeDossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demandeDossier", "apprenant" }, allowSetters = true)
    private Set<CarteScolaire> carteScolaires = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "demandeDossiers", "diplomes", "attestations" }, allowSetters = true)
    private EtatDemande etatDemande;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeDossier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public DemandeDossier nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public DemandeDossier prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public DemandeDossier email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public DemandeDossier dateNaissance(LocalDate dateNaissance) {
        this.setDateNaissance(dateNaissance);
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public DemandeDossier matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public TypeDossier getTypeDossier() {
        return this.typeDossier;
    }

    public DemandeDossier typeDossier(TypeDossier typeDossier) {
        this.setTypeDossier(typeDossier);
        return this;
    }

    public void setTypeDossier(TypeDossier typeDossier) {
        this.typeDossier = typeDossier;
    }

    public LocalDate getAnnee() {
        return this.annee;
    }

    public DemandeDossier annee(LocalDate annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(LocalDate annee) {
        this.annee = annee;
    }

    public Serie getSerie() {
        return this.serie;
    }

    public DemandeDossier serie(Serie serie) {
        this.setSerie(serie);
        return this;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Filiere getFiliere() {
        return this.filiere;
    }

    public DemandeDossier filiere(Filiere filiere) {
        this.setFiliere(filiere);
        return this;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public NomSemestre getNomSemestre() {
        return this.nomSemestre;
    }

    public DemandeDossier nomSemestre(NomSemestre nomSemestre) {
        this.setNomSemestre(nomSemestre);
        return this;
    }

    public void setNomSemestre(NomSemestre nomSemestre) {
        this.nomSemestre = nomSemestre;
    }

    public Niveau getNiveau() {
        return this.niveau;
    }

    public DemandeDossier niveau(Niveau niveau) {
        this.setNiveau(niveau);
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public TypeReleve getTypeReleve() {
        return this.typeReleve;
    }

    public DemandeDossier typeReleve(TypeReleve typeReleve) {
        this.setTypeReleve(typeReleve);
        return this;
    }

    public void setTypeReleve(TypeReleve typeReleve) {
        this.typeReleve = typeReleve;
    }

    public NomDiplome getNomDiplome() {
        return this.nomDiplome;
    }

    public DemandeDossier nomDiplome(NomDiplome nomDiplome) {
        this.setNomDiplome(nomDiplome);
        return this;
    }

    public void setNomDiplome(NomDiplome nomDiplome) {
        this.nomDiplome = nomDiplome;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public DemandeDossier photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public DemandeDossier photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Set<Apprenant> getApprenants() {
        return this.apprenants;
    }

    public void setApprenants(Set<Apprenant> apprenants) {
        if (this.apprenants != null) {
            this.apprenants.forEach(i -> i.setDemandeDossier(null));
        }
        if (apprenants != null) {
            apprenants.forEach(i -> i.setDemandeDossier(this));
        }
        this.apprenants = apprenants;
    }

    public DemandeDossier apprenants(Set<Apprenant> apprenants) {
        this.setApprenants(apprenants);
        return this;
    }

    public DemandeDossier addApprenant(Apprenant apprenant) {
        this.apprenants.add(apprenant);
        apprenant.setDemandeDossier(this);
        return this;
    }

    public DemandeDossier removeApprenant(Apprenant apprenant) {
        this.apprenants.remove(apprenant);
        apprenant.setDemandeDossier(null);
        return this;
    }

    public Set<Bulletin> getBulletins() {
        return this.bulletins;
    }

    public void setBulletins(Set<Bulletin> bulletins) {
        if (this.bulletins != null) {
            this.bulletins.forEach(i -> i.setDemandeDossier(null));
        }
        if (bulletins != null) {
            bulletins.forEach(i -> i.setDemandeDossier(this));
        }
        this.bulletins = bulletins;
    }

    public DemandeDossier bulletins(Set<Bulletin> bulletins) {
        this.setBulletins(bulletins);
        return this;
    }

    public DemandeDossier addBulletin(Bulletin bulletin) {
        this.bulletins.add(bulletin);
        bulletin.setDemandeDossier(this);
        return this;
    }

    public DemandeDossier removeBulletin(Bulletin bulletin) {
        this.bulletins.remove(bulletin);
        bulletin.setDemandeDossier(null);
        return this;
    }

    public Set<ReleveNote> getReleveNotes() {
        return this.releveNotes;
    }

    public void setReleveNotes(Set<ReleveNote> releveNotes) {
        if (this.releveNotes != null) {
            this.releveNotes.forEach(i -> i.setDemandeDossier(null));
        }
        if (releveNotes != null) {
            releveNotes.forEach(i -> i.setDemandeDossier(this));
        }
        this.releveNotes = releveNotes;
    }

    public DemandeDossier releveNotes(Set<ReleveNote> releveNotes) {
        this.setReleveNotes(releveNotes);
        return this;
    }

    public DemandeDossier addReleveNote(ReleveNote releveNote) {
        this.releveNotes.add(releveNote);
        releveNote.setDemandeDossier(this);
        return this;
    }

    public DemandeDossier removeReleveNote(ReleveNote releveNote) {
        this.releveNotes.remove(releveNote);
        releveNote.setDemandeDossier(null);
        return this;
    }

    public Set<CarteScolaire> getCarteScolaires() {
        return this.carteScolaires;
    }

    public void setCarteScolaires(Set<CarteScolaire> carteScolaires) {
        if (this.carteScolaires != null) {
            this.carteScolaires.forEach(i -> i.setDemandeDossier(null));
        }
        if (carteScolaires != null) {
            carteScolaires.forEach(i -> i.setDemandeDossier(this));
        }
        this.carteScolaires = carteScolaires;
    }

    public DemandeDossier carteScolaires(Set<CarteScolaire> carteScolaires) {
        this.setCarteScolaires(carteScolaires);
        return this;
    }

    public DemandeDossier addCarteScolaire(CarteScolaire carteScolaire) {
        this.carteScolaires.add(carteScolaire);
        carteScolaire.setDemandeDossier(this);
        return this;
    }

    public DemandeDossier removeCarteScolaire(CarteScolaire carteScolaire) {
        this.carteScolaires.remove(carteScolaire);
        carteScolaire.setDemandeDossier(null);
        return this;
    }

    public EtatDemande getEtatDemande() {
        return this.etatDemande;
    }

    public void setEtatDemande(EtatDemande etatDemande) {
        this.etatDemande = etatDemande;
    }

    public DemandeDossier etatDemande(EtatDemande etatDemande) {
        this.setEtatDemande(etatDemande);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeDossier)) {
            return false;
        }
        return id != null && id.equals(((DemandeDossier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeDossier{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", email='" + getEmail() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", matricule='" + getMatricule() + "'" +
            ", typeDossier='" + getTypeDossier() + "'" +
            ", annee='" + getAnnee() + "'" +
            ", serie='" + getSerie() + "'" +
            ", filiere='" + getFiliere() + "'" +
            ", nomSemestre='" + getNomSemestre() + "'" +
            ", niveau='" + getNiveau() + "'" +
            ", typeReleve='" + getTypeReleve() + "'" +
            ", nomDiplome='" + getNomDiplome() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
