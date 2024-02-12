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
import com.vadimzu.webpcstore.security.dtos.JwtRequest;
import com.vadimzu.webpcstore.security.dtos.JwtResponse;
import com.vadimzu.webpcstore.security.dtos.RegistrationStaffDto;
import com.vadimzu.webpcstore.security.jwt.JwtService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

     @Autowired
    private StaffRepo staffRepo;

    public StaffEntity registration(RegistrationStaffDto reg) throws ResourceAlreadyExistException, DataAccessException {

        // check if the entered login and password is empty   
        if (reg.getStaffName().isEmpty() || reg.getLogin().isEmpty()) {
            // exception message goes with response body via StaffController 
            throw new DataAccessException("Name and Login can't be empty");
        }
        // check if the entered passwords is empty   
        if (reg.getConfirmPassword().isEmpty() || reg.getPassword().isEmpty()) {
            throw new DataAccessException("Password and Confirm password can't be empty");
        }
        if (reg.getLogin().isEmpty() || reg.getPassword().isEmpty()) {
            System.out.println("Please provide name and password");
            throw new DataAccessException("Login and password can't be empty");
        }
        //set temporally confirmPassword
        //reg.setConfirmPassword(reg.getPassword());
        if (!reg.getPassword().equals(reg.getConfirmPassword())) {
            throw new DataAccessException("Passwords are not the same");
        }

        // check if login and password are long enough
        if (reg.getLogin().length() < 4 || reg.getPassword().length() < 8) {
            throw new DataAccessException("Please provide login and password long enough");

        }
        if (staffRepo.findByLogin(reg.getLogin()) != null) {
            throw new ResourceAlreadyExistException("A staff with same name already exists");
        }
        //Create new staff 
        StaffEntity staff = new StaffEntity();
        staff.setStaffName(reg.getStaffName());
        staff.setLogin(reg.getLogin());
        staff.setPassword(passwordEncoder.encode(reg.getPassword()));// decode password before persisting into table.
        staff.setRole("seller");
        
        StaffEntity newStaff = staffRepo.save(staff);//persisting into DB
        
        return newStaff;
    }

    public JwtResponse login(JwtRequest authRequest) throws ResourceNotFoundException {
        try {
            String staffLogin = authRequest.getLogin();
            String password = authRequest.getPassword();

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
                throw new ResourceNotFoundException("Staff with login: " + authRequest.getLogin() + " has NOT been found in DB");
            }

            String token = jwtService.generateToken(staffEntity);

            return new JwtResponse(staffLogin, token);

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
