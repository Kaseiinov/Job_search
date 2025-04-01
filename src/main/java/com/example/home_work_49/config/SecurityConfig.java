package com.example.home_work_49.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String fetchUsersQuery = "select email, password, enabled\n" +
                "from users\n" +
                "where email = ?";

        String fetchRolesQuery = "select u.email, r.role\n" +
                "from users u, roles r\n" +
                "where email = ?\n" +
                "and u.ROLE_ID = r.id";
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(fetchUsersQuery)
                .authoritiesByUsernameQuery(fetchRolesQuery);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/users/createUser").permitAll()

                        // Закрытые эндпоинты (требуют авторизации)
                        .requestMatchers(HttpMethod.POST, "/vacancies/create/vacancy").fullyAuthenticated()
                        .requestMatchers(HttpMethod.DELETE, "/vacancies/delete/**").fullyAuthenticated()
                        .requestMatchers(HttpMethod.PUT, "/vacancies/update/**").fullyAuthenticated()
                        .requestMatchers(HttpMethod.POST, "/resumes/createResume").fullyAuthenticated()
                        .requestMatchers(HttpMethod.PUT, "/users/update/**").fullyAuthenticated()

                        // Открытые эндпоинты (вакансии и резюме)
                        .requestMatchers("/vacancies/**").permitAll()
                        .requestMatchers("/resumes/**").permitAll()

                        // Все остальные запросы к /users требуют авторизации
                        .requestMatchers("/users/**").fullyAuthenticated()

                        // Блокировка неучтенных эндпоинтов
                        .anyRequest().denyAll()
                );

        return http.build();
    }

}
