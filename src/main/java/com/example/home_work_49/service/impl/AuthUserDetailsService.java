package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.Authority;
import com.example.home_work_49.models.Role;
import com.example.home_work_49.models.User;
import com.example.home_work_49.service.RoleService;
import com.example.home_work_49.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmailModel(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );

    }

    private Collection<GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Authority> collection = new ArrayList<>();

        for (Role role : roles) {
            privileges.add(role.getRole());
            collection.addAll(role.getAuthorities());
        }
        for (Authority authority : collection) {
            privileges.add(authority.getAuthority());
        }
        return privileges;
    }

    public void processOAuthPostLogin(String email, String firstName, String lastName){
        var existingUser = userService.getUserByEmailModel(email);

        if(existingUser.isEmpty()){
            User user = new User();
            user.setName(firstName);
            user.setSurname(lastName);
            user.setEmail(email);
            user.setPassword(encoder.encode("qwe"));
            user.setAccountType("APPLICANT");
            user.setEnabled(true);
            user.setRoles(new HashSet<>());

            Role role = roleService.findRoleByRole(user.getAccountType().toUpperCase());

            user.addRole(role);

            userService.save(user);
        }

        UserDetails userDetails = loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
