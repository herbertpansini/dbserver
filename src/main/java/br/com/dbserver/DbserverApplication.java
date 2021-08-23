package br.com.dbserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DbserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbserverApplication.class, args);
	}

}
