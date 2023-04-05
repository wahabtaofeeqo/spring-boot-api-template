package com.example.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.template.controllers.IndexController;
import static org.assertj.core.api.Assertions.assertThat;

class ApiApplicationTests extends AbstractTest {

    @Autowired
    private IndexController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
