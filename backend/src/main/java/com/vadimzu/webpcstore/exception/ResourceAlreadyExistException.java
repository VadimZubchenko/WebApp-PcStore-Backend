/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.exception;

import org.springframework.web.servlet.function.ServerResponse;

/**
 *
 * @author vadimzubchenko
 */
public class ResourceAlreadyExistException extends Exception{

    public ResourceAlreadyExistException(String message) {
        super(message);
        ServerResponse.status(409);
    }
    
}
