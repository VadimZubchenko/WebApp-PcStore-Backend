/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class LogoutService implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest req,
            HttpServletResponse resp,
            Authentication authentication
    ) {

        SecurityContextHolder.clearContext();
        System.out.println("SecurityContextHolder is after logout: " + SecurityContextHolder.getContext());
    }

}
