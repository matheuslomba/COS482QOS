package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientOrder.class);
        ClientOrder clientOrder1 = new ClientOrder();
        clientOrder1.setId(1L);
        ClientOrder clientOrder2 = new ClientOrder();
        clientOrder2.setId(clientOrder1.getId());
        assertThat(clientOrder1).isEqualTo(clientOrder2);
        clientOrder2.setId(2L);
        assertThat(clientOrder1).isNotEqualTo(clientOrder2);
        clientOrder1.setId(null);
        assertThat(clientOrder1).isNotEqualTo(clientOrder2);
    }
}
