import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'localite',
        data: { pageTitle: 'dbGestionMatricule5App.localite.home.title' },
        loadChildren: () => import('./localite/localite.module').then(m => m.LocaliteModule),
      },
      {
        path: 'inspection',
        data: { pageTitle: 'dbGestionMatricule5App.inspection.home.title' },
        loadChildren: () => import('./inspection/inspection.module').then(m => m.InspectionModule),
      },
      {
        path: 'etablissement',
        data: { pageTitle: 'dbGestionMatricule5App.etablissement.home.title' },
        loadChildren: () => import('./etablissement/etablissement.module').then(m => m.EtablissementModule),
      },
      {
        path: 'apprenant',
        data: { pageTitle: 'dbGestionMatricule5App.apprenant.home.title' },
        loadChildren: () => import('./apprenant/apprenant.module').then(m => m.ApprenantModule),
      },
      {
        path: 'carte-scolaire',
        data: { pageTitle: 'dbGestionMatricule5App.carteScolaire.home.title' },
        loadChildren: () => import('./carte-scolaire/carte-scolaire.module').then(m => m.CarteScolaireModule),
      },
      {
        path: 'diplome',
        data: { pageTitle: 'dbGestionMatricule5App.diplome.home.title' },
        loadChildren: () => import('./diplome/diplome.module').then(m => m.DiplomeModule),
      },
      {
        path: 'attestation',
        data: { pageTitle: 'dbGestionMatricule5App.attestation.home.title' },
        loadChildren: () => import('./attestation/attestation.module').then(m => m.AttestationModule),
      },
      {
        path: 'inscription',
        data: { pageTitle: 'dbGestionMatricule5App.inscription.home.title' },
        loadChildren: () => import('./inscription/inscription.module').then(m => m.InscriptionModule),
      },
      {
        path: 'personnel',
        data: { pageTitle: 'dbGestionMatricule5App.personnel.home.title' },
        loadChildren: () => import('./personnel/personnel.module').then(m => m.PersonnelModule),
      },
      {
        path: 'classe',
        data: { pageTitle: 'dbGestionMatricule5App.classe.home.title' },
        loadChildren: () => import('./classe/classe.module').then(m => m.ClasseModule),
      },
      {
        path: 'etat-demande',
        data: { pageTitle: 'dbGestionMatricule5App.etatDemande.home.title' },
        loadChildren: () => import('./etat-demande/etat-demande.module').then(m => m.EtatDemandeModule),
      },
      {
        path: 'serie-etude',
        data: { pageTitle: 'dbGestionMatricule5App.serieEtude.home.title' },
        loadChildren: () => import('./serie-etude/serie-etude.module').then(m => m.SerieEtudeModule),
      },
      {
        path: 'filiere-etude',
        data: { pageTitle: 'dbGestionMatricule5App.filiereEtude.home.title' },
        loadChildren: () => import('./filiere-etude/filiere-etude.module').then(m => m.FiliereEtudeModule),
      },
      {
        path: 'niveau-etude',
        data: { pageTitle: 'dbGestionMatricule5App.niveauEtude.home.title' },
        loadChildren: () => import('./niveau-etude/niveau-etude.module').then(m => m.NiveauEtudeModule),
      },
      {
        path: 'releve-note',
        data: { pageTitle: 'dbGestionMatricule5App.releveNote.home.title' },
        loadChildren: () => import('./releve-note/releve-note.module').then(m => m.ReleveNoteModule),
      },
      {
        path: 'bulletin',
        data: { pageTitle: 'dbGestionMatricule5App.bulletin.home.title' },
        loadChildren: () => import('./bulletin/bulletin.module').then(m => m.BulletinModule),
      },
      {
        path: 'programme',
        data: { pageTitle: 'dbGestionMatricule5App.programme.home.title' },
        loadChildren: () => import('./programme/programme.module').then(m => m.ProgrammeModule),
      },
      {
        path: 'demande-mat-app',
        data: { pageTitle: 'dbGestionMatricule5App.demandeMatApp.home.title' },
        loadChildren: () => import('./demande-mat-app/demande-mat-app.module').then(m => m.DemandeMatAppModule),
      },
      {
        path: 'demande-mat-etab',
        data: { pageTitle: 'dbGestionMatricule5App.demandeMatEtab.home.title' },
        loadChildren: () => import('./demande-mat-etab/demande-mat-etab.module').then(m => m.DemandeMatEtabModule),
      },
      {
        path: 'demande-dossier',
        data: { pageTitle: 'dbGestionMatricule5App.demandeDossier.home.title' },
        loadChildren: () => import('./demande-dossier/demande-dossier.module').then(m => m.DemandeDossierModule),
      },
      {
        path: 'matiere',
        data: { pageTitle: 'dbGestionMatricule5App.matiere.home.title' },
        loadChildren: () => import('./matiere/matiere.module').then(m => m.MatiereModule),
      },
      {
        path: 'image',
        data: { pageTitle: 'dbGestionMatricule5App.image.home.title' },
        loadChildren: () => import('./image/image.module').then(m => m.ImageModule),
      },
      {
        path: 'bon',
        data: { pageTitle: 'dbGestionMatricule5App.bon.home.title' },
        loadChildren: () => import('./bon/bon.module').then(m => m.BonModule),
      },
      {
        path: 'observation',
        data: { pageTitle: 'dbGestionMatricule5App.observation.home.title' },
        loadChildren: () => import('./observation/observation.module').then(m => m.ObservationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
