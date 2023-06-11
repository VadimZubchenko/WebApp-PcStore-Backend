/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.controller;

import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.exception.CustomerAlreadyExistExeption;
import com.vadimzu.webpcstore.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vadimzubchenko
 */
@RestController
@RequestMapping("/staff")
public class StaffController {

@Autowired
    private StaffService staffService;
    
@PostMapping
    public ResponseEntity registration(@RequestBody StaffEntity staff) {
        try {
            // delegate saving entity to StaffService
            staffService.registration(staff);
            return ResponseEntity.ok("Staff's saved succesfully");
        } catch (CustomerAlreadyExistExeption e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }

    }

@GetMapping
    public ResponseEntity getOneStaff(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(staffService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }
    }
}
