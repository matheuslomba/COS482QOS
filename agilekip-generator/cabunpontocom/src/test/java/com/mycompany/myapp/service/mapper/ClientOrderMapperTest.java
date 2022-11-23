package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientOrderMapperTest {

    private ClientOrderMapper clientOrderMapper;

    @BeforeEach
    public void setUp() {
        clientOrderMapper = new ClientOrderMapperImpl();
    }
}
