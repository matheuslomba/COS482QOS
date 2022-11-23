package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RamDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RamDTO.class);
        RamDTO ramDTO1 = new RamDTO();
        ramDTO1.setId(1L);
        RamDTO ramDTO2 = new RamDTO();
        assertThat(ramDTO1).isNotEqualTo(ramDTO2);
        ramDTO2.setId(ramDTO1.getId());
        assertThat(ramDTO1).isEqualTo(ramDTO2);
        ramDTO2.setId(2L);
        assertThat(ramDTO1).isNotEqualTo(ramDTO2);
        ramDTO1.setId(null);
        assertThat(ramDTO1).isNotEqualTo(ramDTO2);
    }
}
