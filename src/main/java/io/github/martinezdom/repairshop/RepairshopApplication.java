package io.github.martinezdom.repairshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@OpenAPIDefinition(info = @Info(title = "Repair Shop API", version = "1.0", description = "API de Gestión para el Taller Mecánico"), security = @SecurityRequirement(name = "bearerAuth"))
@SecurityScheme(name = "bearerAuth", description = "Pega aquí tu token JWT", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SpringBootApplication
public class RepairshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepairshopApplication.class, args);
	}

}
