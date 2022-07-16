package sn.seye.gestionmatricule.mefpai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.StatutEtab;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.TypeEtab;

/**
 * A Etablissement.
 */
@Entity
@Table(name = "etablissement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom_etab", nullable = false)
    private String nomEtab;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_etab", nullable = false)
    private TypeEtab typeEtab;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutEtab statut;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "email")
    private String email;

    @Column(name = "latitude")
    private Long latitude;

    @Column(name = "longitude")
    private Long longitude;

    @Column(name = "matricule_etab")
    private String matriculeEtab;

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etablissement", "matiere" }, allowSetters = true)
    private Set<Bon> bons = new HashSet<>();

    @OneToMany(mappedBy = "etablissement")
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

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etablissement", "inspection" }, allowSetters = true)
    private Set<Personnel> personnel = new HashSet<>();

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inscriptions", "etablissement", "programme", "niveauEtude" }, allowSetters = true)
    private Set<Classe> classes = new HashSet<>();

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etablissement", "apprenant", "etatDemande" }, allowSetters = true)
    private Set<Diplome> diplomes = new HashSet<>();

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etablissement" }, allowSetters = true)
    private Set<DemandeMatEtab> demandeMatEtabs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "etablissements" }, allowSetters = true)
    private Localite localite;

    @ManyToOne
    @JsonIgnoreProperties(value = { "personnel", "etablissements" }, allowSetters = true)
    private Inspection inspection;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etablissements", "releveNotes" }, allowSetters = true)
    private FiliereEtude filiereEtude;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etablissements", "releveNotes" }, allowSetters = true)
    private SerieEtude serieEtude;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Etablissement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEtab() {
        return this.nomEtab;
    }

    public Etablissement nomEtab(String nomEtab) {
        this.setNomEtab(nomEtab);
        return this;
    }

    public void setNomEtab(String nomEtab) {
        this.nomEtab = nomEtab;
    }

    public TypeEtab getTypeEtab() {
        return this.typeEtab;
    }

    public Etablissement typeEtab(TypeEtab typeEtab) {
        this.setTypeEtab(typeEtab);
        return this;
    }

    public void setTypeEtab(TypeEtab typeEtab) {
        this.typeEtab = typeEtab;
    }

    public StatutEtab getStatut() {
        return this.statut;
    }

    public Etablissement statut(StatutEtab statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutEtab statut) {
        this.statut = statut;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Etablissement adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public Etablissement email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLatitude() {
        return this.latitude;
    }

    public Etablissement latitude(Long latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return this.longitude;
    }

    public Etablissement longitude(Long longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public String getMatriculeEtab() {
        return this.matriculeEtab;
    }

    public Etablissement matriculeEtab(String matriculeEtab) {
        this.setMatriculeEtab(matriculeEtab);
        return this;
    }

    public void setMatriculeEtab(String matriculeEtab) {
        this.matriculeEtab = matriculeEtab;
    }

    public Set<Bon> getBons() {
        return this.bons;
    }

    public void setBons(Set<Bon> bons) {
        if (this.bons != null) {
            this.bons.forEach(i -> i.setEtablissement(null));
        }
        if (bons != null) {
            bons.forEach(i -> i.setEtablissement(this));
        }
        this.bons = bons;
    }

    public Etablissement bons(Set<Bon> bons) {
        this.setBons(bons);
        return this;
    }

    public Etablissement addBon(Bon bon) {
        this.bons.add(bon);
        bon.setEtablissement(this);
        return this;
    }

    public Etablissement removeBon(Bon bon) {
        this.bons.remove(bon);
        bon.setEtablissement(null);
        return this;
    }

    public Set<Apprenant> getApprenants() {
        return this.apprenants;
    }

    public void setApprenants(Set<Apprenant> apprenants) {
        if (this.apprenants != null) {
            this.apprenants.forEach(i -> i.setEtablissement(null));
        }
        if (apprenants != null) {
            apprenants.forEach(i -> i.setEtablissement(this));
        }
        this.apprenants = apprenants;
    }

    public Etablissement apprenants(Set<Apprenant> apprenants) {
        this.setApprenants(apprenants);
        return this;
    }

    public Etablissement addApprenant(Apprenant apprenant) {
        this.apprenants.add(apprenant);
        apprenant.setEtablissement(this);
        return this;
    }

    public Etablissement removeApprenant(Apprenant apprenant) {
        this.apprenants.remove(apprenant);
        apprenant.setEtablissement(null);
        return this;
    }

    public Set<Personnel> getPersonnel() {
        return this.personnel;
    }

    public void setPersonnel(Set<Personnel> personnel) {
        if (this.personnel != null) {
            this.personnel.forEach(i -> i.setEtablissement(null));
        }
        if (personnel != null) {
            personnel.forEach(i -> i.setEtablissement(this));
        }
        this.personnel = personnel;
    }

    public Etablissement personnel(Set<Personnel> personnel) {
        this.setPersonnel(personnel);
        return this;
    }

    public Etablissement addPersonnel(Personnel personnel) {
        this.personnel.add(personnel);
        personnel.setEtablissement(this);
        return this;
    }

    public Etablissement removePersonnel(Personnel personnel) {
        this.personnel.remove(personnel);
        personnel.setEtablissement(null);
        return this;
    }

    public Set<Classe> getClasses() {
        return this.classes;
    }

    public void setClasses(Set<Classe> classes) {
        if (this.classes != null) {
            this.classes.forEach(i -> i.setEtablissement(null));
        }
        if (classes != null) {
            classes.forEach(i -> i.setEtablissement(this));
        }
        this.classes = classes;
    }

    public Etablissement classes(Set<Classe> classes) {
        this.setClasses(classes);
        return this;
    }

    public Etablissement addClasse(Classe classe) {
        this.classes.add(classe);
        classe.setEtablissement(this);
        return this;
    }

    public Etablissement removeClasse(Classe classe) {
        this.classes.remove(classe);
        classe.setEtablissement(null);
        return this;
    }

    public Set<Diplome> getDiplomes() {
        return this.diplomes;
    }

    public void setDiplomes(Set<Diplome> diplomes) {
        if (this.diplomes != null) {
            this.diplomes.forEach(i -> i.setEtablissement(null));
        }
        if (diplomes != null) {
            diplomes.forEach(i -> i.setEtablissement(this));
        }
        this.diplomes = diplomes;
    }

    public Etablissement diplomes(Set<Diplome> diplomes) {
        this.setDiplomes(diplomes);
        return this;
    }

    public Etablissement addDiplome(Diplome diplome) {
        this.diplomes.add(diplome);
        diplome.setEtablissement(this);
        return this;
    }

    public Etablissement removeDiplome(Diplome diplome) {
        this.diplomes.remove(diplome);
        diplome.setEtablissement(null);
        return this;
    }

    public Set<DemandeMatEtab> getDemandeMatEtabs() {
        return this.demandeMatEtabs;
    }

    public void setDemandeMatEtabs(Set<DemandeMatEtab> demandeMatEtabs) {
        if (this.demandeMatEtabs != null) {
            this.demandeMatEtabs.forEach(i -> i.setEtablissement(null));
        }
        if (demandeMatEtabs != null) {
            demandeMatEtabs.forEach(i -> i.setEtablissement(this));
        }
        this.demandeMatEtabs = demandeMatEtabs;
    }

    public Etablissement demandeMatEtabs(Set<DemandeMatEtab> demandeMatEtabs) {
        this.setDemandeMatEtabs(demandeMatEtabs);
        return this;
    }

    public Etablissement addDemandeMatEtab(DemandeMatEtab demandeMatEtab) {
        this.demandeMatEtabs.add(demandeMatEtab);
        demandeMatEtab.setEtablissement(this);
        return this;
    }

    public Etablissement removeDemandeMatEtab(DemandeMatEtab demandeMatEtab) {
        this.demandeMatEtabs.remove(demandeMatEtab);
        demandeMatEtab.setEtablissement(null);
        return this;
    }

    public Localite getLocalite() {
        return this.localite;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }

    public Etablissement localite(Localite localite) {
        this.setLocalite(localite);
        return this;
    }

    public Inspection getInspection() {
        return this.inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

    public Etablissement inspection(Inspection inspection) {
        this.setInspection(inspection);
        return this;
    }

    public FiliereEtude getFiliereEtude() {
        return this.filiereEtude;
    }

    public void setFiliereEtude(FiliereEtude filiereEtude) {
        this.filiereEtude = filiereEtude;
    }

    public Etablissement filiereEtude(FiliereEtude filiereEtude) {
        this.setFiliereEtude(filiereEtude);
        return this;
    }

    public SerieEtude getSerieEtude() {
        return this.serieEtude;
    }

    public void setSerieEtude(SerieEtude serieEtude) {
        this.serieEtude = serieEtude;
    }

    public Etablissement serieEtude(SerieEtude serieEtude) {
        this.setSerieEtude(serieEtude);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etablissement)) {
            return false;
        }
        return id != null && id.equals(((Etablissement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etablissement{" +
            "id=" + getId() +
            ", nomEtab='" + getNomEtab() + "'" +
            ", typeEtab='" + getTypeEtab() + "'" +
            ", statut='" + getStatut() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", email='" + getEmail() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", matriculeEtab='" + getMatriculeEtab() + "'" +
            "}";
    }
}
