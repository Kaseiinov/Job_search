package com.example.home_work_49.config;

import com.example.home_work_49.models.CustomOAuth2User;
import com.example.home_work_49.service.impl.AuthUserDetailsService;
import com.example.home_work_49.service.impl.CustomAuthenticationSuccessHandler;
import com.example.home_work_49.service.impl.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    // Remove CustomAuthenticationSuccessHandler from constructor
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthUserDetailsService authUserDetailsService,
                                           CustomAuthenticationSuccessHandler successHandler) throws Exception {
        // Inject both as method parameters

        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(successHandler)  // Use method parameter
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())

                .oauth2Login(oauth -> oauth
                        .loginPage("/auth/login")
                        .userInfoEndpoint(userConfig -> userConfig
                                .userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            var oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                            authUserDetailsService.processOAuthPostLogin(
                                    oauthUser.getAttribute("email"),
                                    oauthUser.getAttribute("given_name"),
                                    oauthUser.getAttribute("family_name")
                            );
                            response.sendRedirect("/");
                        }))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/search/vacancy/advanced").permitAll()
                        .requestMatchers(HttpMethod.GET, "/publications").permitAll()
                        .requestMatchers(HttpMethod.POST, "publications/**").fullyAuthenticated()
                        .requestMatchers("/users/**").fullyAuthenticated()
                        .requestMatchers("/vacancies").permitAll()
                        .requestMatchers("/api/vacancies").permitAll()
                        .requestMatchers("/vacancies/**").hasAnyAuthority("EMPLOYER", "ADMIN")
                        .requestMatchers("/resumes").hasAnyAuthority("EMPLOYER", "ADMIN")
                        .requestMatchers("/resumes/**").hasAnyAuthority("APPLICANT", "ADMIN")
                        .anyRequest().fullyAuthenticated()
                );

        return http.build();
    }
}