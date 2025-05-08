package com.example.blockchainapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BlockchainapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainapiApplication.class, args);
	}

}
