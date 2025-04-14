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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize -> authorize
                        // Public endpoints
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/").permitAll()

                        // User endpoints
                        .requestMatchers("/users/**").fullyAuthenticated()


                        // Vacancy endpoints
                        .requestMatchers("/vacancies/**").hasAnyAuthority("EMPLOYER", "ADMIN")
                        .requestMatchers("/vacancies/").permitAll()


                        // Resume endpoints
                        .requestMatchers("/resumes/**").hasAnyAuthority("APPLICANT", "ADMIN")
                        .requestMatchers("/resumes/").permitAll()


                        // all other requests
                        .anyRequest().fullyAuthenticated()
                );

        return http.build();
    }

}
//                        // Vacancy endpoints
//                        .requestMatchers(HttpMethod.GET, "/vacancies/create").hasAnyAuthority("EMPLOYER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/vacancies/create").hasAnyAuthority("EMPLOYER", "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/vacancies/delete/**").hasAnyAuthority("ADMIN", "EMPLOYER")
//                        .requestMatchers(HttpMethod.PUT, "/vacancies/update/**").hasAnyAuthority("ADMIN", "EMPLOYER")
//                        .requestMatchers(HttpMethod.GET, "/vacancies/**").permitAll()
//
//                        // Resume endpoints
//                        .requestMatchers(HttpMethod.POST, "/resumes/create").hasAnyAuthority("ADMIN", "APPLICANT")
//                        .requestMatchers(HttpMethod.GET, "/resumes/**").permitAll()