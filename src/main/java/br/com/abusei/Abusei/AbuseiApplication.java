package br.com.abusei.Abusei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AbuseiApplication{
		
	public static void main(String[] args) {
		SpringApplication.run(AbuseiApplication.class, args);
	}
	
	
}
