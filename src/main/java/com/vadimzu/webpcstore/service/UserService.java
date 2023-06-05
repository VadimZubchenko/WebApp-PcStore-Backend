/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.UserEntity;
import com.vadimzu.webpcstore.exception.UserAlreadyExistExeption;
import com.vadimzu.webpcstore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class UserService {

// inject repository for calling entity from DB
    @Autowired
    private UserRepo userRepo;

    public UserEntity registration(UserEntity user) throws UserAlreadyExistExeption {
        // check if the same username exists in DB
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistExeption("A user with same name already exists");
        }

        return userRepo.save(user);
    }

}
