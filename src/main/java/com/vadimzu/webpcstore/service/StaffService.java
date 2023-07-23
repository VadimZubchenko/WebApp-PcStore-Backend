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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class StaffService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    public Map<Object, Object> getResponse() {
        return response;
    }

    Map<Object, Object> response;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
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

        return staffRepo.save(staff);
    }

    public Map<Object, Object> login(StaffEntity staff) throws ResourceNotFoundException {

        try {
            String staffLogin = staff.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(staffLogin, staff.getPassword()));
            StaffEntity staffEntity = staffRepo.findByLogin(staffLogin);

            if (staffLogin == null) {
                System.out.println("Staff " + staff.getLogin() + " has NOT been found in DB");
                throw new ResourceNotFoundException("Staff with login: " + staff.getLogin() + " has NOT been found in DB");
            }

            String token = jwtTokenProvider.createToken(staffLogin, staffEntity.getRole());

            response = new HashMap<>();
            response.put("staffLogin", staffLogin);
            response.put("token", token);

            System.out.println("Responce:" + response);

            return response;

        } catch (Exception e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }

    public Boolean logout(String token) throws ResourceNotFoundException {
        try {

            if (token == null) {

                throw new ResourceNotFoundException("Token not found");

            }
            System.out.println("Deleted token: " + token);
            
            response.remove(token);
            
            System.out.println("Responce:" + response);
            
            return true;
        } catch (Exception e) {

            throw new BadCredentialsException("Invalid token" + e);
        }
    }
    //     first version without token 
    //        // check if the entered login and password is empty   
    //        if (staff.getLogin().isEmpty() || staff.getPassword().isEmpty()) {
    //            System.out.println("Please provide name and password");
    //            throw new ResourceNotFoundException("Please provide name and password");
    //        }
    //
    //        // check is there the registered staff in db
    //        StaffEntity staffEntity = staffRepo.findByLogin(staff.getLogin());
    //        if (staffEntity == null) {
    //            System.out.println("Staff " + staff.getLogin() + " has NOT been found in DB");
    //            throw new ResourceNotFoundException("Staff with login: " + staff.getLogin() + " has NOT been found in DB");
    //        } else {
    //            System.out.println("User " + staff.getLogin() + " has been found in DB");
    //        }
    //
    //        // check entered password match to registered password
    //        String rawPassword = staff.getPassword();
    //        String encodedPassword = staffEntity.getPassword();
    //        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
    //            System.out.println("Your name and password match");
    //            return true;
    //            
    //        } else {
    //            System.out.println("Please check your password.");
    //            throw new ResourceNotFoundException("Please check your password.");
    //        }
    //        
    //    }
    //    

    public Staff getOne(Long id) throws ResourceNotFoundException {
        StaffEntity staffEntity = staffRepo.findById(id).get();
        if (staffEntity == null) {
            throw new ResourceNotFoundException("A staff with name is not found");

        }
        return Staff.toModel(staffEntity);
    }

}
