/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.exception.CustomerAlreadyExistExeption;
import com.vadimzu.webpcstore.exception.CustomerNotFoundException;
import com.vadimzu.webpcstore.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class StaffService {

@Autowired
private StaffRepo staffRepo;

public StaffEntity registration(StaffEntity staff) throws CustomerAlreadyExistExeption {
        
        if (staffRepo.findByStaffName(staff.getStaffName()) != null) {
            throw new CustomerAlreadyExistExeption("A staff with same name already exists");
        }

        return staffRepo.save(staff);
    }

public StaffEntity getOne(Long id) throws CustomerNotFoundException {
        StaffEntity customer = staffRepo.findById(id).get();
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with name is not found");

        }
        return customer;
    }

    
}
