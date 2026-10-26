package de.dhbw.skywatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Entry point of the SkyWatch backend.
// @SpringBootApplication turns on auto-config and scans this package for components.
@SpringBootApplication
public class SkywatchApplication {

	public static void main(String[] args) {
		// starts the embedded Tomcat on port 8080
		SpringApplication.run(SkywatchApplication.class, args);
	}

}
