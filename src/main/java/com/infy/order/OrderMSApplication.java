package com.infy.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderMSApplication.class, args);
	}

}
