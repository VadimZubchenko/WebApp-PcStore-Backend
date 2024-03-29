/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.PartEntity;
import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.model.Part;
import com.vadimzu.webpcstore.repository.PartRepo;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
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

    public List<Part> getAll() throws ResourceNotFoundException {
        List<PartEntity> parts = partRepo.findAll();
        
        // create new array for model of part
        List<Part> modelParts = new ArrayList<>();
        
        if (parts == null) {
            throw new ResourceNotFoundException("There are no registered parts in DB ");
        }
        //change all parts into toModel
        for(PartEntity part : parts){
            modelParts.add(Part.toModel(part));
            
        }
        return modelParts;
    }

    public PartEntity addPart(PartEntity part) {
        return partRepo.save(part);
        
    }

}
