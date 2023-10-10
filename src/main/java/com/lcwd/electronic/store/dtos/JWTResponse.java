package com.lcwd.electronic.store.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTResponse {
    //To send data
    private String jwtToken;
    private UserDto user;
}
