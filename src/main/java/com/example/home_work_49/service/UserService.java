package com.example.home_work_49.service;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.dto.UserImageDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService {

    void updateUserByEmail(String email, UserDto userDto, UserImageDto userImageDto) throws SuchEmailAlreadyExistsException, RoleNotFoundException;

    UserDto getUserByPhone(String phoneNumber);

    UserDto getUserByEmail(String userEmail);

    List<UserDto> getApplicantsByVacancy(String vacancyName);

    void addUser(UserDto userDto) throws SuchEmailAlreadyExistsException, RoleNotFoundException;
}
