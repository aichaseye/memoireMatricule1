package sn.seye.gestionmatricule.mefpai.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, sn.seye.gestionmatricule.mefpai.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, sn.seye.gestionmatricule.mefpai.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.User.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Authority.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.User.class.getName() + ".authorities");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Localite.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Localite.class.getName() + ".etablissements");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Inspection.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Inspection.class.getName() + ".personnel");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Inspection.class.getName() + ".etablissements");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Etablissement.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Etablissement.class.getName() + ".bons");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Etablissement.class.getName() + ".apprenants");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Etablissement.class.getName() + ".personnel");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Etablissement.class.getName() + ".classes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Etablissement.class.getName() + ".diplomes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Etablissement.class.getName() + ".demandeMatEtabs");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Apprenant.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Apprenant.class.getName() + ".inscriptions");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Apprenant.class.getName() + ".releveNotes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Apprenant.class.getName() + ".bulletins");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Apprenant.class.getName() + ".diplomes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Apprenant.class.getName() + ".observations");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Apprenant.class.getName() + ".carteScolaires");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.CarteScolaire.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Diplome.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Attestation.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Inscription.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Personnel.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Classe.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Classe.class.getName() + ".inscriptions");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.EtatDemande.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.EtatDemande.class.getName() + ".demandeDossiers");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.EtatDemande.class.getName() + ".diplomes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.EtatDemande.class.getName() + ".attestations");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.SerieEtude.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.SerieEtude.class.getName() + ".etablissements");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.SerieEtude.class.getName() + ".releveNotes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.FiliereEtude.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.FiliereEtude.class.getName() + ".etablissements");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.FiliereEtude.class.getName() + ".releveNotes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.NiveauEtude.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.NiveauEtude.class.getName() + ".classes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.NiveauEtude.class.getName() + ".apprenants");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.ReleveNote.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.ReleveNote.class.getName() + ".programmes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Bulletin.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Programme.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Programme.class.getName() + ".classes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Programme.class.getName() + ".releveNotes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.DemandeMatApp.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.DemandeMatApp.class.getName() + ".apprenants");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.DemandeMatEtab.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.DemandeDossier.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.DemandeDossier.class.getName() + ".apprenants");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.DemandeDossier.class.getName() + ".bulletins");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.DemandeDossier.class.getName() + ".releveNotes");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.DemandeDossier.class.getName() + ".carteScolaires");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Matiere.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Matiere.class.getName() + ".bons");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Matiere.class.getName() + ".images");
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Image.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Bon.class.getName());
            createCache(cm, sn.seye.gestionmatricule.mefpai.domain.Observation.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
