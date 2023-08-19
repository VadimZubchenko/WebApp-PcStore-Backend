/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.config;

import com.vadimzu.webpcstore.service.StaffService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {

    private final StaffService staffService;

    @Override
    public void logout(HttpServletRequest req,
            HttpServletResponse resp,
            Authentication authentication
    ) {
        final String bearerToken = req.getHeader("token");
        final String jwt;
        
        Object storedToken;
        if (bearerToken == null && !bearerToken.startsWith("")) {
            return;
        }
        jwt = bearerToken.substring(0);
        
        storedToken = staffService.getResponse().remove(jwt);
        log.info("LOGOUT: Stored token has been removed value: " + storedToken);
        
        SecurityContextHolder.clearContext();
        log.info("LOGOUT: Secured context has been cleared value: " + SecurityContextHolder.getContext().getAuthentication());
        
    }

}
