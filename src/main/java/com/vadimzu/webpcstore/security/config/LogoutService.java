/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.config;

import com.vadimzu.webpcstore.service.StaffService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
        if (bearerToken == null && !bearerToken.startsWith("Bearer_")) {
            return;
        }
        jwt = bearerToken.substring(7);
        storedToken = staffService.getResponse().remove(jwt);
        SecurityContextHolder.clearContext();
    }

}
