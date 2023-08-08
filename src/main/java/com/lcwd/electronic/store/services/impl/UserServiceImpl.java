package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //Generate Unique Id in String format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //This method is converting dto to entity
        User user = dtoToEntity(userDto);

        User savedUser = userRepository.save(user);

        //Entity to Dto
        UserDto newDto = entityToDto(savedUser);
        
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not found with given id"));
        user.setName(userDto.getName());
        //email update
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(user);

        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not found with given id"));
        //delete user
        userRepository.delete(user);
    }

    @Override
    //public List<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        //List<User> users = userRepository.findAll();
        //Implementing Pagination

        //Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //Implementing Sorting
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        //Sort sort = Sort.by(sortBy);
        //pageNumber starts from 0
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);

        //Converting Entity(First Entity is converted into page) to DTO
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        return response;
        //return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not found with given id"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        //implementation after custom finder method - Special Method
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email id !!."));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        //implementation after custom finder method - Special Method
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName()).build();
//
//        return userDto;
        return mapper.map(savedUser, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .imageName(userDto.getImageName())
//                .gender(userDto.getGender()).build();
//
//        return user;
        return mapper.map(userDto, User.class);
    }
}
