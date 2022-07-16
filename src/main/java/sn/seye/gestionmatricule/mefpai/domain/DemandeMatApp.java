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
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Sexe;

/**
 * A DemandeMatApp.
 */
@Entity
@Table(name = "demande_mat_app")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeMatApp implements Serializable {

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
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @Column(name = "matricule_app")
    private String matriculeApp;

    @OneToMany(mappedBy = "demandeMatApp")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeMatApp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public DemandeMatApp nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public DemandeMatApp prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public DemandeMatApp email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public DemandeMatApp telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public DemandeMatApp dateNaissance(LocalDate dateNaissance) {
        this.setDateNaissance(dateNaissance);
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public DemandeMatApp sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getMatriculeApp() {
        return this.matriculeApp;
    }

    public DemandeMatApp matriculeApp(String matriculeApp) {
        this.setMatriculeApp(matriculeApp);
        return this;
    }

    public void setMatriculeApp(String matriculeApp) {
        this.matriculeApp = matriculeApp;
    }

    public Set<Apprenant> getApprenants() {
        return this.apprenants;
    }

    public void setApprenants(Set<Apprenant> apprenants) {
        if (this.apprenants != null) {
            this.apprenants.forEach(i -> i.setDemandeMatApp(null));
        }
        if (apprenants != null) {
            apprenants.forEach(i -> i.setDemandeMatApp(this));
        }
        this.apprenants = apprenants;
    }

    public DemandeMatApp apprenants(Set<Apprenant> apprenants) {
        this.setApprenants(apprenants);
        return this;
    }

    public DemandeMatApp addApprenant(Apprenant apprenant) {
        this.apprenants.add(apprenant);
        apprenant.setDemandeMatApp(this);
        return this;
    }

    public DemandeMatApp removeApprenant(Apprenant apprenant) {
        this.apprenants.remove(apprenant);
        apprenant.setDemandeMatApp(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeMatApp)) {
            return false;
        }
        return id != null && id.equals(((DemandeMatApp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeMatApp{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", matriculeApp='" + getMatriculeApp() + "'" +
            "}";
    }
}
