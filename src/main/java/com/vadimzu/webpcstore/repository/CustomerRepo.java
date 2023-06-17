/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vadimzu.webpcstore.repository;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vadimzubchenko
 */
// interface Crud.Repos... which executes CRUD operations using DB 
// later has been changed to JpaRepository for calling findAll() 
@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long>{
//!!!name of function depends on variable name in class 'findBy***'    
CustomerEntity findByCustomerName(String customerName);
}
