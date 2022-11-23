package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CpuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cpu.class);
        Cpu cpu1 = new Cpu();
        cpu1.setId(1L);
        Cpu cpu2 = new Cpu();
        cpu2.setId(cpu1.getId());
        assertThat(cpu1).isEqualTo(cpu2);
        cpu2.setId(2L);
        assertThat(cpu1).isNotEqualTo(cpu2);
        cpu1.setId(null);
        assertThat(cpu1).isNotEqualTo(cpu2);
    }
}
