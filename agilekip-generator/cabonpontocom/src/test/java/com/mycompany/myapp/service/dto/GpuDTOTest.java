package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GpuDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GpuDTO.class);
        GpuDTO gpuDTO1 = new GpuDTO();
        gpuDTO1.setId(1L);
        GpuDTO gpuDTO2 = new GpuDTO();
        assertThat(gpuDTO1).isNotEqualTo(gpuDTO2);
        gpuDTO2.setId(gpuDTO1.getId());
        assertThat(gpuDTO1).isEqualTo(gpuDTO2);
        gpuDTO2.setId(2L);
        assertThat(gpuDTO1).isNotEqualTo(gpuDTO2);
        gpuDTO1.setId(null);
        assertThat(gpuDTO1).isNotEqualTo(gpuDTO2);
    }
}
