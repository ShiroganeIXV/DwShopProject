package com.dwshop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.dwshop.common.entity", "com.dwshop.admin.user"})
public class DwshopBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DwshopBackendApplication.class, args);
	}

}
