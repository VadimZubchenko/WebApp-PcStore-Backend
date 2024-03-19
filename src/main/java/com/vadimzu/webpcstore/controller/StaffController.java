/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.controller;

import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.exception.DataAccessException;
import com.vadimzu.webpcstore.exception.ResourceAlreadyExistException;
import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.security.dtos.JwtRequest;
import com.vadimzu.webpcstore.security.dtos.RegistrationStaffDto;
import com.vadimzu.webpcstore.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vadimzubchenko
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class StaffController {

    @Autowired
    private StaffService staffService;

    // pay attention! to <?> makes possible to take any kind of response type
    // without <?> get time expired error 
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationStaffDto registrationStaffDto) {
        try {
            // delegate saving entity to StaffService
            staffService.registration(registrationStaffDto);
            return ResponseEntity.status(201).body("Registration has been completed successfully.");
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest authRequest) {

        try {
            return ResponseEntity.ok(staffService.login(authRequest));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }

    }

    // Single staff
    @GetMapping("/staffs/{id}")
    public ResponseEntity getOneStaff(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(staffService.getOne(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }
    }

    // get all staffs
    @GetMapping("/staffs")
    public ResponseEntity getAllStaffs() {
        try {
            return ResponseEntity.ok(staffService.getAll());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }
    }
}
