package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    //search
    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);

    //Live Products
    Page<Product> findByLiveTrue(Pageable pageable);

    //Other Methods
    //Custom Finder Method
    //Query Methods
}
