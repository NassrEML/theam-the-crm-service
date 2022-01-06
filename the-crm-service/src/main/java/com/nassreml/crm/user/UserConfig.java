package com.nassreml.crm.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    // Thats only for test LOCALLY

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User first = new User("FirstUser", "1234", true);
            User second = new User("SecondUser", "1234", false);

            userRepository.saveAll(
                    List.of(first, second)
            );

        };
    }
}
