package com.thinkon;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "com.thinkon")
public class Main {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		
		DataSource dataSource = context.getBean(DataSource.class);
		try (Connection connection = dataSource.getConnection()) {
			System.out.println("Database connected successfully!");
		} catch (Exception e) {
			System.err.println("Failed to connect to the database: " + e.getMessage());
		}
	}
}
