package com.example.home_work_49.service.impl;

import com.example.home_work_49.dao.UserDao;
import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.User;
import com.example.home_work_49.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public UserDto getUserByName(String userName) {
        User user = userDao.getUserByName(userName)
                .orElseThrow(UserNotFoundException::new);
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public void addUser(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userDto.getAvatar());
        user.setAccountType(userDto.getAccountType());

        userDao.addUser(user);
    }
}
