/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.dtos;

import lombok.Data;

/**
 *
 * @author vadimzubchenko
 */
@Data   
public class RegistrationStaffDto {
    private String staffName;
    private String login;
    private String password;
    private String confirmPassword;

}
