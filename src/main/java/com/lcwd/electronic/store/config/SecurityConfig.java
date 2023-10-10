package com.lcwd.electronic.store.config;

import com.lcwd.electronic.store.security.JWTAuthenticationEntryPoint;
import com.lcwd.electronic.store.security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    /*
    Here, we are using InMemoryUserDetailsManager to store username and password for security this is for temporary,
    we are not storing any data in database.
     */
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        //Create User
//        UserDetails normalUser = User.builder()
//                .username("Ankit")
//                .password(passwordEncoder().encode("ankit"))
//                .roles("NORMAL").build();
//
//        UserDetails adminUser = User.builder()
//                .username("Durgesh")
//                .password(passwordEncoder().encode("durgesh"))
//                .roles("ADMIN").build();
//
//        //UserDetailsService is an interface, to create an object of this interface we will do through
//        //implementation class - InMemoryUserDetailsManager
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // a lot of deprecated methods
//        http.authorizeHttpRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("login.html")
//                .loginProcessingUrl("/process-url")
//                .defaultSuccessUrl("/dashboard")
//                .failureUrl("error")
//                .and()
//                .logout()
//                .logoutUrl("/logout");

//-------------------------- For now we are not implementing this -------------------------
//        http
//                .authorizeHttpRequests(requests -> requests
//                        //.requestMatchers(HttpMethod.GET).hasAnyRole("NORMAL", "ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("login.html")
//                        .loginProcessingUrl("/process-url")
//                        .defaultSuccessUrl("/dashboard")
//                        .failureUrl("error")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .permitAll());

//        //Basic Authentication
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults());

        //JWT Token
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(entryPoint -> entryPoint.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

}
