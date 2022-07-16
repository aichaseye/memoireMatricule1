package sn.seye.gestionmatricule.mefpai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Serie;

/**
 * A SerieEtude.
 */
@Entity
@Table(name = "serie_etude")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SerieEtude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "serie")
    private Serie serie;

    @OneToMany(mappedBy = "serieEtude")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Etablissement> etablissements = new HashSet<>();

    @OneToMany(mappedBy = "serieEtude")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apprenant", "filiereEtude", "serieEtude", "demandeDossier", "programmes" }, allowSetters = true)
    private Set<ReleveNote> releveNotes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SerieEtude id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return this.serie;
    }

    public SerieEtude serie(Serie serie) {
        this.setSerie(serie);
        return this;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Set<Etablissement> getEtablissements() {
        return this.etablissements;
    }

    public void setEtablissements(Set<Etablissement> etablissements) {
        if (this.etablissements != null) {
            this.etablissements.forEach(i -> i.setSerieEtude(null));
        }
        if (etablissements != null) {
            etablissements.forEach(i -> i.setSerieEtude(this));
        }
        this.etablissements = etablissements;
    }

    public SerieEtude etablissements(Set<Etablissement> etablissements) {
        this.setEtablissements(etablissements);
        return this;
    }

    public SerieEtude addEtablissement(Etablissement etablissement) {
        this.etablissements.add(etablissement);
        etablissement.setSerieEtude(this);
        return this;
    }

    public SerieEtude removeEtablissement(Etablissement etablissement) {
        this.etablissements.remove(etablissement);
        etablissement.setSerieEtude(null);
        return this;
    }

    public Set<ReleveNote> getReleveNotes() {
        return this.releveNotes;
    }

    public void setReleveNotes(Set<ReleveNote> releveNotes) {
        if (this.releveNotes != null) {
            this.releveNotes.forEach(i -> i.setSerieEtude(null));
        }
        if (releveNotes != null) {
            releveNotes.forEach(i -> i.setSerieEtude(this));
        }
        this.releveNotes = releveNotes;
    }

    public SerieEtude releveNotes(Set<ReleveNote> releveNotes) {
        this.setReleveNotes(releveNotes);
        return this;
    }

    public SerieEtude addReleveNote(ReleveNote releveNote) {
        this.releveNotes.add(releveNote);
        releveNote.setSerieEtude(this);
        return this;
    }

    public SerieEtude removeReleveNote(ReleveNote releveNote) {
        this.releveNotes.remove(releveNote);
        releveNote.setSerieEtude(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SerieEtude)) {
            return false;
        }
        return id != null && id.equals(((SerieEtude) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SerieEtude{" +
            "id=" + getId() +
            ", serie='" + getSerie() + "'" +
            "}";
    }
}
