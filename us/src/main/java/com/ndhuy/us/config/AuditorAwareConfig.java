package com.ndhuy.us.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ndhuy.us.metadata.SpringSecurityAuditorAware;

@Configuration
@EnableJpaAuditing
public class AuditorAwareConfig {
    @Bean
    AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}
