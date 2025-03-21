package com.example.home_work_49.service;

import com.example.home_work_49.dto.UserDto;

public interface UserService {
    UserDto getUserByName(String userName);

    UserDto getUserByPhone(String phoneNumber);

    UserDto getUserByEmail(String userEmail);

    void addUser(UserDto userDto);
}
