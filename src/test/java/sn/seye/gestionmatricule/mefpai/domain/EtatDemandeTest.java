package sn.seye.gestionmatricule.mefpai.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.seye.gestionmatricule.mefpai.web.rest.TestUtil;

class EtatDemandeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatDemande.class);
        EtatDemande etatDemande1 = new EtatDemande();
        etatDemande1.setId(1L);
        EtatDemande etatDemande2 = new EtatDemande();
        etatDemande2.setId(etatDemande1.getId());
        assertThat(etatDemande1).isEqualTo(etatDemande2);
        etatDemande2.setId(2L);
        assertThat(etatDemande1).isNotEqualTo(etatDemande2);
        etatDemande1.setId(null);
        assertThat(etatDemande1).isNotEqualTo(etatDemande2);
    }
}
