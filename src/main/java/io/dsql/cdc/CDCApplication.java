package io.dsql.cdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class CDCApplication {

	public static void main(String[] args) {
		SpringApplication.run(CDCApplication.class, args);
	}

}
