package com.example.home_work_49.config;

import com.example.home_work_49.models.CustomOAuth2User;
import com.example.home_work_49.service.impl.AuthUserDetailsService;
import com.example.home_work_49.service.impl.CustomAuthenticationSuccessHandler;
import com.example.home_work_49.service.impl.CustomOAuth2UserService;
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
//    private final DataSource dataSource;
    private final AuthUserDetailsService authUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler successHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(successHandler)
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
//                .csrf(AbstractHttpConfigurer::disable)

                .oauth2Login(oauth -> oauth
                .loginPage("/auth/login")
                .userInfoEndpoint(userConfig -> userConfig
                        .userService(customOAuth2UserService))
                .successHandler((request, response, authentication) -> {
                    var oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                    authUserDetailsService.processOAuthPostLogin(oauthUser.getAttribute("email"), oauthUser.getAttribute("given_name"), oauthUser.getAttribute("family_name"));
                    response.sendRedirect("/");
                }))

                .authorizeHttpRequests(authorize -> authorize
                        // Public endpoints
                        .requestMatchers("/static/**").permitAll()

                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/search/vacancy/advanced").permitAll()

                        // Publicaiton
                                .requestMatchers(HttpMethod.GET, "/publications").permitAll()
                                .requestMatchers(HttpMethod.POST, "publications/**").fullyAuthenticated()


                        // User endpoints
                        .requestMatchers("/users/**").fullyAuthenticated()


                        // Vacancy endpoints
                        .requestMatchers("/vacancies").permitAll()
                        .requestMatchers("/api/vacancies").permitAll()
                        .requestMatchers("/vacancies/**").hasAnyAuthority("EMPLOYER", "ADMIN")


                        // Resume endpoints
                        .requestMatchers("/resumes").hasAnyAuthority("EMPLOYER", "ADMIN")
                        .requestMatchers("/resumes/**").hasAnyAuthority("APPLICANT", "ADMIN")


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