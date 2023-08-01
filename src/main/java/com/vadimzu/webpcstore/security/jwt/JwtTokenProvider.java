/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author vadimzubchenko
 */

@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

//    @Value("${jwt.token.secretWord}")
//    private String secretWord;
//    @Value("${jwt.token.expired}")
//    private long validityInMillisec;
//
//    //use for fatching DB data for creating JwtStaff object
//    private final UserDetailsService userDetailsService;
//
//    // encoder for using for changing password to hash
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }
//
//    // encoder for changing secretWord to hash 
//    @PostConstruct
//    protected void init() {
//        secretWord = Base64.getEncoder().encodeToString(secretWord.getBytes());
//    }
//
//    public String createToken(String staffLogin, String role) {
//
//        Claims claims = Jwts.claims().setSubject(staffLogin);
//        claims.put("roles", role);
//
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + validityInMillisec);
//
//        return Jwts
//                .builder()//
//                .setClaims(claims)//
//                .setIssuedAt(now)//
//                .setExpiration(validity)//
//                .signWith(SignatureAlgorithm.HS256, secretWord)//
//                .compact();
//    }
//    
//    
//    
//    
//    // III. Check if received authToken includes login represented in DB
//    public Authentication getAuthentication(String authToken) {
//        //fetch login from authToken
//        final String staffLogin = extractLogin(authToken);
//        //create userDetail object with JwtStaffFactory based on staff(in DB) with the same staffLogin
//        //using a method loadUserByUsername() to fetch data from DB
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(staffLogin);
//        // create new jwt-token(login and roles) based on userDetails and it's authorities(roles)
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }
//    // extract the staffLogin from token
//    public String extractLogin(String authToken) {
//        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(authToken).getBody().getSubject();
//    }
//
//
//

}
