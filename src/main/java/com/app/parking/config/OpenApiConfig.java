package com.app.parking.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        description = """
                JWT Authorization header using the Bearer scheme.
                
                Example:
                Bearer eyJhbGciOiJIUzI1NiJ9...
                """
)
public class OpenApiConfig {


    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()

                .info(new Info()
                        .title("Parking System - Parking Sharing Platform API")
                        .version("v1.0")
                        .description("""
                                REST APIs for a Peer-to-Peer Parking Sharing Platform.
                                
                                Features:
                                • JWT Authentication & Authorization
                                • Parking Listing Management
                                • Search & Booking System
                                • Wallet Recharge & Payments
                                • Reviews & Ratings
                                • Email Notifications
                                
                                This API enables parking owners to monetize
                                unused parking spaces while allowing drivers
                                to find and book parking seamlessly.
                                """)
                        .contact(new Contact()
                                .name("Nitish Sahni")
                                .email("nitishsahni9565@gmail.com")
                                .url("https://github.com/LogicNinjaX"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))

                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                );
    }

}
