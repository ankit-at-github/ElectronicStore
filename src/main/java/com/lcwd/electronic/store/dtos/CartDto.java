package com.lcwd.electronic.store.dtos;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private String cartId;
    private Date createdAt;
    private UserDto user;

    //Mapping Cart Items here, how many items are there in this cart
    private List<CartItemDto> items = new ArrayList<>();
}
