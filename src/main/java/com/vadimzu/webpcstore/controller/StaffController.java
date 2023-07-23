/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.controller;

import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.exception.DataAccessException;
import com.vadimzu.webpcstore.exception.ResourceAlreadyExistException;
import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // create new staff
    @PostMapping("/staffs")
    public ResponseEntity registration(@RequestBody StaffEntity staff) {
        try {
            // delegate saving entity to StaffService
            staffService.registration(staff);
            return ResponseEntity.ok("Staff's saved succesfully");
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody StaffEntity staff) {

        // TODO... login creates token and send it back to React
        // ...token...
        try {
            // delegate saving entity to StaffService
            return ResponseEntity.ok(staffService.login(staff));
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
}
