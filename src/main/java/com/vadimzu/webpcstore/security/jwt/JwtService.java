/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    public UserDetails getUserDetails(String staffLogin) {
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

    // I. Check if coming request includes the jwt with name "Token" 
    public String resolveToken(HttpServletRequest req) {
        final String jwt = req.getHeader("Token");
        if (jwt != null && jwt.startsWith("")) {
            //return extracted token
            return jwt.substring(0, jwt.length());
        }
        return null;
    }

    public String extractLogin(String jwt) {
        // extract a subject with login from token, 
        return extractClaim(jwt, Claims::getSubject);
    }

    // method to extract a certain data of claim from token
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

    }

    // II. Check if received with jwt staffLogin and DB's login are same 
    //     && received the jwt doesn't expired
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String staffLogin = extractLogin(jwt);
        return (staffLogin.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }

    private Date extractTokenExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    // Check if received token expired
    private boolean isTokenExpired(String jwt) {
        return extractTokenExpiration(jwt).before(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
