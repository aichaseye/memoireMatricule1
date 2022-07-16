package sn.seye.gestionmatricule.mefpai.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.seye.gestionmatricule.mefpai.web.rest.TestUtil;

class AttestationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attestation.class);
        Attestation attestation1 = new Attestation();
        attestation1.setId(1L);
        Attestation attestation2 = new Attestation();
        attestation2.setId(attestation1.getId());
        assertThat(attestation1).isEqualTo(attestation2);
        attestation2.setId(2L);
        assertThat(attestation1).isNotEqualTo(attestation2);
        attestation1.setId(null);
        assertThat(attestation1).isNotEqualTo(attestation2);
    }
}
