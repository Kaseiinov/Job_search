package com.example.home_work_49.service.impl;

import com.example.home_work_49.dao.UserDao;
import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.User;
import com.example.home_work_49.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public void updateUserByName(String name, UserDto userDto) throws SuchEmailAlreadyExistsException {
        userDao.getUserByName(name).orElseThrow(UserNotFoundException::new);
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userDto.getAvatar());
        user.setAccountType(userDto.getAccountType());

        boolean isExists = userDao.emailExists(user.getEmail());
        if(isExists){
            throw new SuchEmailAlreadyExistsException();
        }else{
            userDao.updateUserByName(name, user);
        }

    }

    @Override
    public UserDto getUserByName(String userName) {
        User user = userDao.getUserByName(userName)
                .orElseThrow(UserNotFoundException::new);
        return builder(user);
    }



    @Override
    public UserDto getUserByPhone(String userPhone) {
        User user = userDao.getUserByPhone(userPhone)
                .orElseThrow(UserNotFoundException::new);
        return builder(user);
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        User user = userDao.getUserByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        return builder(user);
    }

    @Override
    public List<UserDto> getApplicantsByVacancy(String vacancyName) {
        List<User> userList =  userDao.getApplicantsByVacancy(vacancyName);

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
    public void addUser(UserDto userDto) throws SuchEmailAlreadyExistsException {

        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userDto.getAvatar());
        user.setAccountType(userDto.getAccountType());

        boolean isExists = userDao.emailExists(user.getEmail());
        if(isExists){
            throw new SuchEmailAlreadyExistsException();
        }else{
            userDao.addUser(user);
        }
    }
}
