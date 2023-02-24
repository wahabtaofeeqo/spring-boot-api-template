package com.example.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.template.controllers.IndexController;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiApplicationTests {

	@Autowired
	private IndexController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
