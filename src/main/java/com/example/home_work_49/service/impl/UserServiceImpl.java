package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.dto.UserImageDto;
import com.example.home_work_49.exceptions.ImageNotFoundException;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.Role;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.UserImage;
import com.example.home_work_49.repository.RoleRepository;
import com.example.home_work_49.repository.UserImageRepository;
import com.example.home_work_49.repository.UserRepository;
import com.example.home_work_49.service.UserService;
import com.example.home_work_49.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserImageRepository userImageRepository;
    private final FileUtil fileUtil;

    @Override
    public void updateUserByEmail(String email, UserDto userDto) throws SuchEmailAlreadyExistsException, RoleNotFoundException {
        boolean isExistsUser = userRepository.existsUserByEmail(email);
        if(!isExistsUser){
            throw new UserNotFoundException();
        }

        String filename = fileUtil.saveUploadFile(userDto.getAvatar().getFile(), "images/");

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        UserImage userImage = new UserImage();
        userImage.setUser(user);
        userImage.setFileName(filename);

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
//        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userImage);

//        user.setAccountType(userDto.getAccountType().toUpperCase());
//        user.setRoles(Arrays.asList(roleRepository.findRoleByRole(userDto.getAccountType().toUpperCase())));

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
                        .avatar(
                                e.getAvatar() == null ? null :
                                        UserImageDto.builder()
                                                .id(e.getAvatar().getId())
                                                .userId(e.getId())
                                                .fileName(e.getAvatar().getFileName())
                                                .build()
                        )
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
                .avatar(
                        user.getAvatar() == null ? null :
                                UserImageDto.builder()
                                        .id(user.getAvatar().getId())
                                        .userId(user.getId())
                                        .fileName(user.getAvatar().getFileName())
                                        .build()
                )
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public void addUser(UserDto userDto) throws SuchEmailAlreadyExistsException, RoleNotFoundException {

        boolean isUserExists = userRepository.existsUserByEmail(userDto.getEmail());
        if(isUserExists){
            throw new SuchEmailAlreadyExistsException();
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAccountType(userDto.getAccountType().toUpperCase());
        user.setEnabled(true);

        Role role = roleRepository.findRoleByRole(userDto.getAccountType().toUpperCase());

        user.setRoles(Arrays.asList(role));
        role.setUsers(Arrays.asList(user));

        userRepository.save(user);
    }

}
