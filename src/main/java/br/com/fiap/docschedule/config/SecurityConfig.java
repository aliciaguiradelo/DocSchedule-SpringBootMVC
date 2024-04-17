package br.com.fiap.docschedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{

        return http
        .authorizeHttpRequests(auth -> auth
            .anyRequest()
            .authenticated()
        )
        .oauth2Login(login -> login
            .loginPage("/login")
            .defaultSuccessUrl("/consultas")
        )
        .logout(logout -> logout
            .logoutUrl("logout")
            .logoutSuccessUrl("/login")
    )
        .build();

    }
    
}