package br.com.news.configuration;

import br.com.news.entity.Author;
import br.com.news.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;

@Configuration
public class DatabaseSeeder {
    @Bean
    public CommandLineRunner initDatabase(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            String adminEmail = "admin@admin.com";

            if (!authorRepository.existsByEmail(adminEmail)) {
                Author admin = new Author();
                admin.setName("Administrador Padrão");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("12345678"));
                admin.setBirthDate(LocalDate.now());
                admin.setEducation("Sistemas de Informação");
                admin.setSignature("Admin");
                admin.setEditor(true);

                authorRepository.save(admin);
                System.out.println("\n\n>>> Administrador padrão criado com sucesso!\n\n");
                return;
            }

            System.out.println("\n\n>>> Administrador padrão já existe no banco de dados. Pulando criação.\n\n");
        };
    }
}