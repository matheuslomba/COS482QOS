package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hd.class);
        Hd hd1 = new Hd();
        hd1.setId(1L);
        Hd hd2 = new Hd();
        hd2.setId(hd1.getId());
        assertThat(hd1).isEqualTo(hd2);
        hd2.setId(2L);
        assertThat(hd1).isNotEqualTo(hd2);
        hd1.setId(null);
        assertThat(hd1).isNotEqualTo(hd2);
    }
}
