package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PowerSourceMapperTest {

    private PowerSourceMapper powerSourceMapper;

    @BeforeEach
    public void setUp() {
        powerSourceMapper = new PowerSourceMapperImpl();
    }
}
