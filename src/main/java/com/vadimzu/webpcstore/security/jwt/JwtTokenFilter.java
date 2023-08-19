/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author vadimzubchenko
 */
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Filter checks every request coming from client
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Fetch token, if it exist, from request via JwtTokenProvider
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        //check validation time till present time of fetched token via JwtTokenProvider
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // //the authentication(user data/role) for saving in Spring Security context
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            
            if (authentication != null) {
                //the authentication(user data/role) gets generated, 
                // which will be stored in the Security Context interface for 
                // this request permition by Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //here if there's no token yet or it's life time is expired 
        chain.doFilter(request, response);
    }
    

}
