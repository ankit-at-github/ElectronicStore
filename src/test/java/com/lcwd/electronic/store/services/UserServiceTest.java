package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.RoleRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    User user;
    Role role;
    String roleId;
    @Autowired
    private ModelMapper mapper;
    @BeforeEach()
    public void init(){

        role = Role.builder().roleId("abc").roleName("NORMAL").build();

        user = User.builder()
                .name("Durgesh")
                .email("durgesh@gmail.com")
                .about("This method is for testing")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                .roles(Set.of(role))
                .build();

        roleId = "abc";
    }
    @Test
    public void createUserTest(){
        //If you are giving any roleId to mockito through roleRepository, it will return role
        Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(role));
        //If you are giving any object to mockito through userRepository, it will return user
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));

        System.out.println(user1.getName());
        //If user1 is not null that means user is created
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Durgesh", user1.getName());
    }
    //Update User Test
    @Test
    public void updateUserTest(){
        String userId="gcmgchgv";
        UserDto userDto = UserDto.builder()
                .name("Durgesh Kumar Tiwari")
                .about("This is updated user about details")
                .gender("Male")
                .imageName("xyz.png")
                .build();

        //Passing any string to user.
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updatedUser = userService.updateUser(userDto, userId);

        //If you don't call update, then name will not be updated, giving old user as input
        //UserDto updatedUser = mapper.map(user, UserDto.class);

        System.out.println(updatedUser.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(), updatedUser.getName(), "Name is not validated!!");
    }
    @Test
    public void deleteUserTest() throws IOException {
        String userid="userIdabc";
        Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));
        userService.deleteUser(userid);
        //We are getting void in return, we are checking delete method is running how many times using mockito.
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }
    @Test
    public void getAllUsersTest(){
        User user1 = User.builder()
                .name("Uttam")
                .email("durgesh@gmail.com")
                .about("This method is for testing")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwdd")
                .roles(Set.of(role))
                .build();
        User user2 = User.builder()
                .name("Ankit")
                .email("durgesh@gmail.com")
                .about("This method is for testing")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                .roles(Set.of(role))
                .build();
        List<User> userList = Arrays.asList(user1, user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
        //getContent -> List
        Assertions.assertEquals(2, allUser.getContent().size());
    }
    @Test
    public void getUserByIdTest(){
        String userid = "userIdTest";
        Mockito.when(userRepository.findById(userid)).thenReturn(Optional.of(user));
        //Actual Call
        UserDto userDto = userService.getUserById(userid);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(), userDto.getName(), "Name not matched!!");
    }
    @Test
    public void getUserByEmailTest(){
        String email = "abc@gmail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserByEmail(email);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmail(), userDto.getEmail(), "Email not matched");
    }
    @Test
    public void searchUserTest(){
        User user1 = User.builder()
                .name("Uttam Kumar")
                .email("durgesh@gmail.com")
                .about("This method is for testing")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwdd")
                .roles(Set.of(role))
                .build();
        User user2 = User.builder()
                .name("Pankaj Tiwari")
                .email("durgesh@gmail.com")
                .about("This method is for testing")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                .roles(Set.of(role))
                .build();
        User user3 = User.builder()
                .name("Ankit Kumar")
                .email("durgesh@gmail.com")
                .about("This method is for testing")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwdd")
                .roles(Set.of(role))
                .build();

        String keywords = "kumar";
        Mockito.when(userRepository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user1, user2, user3));

        //Jo method return kar raha hai
        List<UserDto> userDtos = userService.searchUser(keywords);

        Assertions.assertEquals( 3, userDtos.size(), "Size not matched");
    }
}
