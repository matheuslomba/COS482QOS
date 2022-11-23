package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PowerSourceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerSource.class);
        PowerSource powerSource1 = new PowerSource();
        powerSource1.setId(1L);
        PowerSource powerSource2 = new PowerSource();
        powerSource2.setId(powerSource1.getId());
        assertThat(powerSource1).isEqualTo(powerSource2);
        powerSource2.setId(2L);
        assertThat(powerSource1).isNotEqualTo(powerSource2);
        powerSource1.setId(null);
        assertThat(powerSource1).isNotEqualTo(powerSource2);
    }
}
