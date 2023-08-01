package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto user);

    //update
    UserDto updateUser(UserDto userDto, String userId);

    //delete
    void deleteUser(String userId);

    //get all users
    List<UserDto> getAllUser();

    //get single user by id
    UserDto getUserById(String userId);

    //get single usr by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

    //other user specific features

}
