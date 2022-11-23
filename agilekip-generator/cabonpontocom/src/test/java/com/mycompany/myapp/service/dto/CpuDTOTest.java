package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CpuDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CpuDTO.class);
        CpuDTO cpuDTO1 = new CpuDTO();
        cpuDTO1.setId(1L);
        CpuDTO cpuDTO2 = new CpuDTO();
        assertThat(cpuDTO1).isNotEqualTo(cpuDTO2);
        cpuDTO2.setId(cpuDTO1.getId());
        assertThat(cpuDTO1).isEqualTo(cpuDTO2);
        cpuDTO2.setId(2L);
        assertThat(cpuDTO1).isNotEqualTo(cpuDTO2);
        cpuDTO1.setId(null);
        assertThat(cpuDTO1).isNotEqualTo(cpuDTO2);
    }
}
