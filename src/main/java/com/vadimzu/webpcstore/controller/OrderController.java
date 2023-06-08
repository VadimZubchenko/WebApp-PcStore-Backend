/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.controller;

import com.vadimzu.webpcstore.entity.OrderEntity;
import com.vadimzu.webpcstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vadimzubchenko
 */
@RestController
@RequestMapping("/orders")

public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderEntity order, @RequestParam Long customerId, @RequestParam Long staffId) {
        try {

            return ResponseEntity.ok(orderService.createOrder(order, customerId, staffId));

        } catch (Exception e) {
            return ResponseEntity.ok("Request didn't pass throw");
        }

    }

}
