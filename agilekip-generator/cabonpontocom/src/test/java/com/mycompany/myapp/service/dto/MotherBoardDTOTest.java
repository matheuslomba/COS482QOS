package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MotherBoardDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotherBoardDTO.class);
        MotherBoardDTO motherBoardDTO1 = new MotherBoardDTO();
        motherBoardDTO1.setId(1L);
        MotherBoardDTO motherBoardDTO2 = new MotherBoardDTO();
        assertThat(motherBoardDTO1).isNotEqualTo(motherBoardDTO2);
        motherBoardDTO2.setId(motherBoardDTO1.getId());
        assertThat(motherBoardDTO1).isEqualTo(motherBoardDTO2);
        motherBoardDTO2.setId(2L);
        assertThat(motherBoardDTO1).isNotEqualTo(motherBoardDTO2);
        motherBoardDTO1.setId(null);
        assertThat(motherBoardDTO1).isNotEqualTo(motherBoardDTO2);
    }
}
