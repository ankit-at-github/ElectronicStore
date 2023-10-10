package com.lcwd.electronic.store.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JWTHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Authorization
        //Checking if header is present
        String requestHeader = request.getHeader("Authorization");
        //Bearer 234435dvdfvsdfg
        logger.info("Header : {}", requestHeader);
        String username = null;
        String token = null;

        if(requestHeader != null && requestHeader.startsWith("Bearer"))
        {
            //Bearer -> length = 7
            token = requestHeader.substring(7);
            //Now we have to get username from token
            try{
               username = jwtHelper.getUsernameFromToken(token);

            }catch (IllegalArgumentException e){
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            }catch (ExpiredJwtException e){
                logger.info("Given JWT Token is expired !!");
                e.printStackTrace();
            }catch (MalformedJwtException e){
                logger.info("Some changes has been done in token !! Invalid Token");
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            logger.info("Invalid Header Value !!");
        }

        //username has value, someone is not present in securitycontext (not already logged in)
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //fetch user details from username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean validatedToken = jwtHelper.validateToken(token, userDetails);
            if(validatedToken){
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                logger.info("Validation Fails !!");
            }
        }

        filterChain.doFilter(request, response);

    }
}
