package sn.seye.gestionmatricule.mefpai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Status;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.TypeDossier;

/**
 * A EtatDemande.
 */
@Entity
@Table(name = "etat_demande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EtatDemande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_dossier", nullable = false)
    private TypeDossier typeDossier;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "etatDemande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apprenants", "bulletins", "releveNotes", "carteScolaires", "etatDemande" }, allowSetters = true)
    private Set<DemandeDossier> demandeDossiers = new HashSet<>();

    @OneToMany(mappedBy = "etatDemande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etablissement", "apprenant", "etatDemande" }, allowSetters = true)
    private Set<Diplome> diplomes = new HashSet<>();

    @OneToMany(mappedBy = "etatDemande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etatDemande" }, allowSetters = true)
    private Set<Attestation> attestations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EtatDemande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public EtatDemande matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public TypeDossier getTypeDossier() {
        return this.typeDossier;
    }

    public EtatDemande typeDossier(TypeDossier typeDossier) {
        this.setTypeDossier(typeDossier);
        return this;
    }

    public void setTypeDossier(TypeDossier typeDossier) {
        this.typeDossier = typeDossier;
    }

    public Status getStatus() {
        return this.status;
    }

    public EtatDemande status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<DemandeDossier> getDemandeDossiers() {
        return this.demandeDossiers;
    }

    public void setDemandeDossiers(Set<DemandeDossier> demandeDossiers) {
        if (this.demandeDossiers != null) {
            this.demandeDossiers.forEach(i -> i.setEtatDemande(null));
        }
        if (demandeDossiers != null) {
            demandeDossiers.forEach(i -> i.setEtatDemande(this));
        }
        this.demandeDossiers = demandeDossiers;
    }

    public EtatDemande demandeDossiers(Set<DemandeDossier> demandeDossiers) {
        this.setDemandeDossiers(demandeDossiers);
        return this;
    }

    public EtatDemande addDemandeDossier(DemandeDossier demandeDossier) {
        this.demandeDossiers.add(demandeDossier);
        demandeDossier.setEtatDemande(this);
        return this;
    }

    public EtatDemande removeDemandeDossier(DemandeDossier demandeDossier) {
        this.demandeDossiers.remove(demandeDossier);
        demandeDossier.setEtatDemande(null);
        return this;
    }

    public Set<Diplome> getDiplomes() {
        return this.diplomes;
    }

    public void setDiplomes(Set<Diplome> diplomes) {
        if (this.diplomes != null) {
            this.diplomes.forEach(i -> i.setEtatDemande(null));
        }
        if (diplomes != null) {
            diplomes.forEach(i -> i.setEtatDemande(this));
        }
        this.diplomes = diplomes;
    }

    public EtatDemande diplomes(Set<Diplome> diplomes) {
        this.setDiplomes(diplomes);
        return this;
    }

    public EtatDemande addDiplome(Diplome diplome) {
        this.diplomes.add(diplome);
        diplome.setEtatDemande(this);
        return this;
    }

    public EtatDemande removeDiplome(Diplome diplome) {
        this.diplomes.remove(diplome);
        diplome.setEtatDemande(null);
        return this;
    }

    public Set<Attestation> getAttestations() {
        return this.attestations;
    }

    public void setAttestations(Set<Attestation> attestations) {
        if (this.attestations != null) {
            this.attestations.forEach(i -> i.setEtatDemande(null));
        }
        if (attestations != null) {
            attestations.forEach(i -> i.setEtatDemande(this));
        }
        this.attestations = attestations;
    }

    public EtatDemande attestations(Set<Attestation> attestations) {
        this.setAttestations(attestations);
        return this;
    }

    public EtatDemande addAttestation(Attestation attestation) {
        this.attestations.add(attestation);
        attestation.setEtatDemande(this);
        return this;
    }

    public EtatDemande removeAttestation(Attestation attestation) {
        this.attestations.remove(attestation);
        attestation.setEtatDemande(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtatDemande)) {
            return false;
        }
        return id != null && id.equals(((EtatDemande) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtatDemande{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", typeDossier='" + getTypeDossier() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
