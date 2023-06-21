/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.controller;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import com.vadimzu.webpcstore.exception.ResourceAlreadyExistExeption;
import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
//@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // get all customer
    @GetMapping("/customers")
    public ResponseEntity getAllCustomers() {
        try {
            return ResponseEntity.ok(customerService.getAll());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }
    }

    //Creating new customer and saving recieved from client entity to repository
    @PostMapping("/customers")
    public ResponseEntity registration(@RequestBody CustomerEntity customer) {
        try {
            // delegate saving entity to customerService
            customerService.registration(customer);
            return ResponseEntity.ok("User's saved succesfully");
        } catch (ResourceAlreadyExistExeption e) {
            // return message 'A customer with same....' from CustomerService 
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }

    }

    // Single customer
    @GetMapping("/customers/{id}")
    public ResponseEntity getOneUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(customerService.getOne(id));
        } catch (ResourceNotFoundException e) {
            // return exception message if there's no customer with requested id∫
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            customerService.deleteUser(id);
            return ResponseEntity.ok("The customer with id " + id + " has been deleted");
        } catch (ResourceNotFoundException e) {
            // return exception message if there's no customer with id∫
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Request didn't pass throw");
        }
    }

}
