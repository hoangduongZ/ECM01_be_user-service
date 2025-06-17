package com.ecomerce.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
//	@Bean(initMethod = "migrate")
//	public Flyway flyway() {
//		return Flyway.configure()
//				.dataSource("jdbc:mysql://localhost:3306/user_service", "root", "123456@Ab")
//				.locations("classpath:db/migration")
//				.baselineOnMigrate(true) // nếu database đã có dữ liệu cũ
//				.load();
//	}
}
