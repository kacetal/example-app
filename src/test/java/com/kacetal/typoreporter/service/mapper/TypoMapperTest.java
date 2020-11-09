package com.kacetal.typoreporter.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypoMapperTest {

    private TypoMapper typoMapper;

    @BeforeEach
    public void setUp() {
        typoMapper = new TypoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typoMapper.fromId(null)).isNull();
    }
}
