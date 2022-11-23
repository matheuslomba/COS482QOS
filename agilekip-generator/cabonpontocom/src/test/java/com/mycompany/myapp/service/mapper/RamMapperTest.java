package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RamMapperTest {

    private RamMapper ramMapper;

    @BeforeEach
    public void setUp() {
        ramMapper = new RamMapperImpl();
    }
}
