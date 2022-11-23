package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GpuMapperTest {

    private GpuMapper gpuMapper;

    @BeforeEach
    public void setUp() {
        gpuMapper = new GpuMapperImpl();
    }
}
