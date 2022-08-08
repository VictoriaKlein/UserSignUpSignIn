package com.victoriaKlein.UserSignUpSignIn;

import com.victoriaKlein.UserSignUpSignIn.model.User;
import com.victoriaKlein.UserSignUpSignIn.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class UserConfig {
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserConfig(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepo userRepo) {
        return args -> {
            User user = new User("Mark", bCryptPasswordEncoder.encode("1234"), true, true, true);
            User user2 = new User("Kate", bCryptPasswordEncoder.encode("123"), true, true, true);
            userRepo.saveAll(List.of(user, user2));
        };
    }
}
