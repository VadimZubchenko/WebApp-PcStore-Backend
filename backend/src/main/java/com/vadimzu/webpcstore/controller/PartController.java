package com.vadimzu.webpcstore.controller;

import com.vadimzu.webpcstore.entity.PartEntity;
import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author vadimzubchenko
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/parts")
public class PartController {

    @Autowired
    private PartService partService;

    //get all parts 
    @GetMapping
    public ResponseEntity getAllParts() {
        try {
            return ResponseEntity.ok(partService.getAll());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }
    }

    //add new part
    @PostMapping
    public ResponseEntity<?> addParts(@RequestBody PartEntity part) {
        try {
            partService.addPart(part);
            return ResponseEntity.status(201).body("Part has been added.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }

    }
}
