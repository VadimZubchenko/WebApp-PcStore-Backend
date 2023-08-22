/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author vadimzubchenko
 */
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token; 
}
