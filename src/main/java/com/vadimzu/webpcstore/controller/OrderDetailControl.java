package com.vadimzu.webpcstore.controller;

import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vadimzubchenko
 */
@RestController
@RequestMapping("/order_details")
public class OrderDetailControl {
    @Autowired
    private OrderDetailService orderDetailService;
    
    @DeleteMapping("/{id}")
    public ResponseEntity deletePart(@PathVariable Long id) {
        try {
            orderDetailService.deletePart(id);
            return ResponseEntity.ok("The order with id: " + id + " has been deleted");

        } catch (ResourceNotFoundException e) {
            // return exception message if there's no order with the id
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw " + e.getMessage());
        }
    }
    
}
