package com.application.todoapplication.services;

import com.application.todoapplication.dto.UserGetDto;
import com.application.todoapplication.dto.UserPostDto;
import com.application.todoapplication.dto.UserRegister;
import com.application.todoapplication.entities.User;

import java.util.List;


public interface UserService {
    UserGetDto findById(Long id);
    UserGetDto findByUsername(String username);
    List<UserGetDto> findAll ();

    String findUser(Long id);


    UserGetDto create(UserPostDto userPostDto);

    UserGetDto register(UserPostDto userRegister);

    UserGetDto fromUser(User user);

}
