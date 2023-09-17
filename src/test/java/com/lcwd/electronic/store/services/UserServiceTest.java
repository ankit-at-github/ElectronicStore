package com.lcwd.electronic.store.services;

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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}
