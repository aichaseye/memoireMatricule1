package sn.seye.gestionmatricule.mefpai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Apte;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Asuduite;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Ponctualite;

/**
 * A Observation.
 */
@Entity
@Table(name = "observation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Observation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "asuduite")
    private Asuduite asuduite;

    @Enumerated(EnumType.STRING)
    @Column(name = "ponctualite")
    private Ponctualite ponctualite;

    @Enumerated(EnumType.STRING)
    @Column(name = "apte")
    private Apte apte;

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

    public Observation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asuduite getAsuduite() {
        return this.asuduite;
    }

    public Observation asuduite(Asuduite asuduite) {
        this.setAsuduite(asuduite);
        return this;
    }

    public void setAsuduite(Asuduite asuduite) {
        this.asuduite = asuduite;
    }

    public Ponctualite getPonctualite() {
        return this.ponctualite;
    }

    public Observation ponctualite(Ponctualite ponctualite) {
        this.setPonctualite(ponctualite);
        return this;
    }

    public void setPonctualite(Ponctualite ponctualite) {
        this.ponctualite = ponctualite;
    }

    public Apte getApte() {
        return this.apte;
    }

    public Observation apte(Apte apte) {
        this.setApte(apte);
        return this;
    }

    public void setApte(Apte apte) {
        this.apte = apte;
    }

    public Apprenant getApprenant() {
        return this.apprenant;
    }

    public void setApprenant(Apprenant apprenant) {
        this.apprenant = apprenant;
    }

    public Observation apprenant(Apprenant apprenant) {
        this.setApprenant(apprenant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Observation)) {
            return false;
        }
        return id != null && id.equals(((Observation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Observation{" +
            "id=" + getId() +
            ", asuduite='" + getAsuduite() + "'" +
            ", ponctualite='" + getPonctualite() + "'" +
            ", apte='" + getApte() + "'" +
            "}";
    }
}
