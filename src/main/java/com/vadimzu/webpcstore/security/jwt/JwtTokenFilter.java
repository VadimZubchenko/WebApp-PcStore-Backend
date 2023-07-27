/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author vadimzubchenko
 */

public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Filter checks every request coming from client
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Fetch token, if it exist, from request via JwtTokenProvider
        String staffLogin = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        //check validation time till present time of fetched token via JwtTokenProvider
        if (staffLogin != null && SecurityContextHolder.getContext().getAuthentication() == null) {
             
            UsernamePasswordAuthenticationToken authToken = jwtTokenProvider.getAuthentication(staffLogin);
            
            if (authToken != null) {
                //the token gets generated, and the application will hold the token, 
                // which will be stored in the Security Context interface.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //here if there's no token yet or it's life time is expired 
        chain.doFilter(request, response);
    }
    

}
