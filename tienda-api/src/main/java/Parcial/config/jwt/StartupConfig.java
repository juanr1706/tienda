package Parcial.config.jwt;

import Parcial.model.jtw.User;
import Parcial.repository.jwt.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class StartupConfig {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            if (userRepository.findByUsername(adminUsername).isEmpty()) {
                User admin = User.builder()
                        .username(adminUsername)
                        .password(passwordEncoder.encode("12345678"))
                        .build();
                userRepository.save(admin);
                System.out.println("✅ Usuario admin creado con éxito.");
            } else {
                System.out.println("ℹ️ Usuario admin ya existe, no se creó nuevamente.");
            }
        };
    }
}
