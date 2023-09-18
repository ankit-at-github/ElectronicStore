package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Set;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//This will load default context
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;
    private Role role;
    private User user;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
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

    }
    @Test
    public void createUserTest() throws Exception {
        UserDto dto = mapper.map(user, UserDto.class);
        //1. we have to call a URL (/users) + make a Post call + Send User data as json
        //2. data as jason + status created
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        //actual request for url
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }
    private String convertObjectToJsonString(Object user){
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Test
    public void updateUserTest() throws Exception {
        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        String userId = "123";
        //users/{userId} + put request + json
        mockMvc.perform(MockMvcRequestBuilders.put("/users/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }
    @Test
    public void getAllUsersTest() throws Exception {
        UserDto object1 = UserDto.builder().name("Durgesh").email("durgesh@gmail.com").password("durgesh").about("Testing").build();
        UserDto object2 = UserDto.builder().name("Sumit").email("durgesh@gmail.com").password("durgesh").about("Testing").build();
        UserDto object3 = UserDto.builder().name("Ankit").email("durgesh@gmail.com").password("durgesh").about("Testing").build();
        UserDto object4 = UserDto.builder().name("Uttam").email("durgesh@gmail.com").password("durgesh").about("Testing").build();

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(object1, object2, object3, object4
        ));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);

        Mockito.when(userService.getAllUser(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
