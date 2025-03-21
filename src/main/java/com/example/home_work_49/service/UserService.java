package com.example.home_work_49.service;

import com.example.home_work_49.dto.UserDto;

public interface UserService {
    UserDto getUserByName(String userName);

    void addUser(UserDto userDto);
}
