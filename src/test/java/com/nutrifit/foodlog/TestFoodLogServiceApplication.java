package com.nutrifit.foodlog;

import org.springframework.boot.SpringApplication;

public class TestFoodLogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(FoodLogServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
