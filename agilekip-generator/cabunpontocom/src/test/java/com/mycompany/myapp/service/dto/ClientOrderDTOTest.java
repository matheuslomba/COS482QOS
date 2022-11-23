package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientOrderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientOrderDTO.class);
        ClientOrderDTO clientOrderDTO1 = new ClientOrderDTO();
        clientOrderDTO1.setId(1L);
        ClientOrderDTO clientOrderDTO2 = new ClientOrderDTO();
        assertThat(clientOrderDTO1).isNotEqualTo(clientOrderDTO2);
        clientOrderDTO2.setId(clientOrderDTO1.getId());
        assertThat(clientOrderDTO1).isEqualTo(clientOrderDTO2);
        clientOrderDTO2.setId(2L);
        assertThat(clientOrderDTO1).isNotEqualTo(clientOrderDTO2);
        clientOrderDTO1.setId(null);
        assertThat(clientOrderDTO1).isNotEqualTo(clientOrderDTO2);
    }
}
