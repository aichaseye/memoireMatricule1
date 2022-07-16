package sn.seye.gestionmatricule.mefpai.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Filiere;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Niveau;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.NomSemestre;
import sn.seye.gestionmatricule.mefpai.domain.enumeration.Serie;

/**
 * A Bulletin.
 */
@Entity
@Table(name = "bulletin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bulletin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nomsemestre")
    private NomSemestre nomsemestre;

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

    @Column(name = "matricule")
    private String matricule;

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
    @JsonIgnoreProperties(value = { "apprenants", "bulletins", "releveNotes", "carteScolaires", "etatDemande" }, allowSetters = true)
    private DemandeDossier demandeDossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bulletin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NomSemestre getNomsemestre() {
        return this.nomsemestre;
    }

    public Bulletin nomsemestre(NomSemestre nomsemestre) {
        this.setNomsemestre(nomsemestre);
        return this;
    }

    public void setNomsemestre(NomSemestre nomsemestre) {
        this.nomsemestre = nomsemestre;
    }

    public LocalDate getAnnee() {
        return this.annee;
    }

    public Bulletin annee(LocalDate annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(LocalDate annee) {
        this.annee = annee;
    }

    public Float getMoyenne() {
        return this.moyenne;
    }

    public Bulletin moyenne(Float moyenne) {
        this.setMoyenne(moyenne);
        return this;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    public Serie getSerie() {
        return this.serie;
    }

    public Bulletin serie(Serie serie) {
        this.setSerie(serie);
        return this;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Filiere getFiliere() {
        return this.filiere;
    }

    public Bulletin filiere(Filiere filiere) {
        this.setFiliere(filiere);
        return this;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public Niveau getNiveau() {
        return this.niveau;
    }

    public Bulletin niveau(Niveau niveau) {
        this.setNiveau(niveau);
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Float getMoyenneGenerale() {
        return this.moyenneGenerale;
    }

    public Bulletin moyenneGenerale(Float moyenneGenerale) {
        this.setMoyenneGenerale(moyenneGenerale);
        return this;
    }

    public void setMoyenneGenerale(Float moyenneGenerale) {
        this.moyenneGenerale = moyenneGenerale;
    }

    public String getRang() {
        return this.rang;
    }

    public Bulletin rang(String rang) {
        this.setRang(rang);
        return this;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public Integer getNoteConduite() {
        return this.noteConduite;
    }

    public Bulletin noteConduite(Integer noteConduite) {
        this.setNoteConduite(noteConduite);
        return this;
    }

    public void setNoteConduite(Integer noteConduite) {
        this.noteConduite = noteConduite;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Bulletin matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Apprenant getApprenant() {
        return this.apprenant;
    }

    public void setApprenant(Apprenant apprenant) {
        this.apprenant = apprenant;
    }

    public Bulletin apprenant(Apprenant apprenant) {
        this.setApprenant(apprenant);
        return this;
    }

    public DemandeDossier getDemandeDossier() {
        return this.demandeDossier;
    }

    public void setDemandeDossier(DemandeDossier demandeDossier) {
        this.demandeDossier = demandeDossier;
    }

    public Bulletin demandeDossier(DemandeDossier demandeDossier) {
        this.setDemandeDossier(demandeDossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bulletin)) {
            return false;
        }
        return id != null && id.equals(((Bulletin) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bulletin{" +
            "id=" + getId() +
            ", nomsemestre='" + getNomsemestre() + "'" +
            ", annee='" + getAnnee() + "'" +
            ", moyenne=" + getMoyenne() +
            ", serie='" + getSerie() + "'" +
            ", filiere='" + getFiliere() + "'" +
            ", niveau='" + getNiveau() + "'" +
            ", moyenneGenerale=" + getMoyenneGenerale() +
            ", rang='" + getRang() + "'" +
            ", noteConduite=" + getNoteConduite() +
            ", matricule='" + getMatricule() + "'" +
            "}";
    }
}
