package com.kacetal.typoreporter.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kacetal.typoreporter.web.rest.TestUtil;

public class TypoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypoDTO.class);
        TypoDTO typoDTO1 = new TypoDTO();
        typoDTO1.setId(1L);
        TypoDTO typoDTO2 = new TypoDTO();
        assertThat(typoDTO1).isNotEqualTo(typoDTO2);
        typoDTO2.setId(typoDTO1.getId());
        assertThat(typoDTO1).isEqualTo(typoDTO2);
        typoDTO2.setId(2L);
        assertThat(typoDTO1).isNotEqualTo(typoDTO2);
        typoDTO1.setId(null);
        assertThat(typoDTO1).isNotEqualTo(typoDTO2);
    }
}
