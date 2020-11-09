package com.kacetal.typoreporter.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kacetal.typoreporter.web.rest.TestUtil;

public class TypoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Typo.class);
        Typo typo1 = new Typo();
        typo1.setId(1L);
        Typo typo2 = new Typo();
        typo2.setId(typo1.getId());
        assertThat(typo1).isEqualTo(typo2);
        typo2.setId(2L);
        assertThat(typo1).isNotEqualTo(typo2);
        typo1.setId(null);
        assertThat(typo1).isNotEqualTo(typo2);
    }
}
