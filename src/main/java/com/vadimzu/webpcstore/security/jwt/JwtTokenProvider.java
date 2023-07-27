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
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private long validityInMillisec;

    @Autowired
    private UserDetailsService userDetailsService;

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

        Claims claims = Jwts.claims().setSubject(staffLogin);
        claims.put("roles", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMillisec);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretWord)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        //create userDetails with 
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getStaffLogin(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Token");
        if (bearerToken != null && bearerToken.startsWith("")) {
            return bearerToken.substring(0, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException e){
            log.info("IN token is Expired: ", e);
        } 
        catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is invalid", e);
        }
        return false;
    }

    public String getStaffLogin(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

}
