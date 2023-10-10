package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.JWTRequest;
import com.lcwd.electronic.store.dtos.JWTResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.exceptions.BadApiRequestException;
import com.lcwd.electronic.store.security.JWTHelper;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTHelper jwtHelper;
    //Creating End-point
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request){
        logger.info("Email: {}, Password: {} ", request.getEmail(), request.getPassword());
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtHelper.generateToken(userDetails);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        JWTResponse response = JWTResponse.builder()
                .jwtToken(token)
                .user(userDto)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try{
            manager.authenticate(authentication);
        }catch(BadCredentialsException b){
            throw new BadApiRequestException("Invalid UserName or Password !!");
        }
    }

    @GetMapping("/current")
    public ResponseEntity<UserDetails> getCurrentUser(Principal principal){
        //return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
        String name = principal.getName();
        return new ResponseEntity<>(userDetailsService.loadUserByUsername(name), HttpStatus.OK);
    }
}
