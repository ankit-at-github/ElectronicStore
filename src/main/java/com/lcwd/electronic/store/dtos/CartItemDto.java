package com.lcwd.electronic.store.dtos;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;
}
