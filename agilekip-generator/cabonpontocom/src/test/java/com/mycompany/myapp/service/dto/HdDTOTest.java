package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HdDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HdDTO.class);
        HdDTO hdDTO1 = new HdDTO();
        hdDTO1.setId(1L);
        HdDTO hdDTO2 = new HdDTO();
        assertThat(hdDTO1).isNotEqualTo(hdDTO2);
        hdDTO2.setId(hdDTO1.getId());
        assertThat(hdDTO1).isEqualTo(hdDTO2);
        hdDTO2.setId(2L);
        assertThat(hdDTO1).isNotEqualTo(hdDTO2);
        hdDTO1.setId(null);
        assertThat(hdDTO1).isNotEqualTo(hdDTO2);
    }
}
