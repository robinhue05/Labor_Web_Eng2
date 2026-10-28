package de.dhbw.skywatch;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Entry point of the SkyWatch backend.
// @SpringBootApplication turns on auto-config and scans this package for components.
// @OpenAPIDefinition just sets the title/description shown in Swagger UI
@OpenAPIDefinition(info = @Info(
		title = "SkyWatch API",
		version = "0.1.0",
		description = "Near-Earth Object Monitor - REST API",
		contact = @Contact(name = "DHBW Web-Engineering", email = "noreply@dhbw.de")
))
@SpringBootApplication
public class SkywatchApplication {

	public static void main(String[] args) {
		// starts the embedded Tomcat on port 8080
		SpringApplication.run(SkywatchApplication.class, args);
	}

}
