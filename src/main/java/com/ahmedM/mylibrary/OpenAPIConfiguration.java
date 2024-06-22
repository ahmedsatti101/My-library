package com.ahmedM.mylibrary;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.*;

import java.util.*;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact contact = new Contact();
        contact.setEmail("ahmedysatti@gmail.com");
        contact.setName("Ahmed Mohamed");

        Info info = new Info()
                .contact(contact)
                .title("Book library API")
                .version("1.0")
                .description("This API exposes endpoints available in the API along with the HTTP requests that are available.");

        return new OpenAPI().info(info).servers(List.of(server));
    }
}
