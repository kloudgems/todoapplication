package com.application.todoapplication.services.impl;

import com.application.todoapplication.dto.UserGetDto;
import com.application.todoapplication.dto.UserPostDto;
import com.application.todoapplication.dto.UserRegister;
import com.application.todoapplication.entities.User;
import com.application.todoapplication.repos.UserRepository;
import com.application.todoapplication.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserGetDto findById(Long id) {
        return fromUser(userRepository.getOne(id));
    }

    @Override
    public UserGetDto findByUsername(String username) {
        return fromUser(userRepository.findByUsername(username));
    }

    @Override
    public List<UserGetDto> findAll() {
        List<UserGetDto> userGetDtoList = new ArrayList<>();
        userRepository.findAll().forEach(t->{
            userGetDtoList.add(fromUser(t));
        });
        return userGetDtoList;
    }

    @Override
    public String findUser(Long id) {
        return null;
    }

    @Override
    public UserGetDto create(UserPostDto userPostDto) {
        return fromUser(userRepository.save(toUser(userPostDto)));
    }

    @Override
    public UserGetDto register(UserPostDto userPostDto) {
        return fromUser(userRepository.save(toUser(userPostDto)));
    }

    private User toUser(UserPostDto userPostDto){
        User user = new User();
        user.setPassword(passwordEncoder.encode(userPostDto.getPassword()));
        user.setEmail(userPostDto.getPassword());
        user.setFirstName(userPostDto.getFirstName());
        user.setLastName(userPostDto.getLastName());
        user.setUsername(userPostDto.getUsername());
        user.setPhoneNumber(userPostDto.getPhoneNumber());
        return user;
    }

    @Override
    public UserGetDto fromUser(User user){
        UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId(user.getId());
        userGetDto.setEmail(user.getPassword());
        userGetDto.setFirstName(user.getFirstName());
        userGetDto.setLastName(user.getLastName());
        userGetDto.setUsername(user.getUsername());
        userGetDto.setPhoneNumber(user.getPhoneNumber());
        return  userGetDto;
    }
}
