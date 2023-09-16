/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.PartEntity;
import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.repository.PartRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class PartService {

    @Autowired
    private PartRepo partRepo;

    public List<PartEntity> getAll() throws ResourceNotFoundException {
        List<PartEntity> parts = partRepo.findAll();
        if (parts == null) {
            throw new ResourceNotFoundException("There are no registered parts in DB ");
        }
        return parts;
    }

}
