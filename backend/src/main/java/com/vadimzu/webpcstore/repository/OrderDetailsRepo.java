/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.repository;

import com.vadimzu.webpcstore.entity.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vadimzubchenko
 */
@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetailsEntity, Long>{
    
}
