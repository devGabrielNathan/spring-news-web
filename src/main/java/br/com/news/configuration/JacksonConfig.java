package br.com.news.configuration;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.withConfigOverride(LocalDate.class,
                    override -> override.setFormat(JsonFormat.Value.forPattern(DATE_FORMAT)));
            builder.withConfigOverride(LocalDateTime.class,
                    override -> override.setFormat(JsonFormat.Value.forPattern(DATE_TIME_FORMAT)));
        };
    }
}
