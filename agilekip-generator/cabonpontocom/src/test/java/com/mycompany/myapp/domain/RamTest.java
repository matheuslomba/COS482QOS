package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ram.class);
        Ram ram1 = new Ram();
        ram1.setId(1L);
        Ram ram2 = new Ram();
        ram2.setId(ram1.getId());
        assertThat(ram1).isEqualTo(ram2);
        ram2.setId(2L);
        assertThat(ram1).isNotEqualTo(ram2);
        ram1.setId(null);
        assertThat(ram1).isNotEqualTo(ram2);
    }
}
