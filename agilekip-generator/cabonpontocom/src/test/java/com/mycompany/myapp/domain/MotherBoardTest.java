package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MotherBoardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotherBoard.class);
        MotherBoard motherBoard1 = new MotherBoard();
        motherBoard1.setId(1L);
        MotherBoard motherBoard2 = new MotherBoard();
        motherBoard2.setId(motherBoard1.getId());
        assertThat(motherBoard1).isEqualTo(motherBoard2);
        motherBoard2.setId(2L);
        assertThat(motherBoard1).isNotEqualTo(motherBoard2);
        motherBoard1.setId(null);
        assertThat(motherBoard1).isNotEqualTo(motherBoard2);
    }
}
