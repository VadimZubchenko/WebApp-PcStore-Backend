/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

import com.vadimzu.webpcstore.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.common.util.impl.Log_$logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author vadimzubchenko
 */
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.token.secretWord}")
    private String secretWord;
    @Value("${jwt.token.expired}")
    private long jwtLifetime;

    @Autowired
    private UserDetailsService userDetailsService;

    String staffLogin = null;
    String jwt = null;
    

    // encoder for using for changing password to hash
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    // encoder for changing secretWord to hash 
    @PostConstruct
    protected void init() {
        secretWord = Base64.getEncoder().encodeToString(secretWord.getBytes());
    }

    public String createToken(String staffLogin, String role) {

        //create token  PAYLOAD:DATA with sub and role  
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(staffLogin);
        claims.put("roles", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtLifetime);

        return Jwts.builder()//
                .setClaims(claims)//
                .setSubject(role)
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretWord)//
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String staffLogin) {
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(getRole(jwt)));
        //create userDetails with 
        //UserDetails userDetails = this.userDetailsService.loadUserByUsername(getStaffLogin(token));
        return new UsernamePasswordAuthenticationToken(
                staffLogin,
                null,
                list
                
        );
    }

    public String resolveToken(HttpServletRequest req) {

        String bearerToken = req.getHeader("Token");
        if (bearerToken != null && bearerToken.startsWith("")) {
            jwt = bearerToken.substring(0);
            try {
                staffLogin = getStaffLogin(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("Time of token has been expired");
            } catch (SignatureException e) {
                log.debug("Singing is not correct");
            }
        }

        return staffLogin;
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretWord)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getRole(String token) {
        return getAllClaimsFromToken(token).get("roles", String.class);
    }

    public String getStaffLogin(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

}

//    //check validation time of token
//    public boolean validateToken(String token) {
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
//
//            if (claims.getBody().getExpiration().before(new Date())) {
//                return false;
//            }
//
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            throw new JwtAuthenticationException("JWT token is expired or invalid");
//        }
//    }
