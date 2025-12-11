/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.repository.OrderDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class OrderDetailService {
    
    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    public void deletePart(Long id) throws ResourceNotFoundException{
        if (!orderDetailsRepo.findById(id).isPresent()) {
            throw new ResourceNotFoundException("There's no order with ID: " + id);
        }
        // Find a order entity with the id
        
        orderDetailsRepo.deleteById(id);

        
    }
    
}
