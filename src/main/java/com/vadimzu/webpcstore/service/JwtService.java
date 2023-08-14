/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.StaffEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class JwtService {

    @Value("${jwt.token.secretKey}")
    private String secretKey;

    @Value("${jwt.token.expired}")
    private long validityInMillisec;

    @Autowired
    private UserDetailsService userDetailsService;

    
    // encoder for using for changing password to hash
    // which is used by authenticationManager in StaffSErvice
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    
    public UserDetails  getUserDetails(String staffLogin) {
        return userDetailsService.loadUserByUsername(staffLogin);
    }

    public String generateToken(StaffEntity staffEntity) {
        return generateToken(new HashMap<>(), staffEntity);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            StaffEntity staffEntity
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(staffEntity.getLogin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInMillisec))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // I. Check if coming request includes the authToken with name "Token" 
    public String resolveToken(HttpServletRequest req) {
        final String authToken = req.getHeader("Token");
        if (authToken != null && authToken.startsWith("")) {
            //return extracted token
            return authToken.substring(0, authToken.length());
        }
        return null;
    }

    public String extractLogin(String authToken) {
        // extarct some certain claim from token, a subject with login
        return extractClaim(authToken, Claims::getSubject);
    }
    // to extract a certain data of claim from token
    public <T> T extractClaim(String authToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(authToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String authToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(authToken)
                .getBody();

    }

    // II. Check if received with authToken staffLogin and DB's login are same 
    //     && received the authtoken doesn't expired
    public boolean isTokenValid(String authToken, UserDetails userDetails) {
        final String staffLogin = extractLogin(authToken);
        return (staffLogin.equals(userDetails.getUsername())) && !isTokenExpired(authToken);
    }

    private Date extractTokenExpiration(String authToken) {
        return extractClaim(authToken, Claims::getExpiration);
    }

    // Check if received token expired
    private boolean isTokenExpired(String authToken) {
        return extractTokenExpiration(authToken).before(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
