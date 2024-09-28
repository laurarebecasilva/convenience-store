package com.api.rest.conveniencestore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Habilita a personalização da segurança
public class ConfigurationSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita proteção CSRF, comum em APIs que utilizam tokens
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define que o sistema será stateless (sem sessão)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/**").permitAll() // Permite acesso ao endpoint de registro usuario
                                .anyRequest().authenticated()); // Exige autenticação para qualquer outra requisição
        return http.build(); // Constrói e retorna a cadeia de filtros de segurança
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Bean para criptografar senhas
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); //configuração do authenticationManager
    }
}
//O bean serve para exportar uma classe para o Spring, fazendo com que ele consiga carrega-la e
// realize a dua injeção de dependencia em outras classes.