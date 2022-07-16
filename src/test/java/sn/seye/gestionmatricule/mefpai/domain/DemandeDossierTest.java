package sn.seye.gestionmatricule.mefpai.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.seye.gestionmatricule.mefpai.web.rest.TestUtil;

class DemandeDossierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeDossier.class);
        DemandeDossier demandeDossier1 = new DemandeDossier();
        demandeDossier1.setId(1L);
        DemandeDossier demandeDossier2 = new DemandeDossier();
        demandeDossier2.setId(demandeDossier1.getId());
        assertThat(demandeDossier1).isEqualTo(demandeDossier2);
        demandeDossier2.setId(2L);
        assertThat(demandeDossier1).isNotEqualTo(demandeDossier2);
        demandeDossier1.setId(null);
        assertThat(demandeDossier1).isNotEqualTo(demandeDossier2);
    }
}
