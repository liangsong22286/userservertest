package com.demo.userserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.annotation.MapperScan;

//@EnableEurekaClient
@SpringBootApplication
//@EnableHystrixDashboard
//@EnableCircuitBreaker
//@EnableHystrix
//@EnableFeignClients
//@EnableApolloConfig(value = "application", order = 10)
@Configuration
@MapperScan("com.demo.userserver.dao")
public class UserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServerApplication.class, args);
	}
}
