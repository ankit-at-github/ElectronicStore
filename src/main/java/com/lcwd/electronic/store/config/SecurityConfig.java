package com.lcwd.electronic.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){

        //Create User
        UserDetails normalUser = User.builder()
                .username("Ankit")
                .password(passwordEncoder().encode("ankit"))
                .roles("NORMAL").build();

        UserDetails adminUser = User.builder()
                .username("Durgesh")
                .password(passwordEncoder().encode("durgesh"))
                .roles("ADMIN").build();

        //UserDetailsService is an interface, to create an object of this interface we will do through
        //implementation class - InMemoryUserDetailsManager
        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
