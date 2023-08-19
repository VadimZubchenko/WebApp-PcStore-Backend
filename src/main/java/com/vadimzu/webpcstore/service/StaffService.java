/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.exception.DataAccessException;
import com.vadimzu.webpcstore.exception.ResourceAlreadyExistException;
import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.model.Staff;
import com.vadimzu.webpcstore.repository.StaffRepo;
import com.vadimzu.webpcstore.security.jwt.JwtTokenProvider;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class StaffService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<Object, Object> response;

    public Map<Object, Object> getResponse() {
        return response;
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private StaffRepo staffRepo;

    public StaffEntity registration(StaffEntity staff) throws ResourceAlreadyExistException, DataAccessException {

        // check if the entered login and password is empty   
        if (staff.getLogin().isEmpty() || staff.getPassword().isEmpty()) {
            System.out.println("Please provide name and password");
            throw new DataAccessException("Login and password can't be empty");
        }

        // check if login and password are long enough
        if (staff.getLogin().length() < 4 || staff.getPassword().length() < 8) {
            throw new DataAccessException("Please provide login and password long enough");

        }
        if (staffRepo.findByLogin(staff.getLogin()) != null) {
            throw new ResourceAlreadyExistException("A staff with same name already exists");
        }
        // decode password before persist into table.
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        // set for all role 'seller'
        staff.setRole("seller");

        return staffRepo.save(staff);
    }

    public Map<Object, Object> login(StaffEntity staff) throws ResourceNotFoundException {
        try {
            String staffLogin = staff.getLogin();
            String password = staff.getPassword();

            // authentication request, designed for simple presentation of a username and password            
            //this makes all authentication job itself as a checking of login & password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            staffLogin,
                            password
                    )
            );
            // find staffEntity in db via it's login
            StaffEntity staffEntity = staffRepo.findByLogin(staffLogin);

            if (staffEntity == null) {
                throw new ResourceNotFoundException("Staff with login: " + staff.getLogin() + " has NOT been found in DB");
            }

            String token = jwtService.generateToken(staffEntity);

            response = new HashMap<>();
            response.put("staffLogin", staffLogin);
            response.put("token", token);

            System.out.println("StaffService Responce :" + response);

            return response;

        } catch (Exception e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }

    public Staff getOne(Long id) throws ResourceNotFoundException {
        StaffEntity staffEntity = staffRepo.findById(id).get();
        if (staffEntity == null) {
            throw new ResourceNotFoundException("A staff with name is not found");

        }
        return Staff.toModel(staffEntity);
    }

}
