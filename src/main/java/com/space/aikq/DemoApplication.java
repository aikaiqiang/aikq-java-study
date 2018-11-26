package com.space.aikq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @description
 * @author aikq
 * @date 2018年10月24日 16:51
 */
@SpringBootApplication
@EnableAsync
public class DemoApplication {

	public static void main( String[] args ) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
