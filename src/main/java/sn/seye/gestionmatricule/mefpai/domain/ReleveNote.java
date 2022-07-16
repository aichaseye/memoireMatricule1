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
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Serie;

/**
 * A ReleveNote.
 */
@Entity
@Table(name = "releve_note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReleveNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "annee", nullable = false)
    private LocalDate annee;

    @Column(name = "moyenne")
    private Float moyenne;

    @Enumerated(EnumType.STRING)
    @Column(name = "serie")
    private Serie serie;

    @Enumerated(EnumType.STRING)
    @Column(name = "filiere")
    private Filiere filiere;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau")
    private Niveau niveau;

    @Column(name = "moyenne_generale")
    private Float moyenneGenerale;

    @Column(name = "rang")
    private String rang;

    @Column(name = "note_conduite")
    private Integer noteConduite;

    @NotNull
    @Column(name = "matricule_rel", nullable = false)
    private String matriculeRel;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "etablissements", "releveNotes" }, allowSetters = true)
    private FiliereEtude filiereEtude;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etablissements", "releveNotes" }, allowSetters = true)
    private SerieEtude serieEtude;

    @ManyToOne
    @JsonIgnoreProperties(value = { "apprenants", "bulletins", "releveNotes", "carteScolaires", "etatDemande" }, allowSetters = true)
    private DemandeDossier demandeDossier;

    @ManyToMany(mappedBy = "releveNotes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "classes", "releveNotes" }, allowSetters = true)
    private Set<Programme> programmes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReleveNote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAnnee() {
        return this.annee;
    }

    public ReleveNote annee(LocalDate annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(LocalDate annee) {
        this.annee = annee;
    }

    public Float getMoyenne() {
        return this.moyenne;
    }

    public ReleveNote moyenne(Float moyenne) {
        this.setMoyenne(moyenne);
        return this;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    public Serie getSerie() {
        return this.serie;
    }

    public ReleveNote serie(Serie serie) {
        this.setSerie(serie);
        return this;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Filiere getFiliere() {
        return this.filiere;
    }

    public ReleveNote filiere(Filiere filiere) {
        this.setFiliere(filiere);
        return this;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public Niveau getNiveau() {
        return this.niveau;
    }

    public ReleveNote niveau(Niveau niveau) {
        this.setNiveau(niveau);
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Float getMoyenneGenerale() {
        return this.moyenneGenerale;
    }

    public ReleveNote moyenneGenerale(Float moyenneGenerale) {
        this.setMoyenneGenerale(moyenneGenerale);
        return this;
    }

    public void setMoyenneGenerale(Float moyenneGenerale) {
        this.moyenneGenerale = moyenneGenerale;
    }

    public String getRang() {
        return this.rang;
    }

    public ReleveNote rang(String rang) {
        this.setRang(rang);
        return this;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public Integer getNoteConduite() {
        return this.noteConduite;
    }

    public ReleveNote noteConduite(Integer noteConduite) {
        this.setNoteConduite(noteConduite);
        return this;
    }

    public void setNoteConduite(Integer noteConduite) {
        this.noteConduite = noteConduite;
    }

    public String getMatriculeRel() {
        return this.matriculeRel;
    }

    public ReleveNote matriculeRel(String matriculeRel) {
        this.setMatriculeRel(matriculeRel);
        return this;
    }

    public void setMatriculeRel(String matriculeRel) {
        this.matriculeRel = matriculeRel;
    }

    public Apprenant getApprenant() {
        return this.apprenant;
    }

    public void setApprenant(Apprenant apprenant) {
        this.apprenant = apprenant;
    }

    public ReleveNote apprenant(Apprenant apprenant) {
        this.setApprenant(apprenant);
        return this;
    }

    public FiliereEtude getFiliereEtude() {
        return this.filiereEtude;
    }

    public void setFiliereEtude(FiliereEtude filiereEtude) {
        this.filiereEtude = filiereEtude;
    }

    public ReleveNote filiereEtude(FiliereEtude filiereEtude) {
        this.setFiliereEtude(filiereEtude);
        return this;
    }

    public SerieEtude getSerieEtude() {
        return this.serieEtude;
    }

    public void setSerieEtude(SerieEtude serieEtude) {
        this.serieEtude = serieEtude;
    }

    public ReleveNote serieEtude(SerieEtude serieEtude) {
        this.setSerieEtude(serieEtude);
        return this;
    }

    public DemandeDossier getDemandeDossier() {
        return this.demandeDossier;
    }

    public void setDemandeDossier(DemandeDossier demandeDossier) {
        this.demandeDossier = demandeDossier;
    }

    public ReleveNote demandeDossier(DemandeDossier demandeDossier) {
        this.setDemandeDossier(demandeDossier);
        return this;
    }

    public Set<Programme> getProgrammes() {
        return this.programmes;
    }

    public void setProgrammes(Set<Programme> programmes) {
        if (this.programmes != null) {
            this.programmes.forEach(i -> i.removeReleveNote(this));
        }
        if (programmes != null) {
            programmes.forEach(i -> i.addReleveNote(this));
        }
        this.programmes = programmes;
    }

    public ReleveNote programmes(Set<Programme> programmes) {
        this.setProgrammes(programmes);
        return this;
    }

    public ReleveNote addProgramme(Programme programme) {
        this.programmes.add(programme);
        programme.getReleveNotes().add(this);
        return this;
    }

    public ReleveNote removeProgramme(Programme programme) {
        this.programmes.remove(programme);
        programme.getReleveNotes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReleveNote)) {
            return false;
        }
        return id != null && id.equals(((ReleveNote) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReleveNote{" +
            "id=" + getId() +
            ", annee='" + getAnnee() + "'" +
            ", moyenne=" + getMoyenne() +
            ", serie='" + getSerie() + "'" +
            ", filiere='" + getFiliere() + "'" +
            ", niveau='" + getNiveau() + "'" +
            ", moyenneGenerale=" + getMoyenneGenerale() +
            ", rang='" + getRang() + "'" +
            ", noteConduite=" + getNoteConduite() +
            ", matriculeRel='" + getMatriculeRel() + "'" +
            "}";
    }
}
