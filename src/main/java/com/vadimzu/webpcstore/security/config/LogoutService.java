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
        final String bearerToken = req.getHeader("Token");
        final String jwt;
        Object storedToken;
        if (bearerToken == null && !bearerToken.startsWith("")) {
            return;
        }
        jwt = bearerToken.substring(0);
        //storedToken = staffService.getResponse().remove(jwt);
        System.out.println("Responce after logout: " + staffService.getResponse());
        SecurityContextHolder.clearContext();
    }

}
