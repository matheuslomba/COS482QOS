package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HdMapperTest {

    private HdMapper hdMapper;

    @BeforeEach
    public void setUp() {
        hdMapper = new HdMapperImpl();
    }
}
