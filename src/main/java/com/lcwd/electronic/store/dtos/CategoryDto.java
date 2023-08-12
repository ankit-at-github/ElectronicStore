package com.lcwd.electronic.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min = 4, message = "title must be of minimum 4 characters")
    private String title;

    @NotBlank(message = "Description is required!!")
    private String description;

    private String coverImage;
}
