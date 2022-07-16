package sn.seye.gestionmatricule.mefpai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Programme.
 */
@Entity
@Table(name = "programme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Programme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_module")
    private String nomModule;

    @Column(name = "coef")
    private Integer coef;

    @Column(name = "note")
    private Float note;

    @OneToMany(mappedBy = "programme")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inscriptions", "etablissement", "programme", "niveauEtude" }, allowSetters = true)
    private Set<Classe> classes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_programme__releve_note",
        joinColumns = @JoinColumn(name = "programme_id"),
        inverseJoinColumns = @JoinColumn(name = "releve_note_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apprenant", "filiereEtude", "serieEtude", "demandeDossier", "programmes" }, allowSetters = true)
    private Set<ReleveNote> releveNotes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Programme id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomModule() {
        return this.nomModule;
    }

    public Programme nomModule(String nomModule) {
        this.setNomModule(nomModule);
        return this;
    }

    public void setNomModule(String nomModule) {
        this.nomModule = nomModule;
    }

    public Integer getCoef() {
        return this.coef;
    }

    public Programme coef(Integer coef) {
        this.setCoef(coef);
        return this;
    }

    public void setCoef(Integer coef) {
        this.coef = coef;
    }

    public Float getNote() {
        return this.note;
    }

    public Programme note(Float note) {
        this.setNote(note);
        return this;
    }

    public void setNote(Float note) {
        this.note = note;
    }

    public Set<Classe> getClasses() {
        return this.classes;
    }

    public void setClasses(Set<Classe> classes) {
        if (this.classes != null) {
            this.classes.forEach(i -> i.setProgramme(null));
        }
        if (classes != null) {
            classes.forEach(i -> i.setProgramme(this));
        }
        this.classes = classes;
    }

    public Programme classes(Set<Classe> classes) {
        this.setClasses(classes);
        return this;
    }

    public Programme addClasse(Classe classe) {
        this.classes.add(classe);
        classe.setProgramme(this);
        return this;
    }

    public Programme removeClasse(Classe classe) {
        this.classes.remove(classe);
        classe.setProgramme(null);
        return this;
    }

    public Set<ReleveNote> getReleveNotes() {
        return this.releveNotes;
    }

    public void setReleveNotes(Set<ReleveNote> releveNotes) {
        this.releveNotes = releveNotes;
    }

    public Programme releveNotes(Set<ReleveNote> releveNotes) {
        this.setReleveNotes(releveNotes);
        return this;
    }

    public Programme addReleveNote(ReleveNote releveNote) {
        this.releveNotes.add(releveNote);
        releveNote.getProgrammes().add(this);
        return this;
    }

    public Programme removeReleveNote(ReleveNote releveNote) {
        this.releveNotes.remove(releveNote);
        releveNote.getProgrammes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Programme)) {
            return false;
        }
        return id != null && id.equals(((Programme) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Programme{" +
            "id=" + getId() +
            ", nomModule='" + getNomModule() + "'" +
            ", coef=" + getCoef() +
            ", note=" + getNote() +
            "}";
    }
}
