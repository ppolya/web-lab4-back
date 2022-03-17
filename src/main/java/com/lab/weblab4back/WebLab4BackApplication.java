package com.lab.weblab4back;

import com.lab.weblab4back.model.Role;
import com.lab.weblab4back.service.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class WebLab4BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebLab4BackApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UsersService userService) {
        return(args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
        });
    }
}

