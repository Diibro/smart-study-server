package com.smartstudy.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Dushime Brother and Iakoze Pacifique",
                        email = "brother.dushime@gmail.com",
                        url = "https://brother.descsoftlab.com"
                ),
                description = "Rest Api documentation for Smart study App",
                title = "Smart Study Rest api document",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8081"
                ),
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Access key for getting access to resources",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class APIDocumentation {
}
