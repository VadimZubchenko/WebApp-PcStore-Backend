/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vadimzu.webpcstore.repository;

import com.vadimzu.webpcstore.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author vadimzubchenko
 */
// interface which execute CRUD operations using DB 
public interface UserRepo extends CrudRepository<UserEntity, Long>{
    UserEntity findByUsername(String username);
}
