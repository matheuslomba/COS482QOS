package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GpuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gpu.class);
        Gpu gpu1 = new Gpu();
        gpu1.setId(1L);
        Gpu gpu2 = new Gpu();
        gpu2.setId(gpu1.getId());
        assertThat(gpu1).isEqualTo(gpu2);
        gpu2.setId(2L);
        assertThat(gpu1).isNotEqualTo(gpu2);
        gpu1.setId(null);
        assertThat(gpu1).isNotEqualTo(gpu2);
    }
}
