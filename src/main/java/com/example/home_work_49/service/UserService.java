package com.example.home_work_49.service;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;

import java.util.List;

public interface UserService {
    void updateUserByName(String name, UserDto userDto) throws SuchEmailAlreadyExistsException;

    UserDto getUserByName(String userName);

    UserDto getUserByPhone(String phoneNumber);

    UserDto getUserByEmail(String userEmail);

    List<UserDto> getApplicantsByVacancy(String vacancyName);

    void addUser(UserDto userDto) throws SuchEmailAlreadyExistsException;
}
