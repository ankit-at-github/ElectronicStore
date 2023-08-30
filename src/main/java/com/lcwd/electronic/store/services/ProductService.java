package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    //Create
    ProductDto create(ProductDto productDto);

    //Update
    ProductDto update(ProductDto productDto, String productId);

    //Delete
    void delete(String productId);

    //Get Single
    ProductDto get(String productId);

    //Get All
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Get All : Live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Search Product
    PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);

    //Create Product with Category
    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    //Update Category of Product
    ProductDto updateCategory(String productId, String categoryId);

    //Returning Product with this category
    PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);

    //Other Methods
}
