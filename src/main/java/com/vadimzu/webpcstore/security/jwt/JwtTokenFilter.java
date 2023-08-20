/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

import com.vadimzu.webpcstore.service.JwtService;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author vadimzubchenko
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

//    public JwtTokenFilter(JwtService jwtService) {
//        this.jwtService = jwtService;
//    }
    // Filter checks every request coming from client
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Fetch token, if it exist, from request via JwtService
        final String jwt = jwtService.resolveToken((HttpServletRequest) request);
        // if there's no a token in the request, then contunue filterChain
        // for authoritizatioin User and creating the token for him
        if (jwt == null) {
            //if there's no token yet
            //switch to StaffController.login() for auth login/pass and token creating 
            filterChain.doFilter(request, response);
            return;
        }
        // Extract login from the token
        final String staffLogin = jwtService.extractLogin(jwt);

        // Check if staffLogin exctracted from token successfully and the staff isn't authenticated yet in S.Holder before 
        // S.C.Holder == null means the client-side didn't connect yet and to be added into S.Holder
        if (staffLogin != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //create userDetail object with JwtStaffFactory based on staff(in DB) with the same staffLogin
            //using a method loadUserByUsername() to fetch data from DB
            UserDetails userDetails = jwtService.getUserDetails(staffLogin);

            // Check is token's login and DB's login are same and token not expired
//            if (jwtService.isTokenValid(jwt, userDetails)) {
                // the first time the token for an authentication request was represented in login process
                //create the authToken(it not a jwtToken) with jwtStaff(userDetails with login) 
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,//cause we don't have credential(login/password) while a jwtStaff being created 
                        userDetails.getAuthorities()); // get a role 
                // add to token request details 
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // settin authToken into SECURITY HOLDER to giving permistion for this request 
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
//        }
        //here if there's a token life time is expired 
        filterChain.doFilter(request, response);
    }
}
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        //Fetch token, if it exist, from request via JwtTokenProvider
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//        //check validation time till present time of fetched token via JwtTokenProvider
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            //create updated token(auth.) with jwtStaff(userDetails with login, password, roles.. etc)
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            
//            if (authentication != null) {
//                // the new authentication(user Data and role) gets generatednand 
//                // will be stored in the Security Context interface for 
//                // give permition to this request by Spring Security
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//        //here if there's no token yet or it's life time is expired 
//        chain.doFilter(request, response);
//    }

