/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.controller;

import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.service.OrderService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author vadimzubchenko
 */
@RestController
@RequestMapping("/orders")

public class OrderController {

    @Autowired
    private OrderService orderService;

    //the common endpoint is presented above, so no need here
    @PostMapping
    public ResponseEntity createOrder(@RequestBody Map<String, Object> body) {
        try {
            
            orderService.createOrder(body);
            return ResponseEntity.status(201).body("Order has been created");

        } catch (Exception e) {
            return ResponseEntity.ok("Request didn't pass throw");
        }

    }
    //the common endpoint is presented above, so there's no need here
    @GetMapping
    public ResponseEntity getAllOrders() {
        try {
           
            return ResponseEntity.ok(orderService.getAll_Orders());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getOne_Order(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.getOne_Order(id));
        } catch (ResourceNotFoundException e) {
            // return exception message if there's no order with requested id∫
            return ResponseEntity.badRequest().body(e.getMessage());

        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }
    
    }

}
