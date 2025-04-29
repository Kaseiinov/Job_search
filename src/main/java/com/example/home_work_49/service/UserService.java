package com.example.home_work_49.service;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.dto.UserEditDto;
import com.example.home_work_49.dto.UserImageDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import javax.management.relation.RoleNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    void makeResetPasswdLink(HttpServletRequest request) throws UserNotFoundException, MessagingException, UnsupportedEncodingException;

    void updateResetPasswordToken(String token, String email);

    User getUserByResetPasswordToken(String token);

    void updatePassword(User user, String password);

    void updateUserByEmail(String email, UserEditDto userEditDto) throws SuchEmailAlreadyExistsException, RoleNotFoundException;

    UserDto getUserByPhone(String phoneNumber);

    UserDto getUserByEmail(String userEmail);

    UserEditDto getUserByEmailByEditType(String userEmail);

    UserDto getUserById(Long id);

    List<UserDto> getApplicantsByVacancy(String vacancyName);

    void addUser(UserDto userDto) throws SuchEmailAlreadyExistsException, RoleNotFoundException;
}
