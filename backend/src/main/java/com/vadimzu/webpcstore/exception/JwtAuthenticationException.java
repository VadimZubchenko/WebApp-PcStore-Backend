/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author vadimzubchenko
 */
public class JwtAuthenticationException extends AuthenticationException{
    
    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
    
    
    
}
