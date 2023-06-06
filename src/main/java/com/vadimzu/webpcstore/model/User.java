/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.model;

import com.vadimzu.webpcstore.entity.UserEntity;
import org.aspectj.weaver.Advice;

/**
 *
 * @author vadimzubchenko
 */
public class User {

    private long id;
    private String userName;

    public static User toModel(UserEntity user) {
        User model = new User();
        model.setId(user.getId());
        model.setUserName(user.getUsername());

        return model;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
