/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vadimzu.webpcstore.repository;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author vadimzubchenko
 */
// interface which execute CRUD operations using DB 
public interface CustomerRepo extends CrudRepository<CustomerEntity, Long>{
//!!!name of function depends on variable name in class 'findBy***'    
CustomerEntity findByCustomerName(String customerName);
}
