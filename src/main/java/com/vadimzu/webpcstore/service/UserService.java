/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.UserEntity;
import com.vadimzu.webpcstore.exception.UserAlreadyExistExeption;
import com.vadimzu.webpcstore.exception.UserNotFoundException;
import com.vadimzu.webpcstore.model.User;
import com.vadimzu.webpcstore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

    public User getOne(Long id) throws UserNotFoundException {
        UserEntity user = userRepo.findById(id).get();
        if (user == null) {
            throw new UserNotFoundException("User with name is not found");

        }
        return User.toModel(user);
    }

    public User deleteUser(Long id) {
       UserEntity user = userRepo.findById(id).get();
       userRepo.deleteById(id);

        return User.toModel(user);

    }

}
