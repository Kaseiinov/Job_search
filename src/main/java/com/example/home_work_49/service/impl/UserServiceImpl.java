package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.Role;
import com.example.home_work_49.models.User;
import com.example.home_work_49.repository.RoleRepository;
import com.example.home_work_49.repository.UserRepository;
import com.example.home_work_49.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void updateUserByEmail(String email, UserDto userDto) throws SuchEmailAlreadyExistsException, RoleNotFoundException {
        boolean isExistsUser = userRepository.existsUserByEmail(email);
        if(!isExistsUser){
            throw new UserNotFoundException();
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userDto.getAvatar());
        user.setAccountType(userDto.getAccountType().toUpperCase());
        user.getRoles().add(roleRepository.findRoleByRole(userDto.getAccountType().toUpperCase()));

        userRepository.save(user);

    }

    @Override
    public UserDto getUserByPhone(String userPhone) {
        User user = userRepository.findByPhoneNumber(userPhone);
        return builder(user);
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return builder(user);
    }

    @Override
    public List<UserDto> getApplicantsByVacancy(String vacancyName) {
        List<User> userList = userRepository.findAllByVacancyName(vacancyName);

        return userList.stream()
                .map(e -> UserDto
                        .builder()
                        .id(e.getId())
                        .name(e.getName())
                        .surname(e.getSurname())
                        .age(e.getAge())
                        .email(e.getEmail())
                        .phoneNumber(e.getPhoneNumber())
                        .avatar(e.getAvatar())
                        .accountType(e.getAccountType())
                        .build())
                .toList();
    }

    private UserDto builder(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public void addUser(UserDto userDto) throws SuchEmailAlreadyExistsException, RoleNotFoundException {

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userDto.getAvatar());
        user.setAccountType(userDto.getAccountType().toUpperCase());
        user.setEnabled(true);
        user.getRoles().add(roleRepository.findRoleByRole(userDto.getAccountType().toUpperCase()));

        userRepository.save(user);
    }

}
