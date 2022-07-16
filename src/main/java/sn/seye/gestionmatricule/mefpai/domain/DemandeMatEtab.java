package sn.seye.gestionmatricule.mefpai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.NomDep;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Responsabilite;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.StatutEtab;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.TypeEtab;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.TypeInspection;

/**
 * A DemandeMatEtab.
 */
@Entity
@Table(name = "demande_mat_etab")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeMatEtab implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "responsabilite", nullable = false)
    private Responsabilite responsabilite;

    @NotNull
    @Column(name = "nom_etab", nullable = false)
    private String nomEtab;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "departement", nullable = false)
    private NomDep departement;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_etab", nullable = false)
    private TypeEtab typeEtab;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutEtab statut;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_insp", nullable = false)
    private TypeInspection typeInsp;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "matricule_etab")
    private String matriculeEtab;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeMatEtab id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public DemandeMatEtab nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public DemandeMatEtab prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Responsabilite getResponsabilite() {
        return this.responsabilite;
    }

    public DemandeMatEtab responsabilite(Responsabilite responsabilite) {
        this.setResponsabilite(responsabilite);
        return this;
    }

    public void setResponsabilite(Responsabilite responsabilite) {
        this.responsabilite = responsabilite;
    }

    public String getNomEtab() {
        return this.nomEtab;
    }

    public DemandeMatEtab nomEtab(String nomEtab) {
        this.setNomEtab(nomEtab);
        return this;
    }

    public void setNomEtab(String nomEtab) {
        this.nomEtab = nomEtab;
    }

    public NomDep getDepartement() {
        return this.departement;
    }

    public DemandeMatEtab departement(NomDep departement) {
        this.setDepartement(departement);
        return this;
    }

    public void setDepartement(NomDep departement) {
        this.departement = departement;
    }

    public TypeEtab getTypeEtab() {
        return this.typeEtab;
    }

    public DemandeMatEtab typeEtab(TypeEtab typeEtab) {
        this.setTypeEtab(typeEtab);
        return this;
    }

    public void setTypeEtab(TypeEtab typeEtab) {
        this.typeEtab = typeEtab;
    }

    public StatutEtab getStatut() {
        return this.statut;
    }

    public DemandeMatEtab statut(StatutEtab statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutEtab statut) {
        this.statut = statut;
    }

    public TypeInspection getTypeInsp() {
        return this.typeInsp;
    }

    public DemandeMatEtab typeInsp(TypeInspection typeInsp) {
        this.setTypeInsp(typeInsp);
        return this;
    }

    public void setTypeInsp(TypeInspection typeInsp) {
        this.typeInsp = typeInsp;
    }

    public String getEmail() {
        return this.email;
    }

    public DemandeMatEtab email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatriculeEtab() {
        return this.matriculeEtab;
    }

    public DemandeMatEtab matriculeEtab(String matriculeEtab) {
        this.setMatriculeEtab(matriculeEtab);
        return this;
    }

    public void setMatriculeEtab(String matriculeEtab) {
        this.matriculeEtab = matriculeEtab;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public DemandeMatEtab etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeMatEtab)) {
            return false;
        }
        return id != null && id.equals(((DemandeMatEtab) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeMatEtab{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", responsabilite='" + getResponsabilite() + "'" +
            ", nomEtab='" + getNomEtab() + "'" +
            ", departement='" + getDepartement() + "'" +
            ", typeEtab='" + getTypeEtab() + "'" +
            ", statut='" + getStatut() + "'" +
            ", typeInsp='" + getTypeInsp() + "'" +
            ", email='" + getEmail() + "'" +
            ", matriculeEtab='" + getMatriculeEtab() + "'" +
            "}";
    }
}
