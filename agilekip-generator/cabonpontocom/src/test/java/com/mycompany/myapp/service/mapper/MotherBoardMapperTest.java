package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MotherBoardMapperTest {

    private MotherBoardMapper motherBoardMapper;

    @BeforeEach
    public void setUp() {
        motherBoardMapper = new MotherBoardMapperImpl();
    }
}
