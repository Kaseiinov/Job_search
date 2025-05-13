package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.dto.UserEditDto;
import com.example.home_work_49.dto.UserImageDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.Role;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.UserImage;
import com.example.home_work_49.repository.UserRepository;
import com.example.home_work_49.service.RoleService;
import com.example.home_work_49.service.UserService;
import com.example.home_work_49.util.CommonUtilities;
import com.example.home_work_49.util.FileUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final FileUtil fileUtil;
    private final PasswordEncoder encoder;
    private final EmailService emailService;


    @Override
    public void makeResetPasswdLink(HttpServletRequest request) throws UserNotFoundException, MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(token, email);
        String resetPasswordLnk = CommonUtilities.getSiteURL(request) + "/auth/reset_password?token=" + token;
        emailService.sendEmail(email, resetPasswordLnk);
    }

    @Override
    public void updateResetPasswordToken(String token, String email){
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }

    @Override
    public User getUserByResetPasswordToken(String token){
        return userRepository.findByResetPasswordToken(token).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void updatePassword(User user, String password){
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.saveAndFlush(user);
    }



    @Override
    public void updateUserByEmail(String email, UserEditDto userEditDto) throws SuchEmailAlreadyExistsException, RoleNotFoundException {
        boolean isExistsUser = userRepository.existsUserByEmail(email);
        if(!isExistsUser){
            throw new UserNotFoundException();
        }

        String filename = fileUtil.saveUploadFile(userEditDto.getAvatar().getFile(), "images/");

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        UserImage userImage = new UserImage();
        userImage.setUser(user);
        userImage.setFileName(filename);

        user.setName(userEditDto.getName());
        user.setSurname(userEditDto.getSurname());
        user.setAge(userEditDto.getAge());
//        user.setEmail(userDto.getEmail());
        user.setPassword(userEditDto.getPassword());
        user.setPhoneNumber(userEditDto.getPhoneNumber());
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
    public Optional<User> getUserByEmailModel(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        return user;
    }

    @Override
    public UserEditDto getUserByEmailByEditType(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return builderEditType(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.of(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
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

        Role role = roleService.findRoleByRole(userDto.getAccountType().toUpperCase());

        user.setRoles(Arrays.asList(role));
        role.setUsers(Arrays.asList(user));

        userRepository.saveAndFlush(user);
    }

    @Override
    public void save(User user){
        userRepository.saveAndFlush(user);
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

    private UserEditDto builderEditType(User user) {
        return UserEditDto.builder()
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

}
