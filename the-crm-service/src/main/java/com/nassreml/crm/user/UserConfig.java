package com.nassreml.crm.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class UserConfig {

    // DEFAULT USERS: Thats only for test LOCALLY

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User first = new User("FirstUser", new BCryptPasswordEncoder().encode("1234"), true);
            User second = new User("SecondUser", new BCryptPasswordEncoder().encode("1234"), false);

            userRepository.saveAll(
                    List.of(first, second)
            );

        };
    }
}
