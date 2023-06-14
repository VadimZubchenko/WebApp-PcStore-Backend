/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.exception.ResourceAlreadyExistExeption;
import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.model.Staff;
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

public StaffEntity registration(StaffEntity staff) throws ResourceAlreadyExistExeption {
        
        if (staffRepo.findByStaffName(staff.getStaffName()) != null) {
            throw new ResourceAlreadyExistExeption("A staff with same name already exists");
        }

        return staffRepo.save(staff);
    }

public Staff getOne(Long id) throws ResourceNotFoundException {
        StaffEntity staff = staffRepo.findById(id).get();
        if (staff == null) {
            throw new ResourceNotFoundException("A staff with name is not found");

        }
        return Staff.toModel(staff);
    }

    
}
