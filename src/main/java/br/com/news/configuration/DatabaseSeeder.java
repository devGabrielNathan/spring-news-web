package br.com.news.configuration;

import br.com.news.entity.Author;
import br.com.news.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;

@Configuration
@Profile("mock")
public class DatabaseSeeder {
    @Bean
    public CommandLineRunner initDatabase(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {

        
        return args -> {
            
            seedRedator(authorRepository, passwordEncoder);
            
            seedRevisor(authorRepository, passwordEncoder);

            seedAuthors(authorRepository, passwordEncoder);
        };
    }

    private void seedRedator(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        
        String redatorEmail = "redator@admin.com";

        if (!authorRepository.existsByEmail(redatorEmail)) {
            Author redator = new Author();
            redator.setName("Redator Admin");
            redator.setEmail(redatorEmail);
            redator.setPassword(passwordEncoder.encode("12345678"));
            redator.setBirthDate(LocalDate.now());
            redator.setEducation("Sistemas de Informação");
            redator.setSignature("Redator Admin");
            redator.setEditor(false);

            authorRepository.save(redator);
            System.out.println(">>> Redator Admin criado com sucesso!");
            return;
        }
        System.out.println(">>> Redator Admin já existe no banco de dados. Pulando criação.");
    }

    private void seedRevisor(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        
        String revisorEmail = "revisor@admin.com";

        if (!authorRepository.existsByEmail(revisorEmail)) {
            Author revisor = new Author();
            revisor.setName("Revisor Admin");
            revisor.setEmail(revisorEmail);
            revisor.setPassword(passwordEncoder.encode("12345678"));
            revisor.setBirthDate(LocalDate.now());
            revisor.setEducation("Sistemas de Informação");
            revisor.setSignature("Revisor Admin");
            revisor.setEditor(true);

            authorRepository.save(revisor);
            System.out.println(">>> Revisor Admin criado com sucesso!");
            return;
        }
        System.out.println(">>> Revisor Admin já existe no banco de dados. Pulando criação.");
    }

    private void seedAuthors(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        
        String[] authorNames = {"Gabriel Nathan", "Itallo", "Leonardo", "Nathan"};

        String[] authorEmails = {"gabrielnathan@gmail.com", "itallo@gmail.com", "leonardo@gmail.com", "nathan@gmail.com"};

        String[] authorSignature = {"Gabriel Nathan", "Itallo", "Leonardo", "Nathan"};

        for (int i = 0; i < authorNames.length; i++) {
            if (!authorRepository.existsByEmail(authorEmails[i])) {
                Author author = new Author();
                author.setName(authorNames[i]);
                author.setEmail(authorEmails[i]);
                author.setPassword(passwordEncoder.encode("12345678"));
                author.setBirthDate(LocalDate.now());
                author.setEducation("Sistemas de Informação");
                author.setSignature(authorSignature[i]);
                author.setEditor(true);

                authorRepository.save(author);
                System.out.println(">>> " + authorNames[i] + " criado com sucesso!");
                continue;
            }

            System.out.println(">>> " + authorNames[i] + " já existe no banco de dados. Pulando criação.");
        }
    }
}