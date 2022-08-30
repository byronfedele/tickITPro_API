package com.revature.tickITPro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TickItProApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickItProApplication.class, args);
	}

}
