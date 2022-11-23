package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PowerSourceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerSourceDTO.class);
        PowerSourceDTO powerSourceDTO1 = new PowerSourceDTO();
        powerSourceDTO1.setId(1L);
        PowerSourceDTO powerSourceDTO2 = new PowerSourceDTO();
        assertThat(powerSourceDTO1).isNotEqualTo(powerSourceDTO2);
        powerSourceDTO2.setId(powerSourceDTO1.getId());
        assertThat(powerSourceDTO1).isEqualTo(powerSourceDTO2);
        powerSourceDTO2.setId(2L);
        assertThat(powerSourceDTO1).isNotEqualTo(powerSourceDTO2);
        powerSourceDTO1.setId(null);
        assertThat(powerSourceDTO1).isNotEqualTo(powerSourceDTO2);
    }
}
