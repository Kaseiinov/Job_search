package com.example.home_work_49.service.impl;

import com.example.home_work_49.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectURL = determineRedirectUrl(authentication);
        response.sendRedirect(redirectURL);
    }

    public String determineRedirectUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectURL = "/";

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ADMIN") || role.equals("admin")) {
                redirectURL = "/";
                break;
            } else if (role.equals("EMPLOYER") || role.equals("employer")) {
                redirectURL = "/resumes";
                break;
            } else if (role.equals("APPLICANT") || role.equals("applicant")) {
                redirectURL = "/vacancies";
                break;
            }
        }
        return redirectURL;
    }
}

