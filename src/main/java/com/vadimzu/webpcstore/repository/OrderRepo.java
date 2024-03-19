/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vadimzu.webpcstore.repository;

import com.vadimzu.webpcstore.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author vadimzubchenko
 */
public interface OrderRepo extends JpaRepository<OrderEntity, Long>{
    
}
