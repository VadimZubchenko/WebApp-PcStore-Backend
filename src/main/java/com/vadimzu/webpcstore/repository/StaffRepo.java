/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vadimzu.webpcstore.repository;

import com.vadimzu.webpcstore.entity.StaffEntity;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author vadimzubchenko
 */
public interface StaffRepo extends CrudRepository<StaffEntity, Long> {

    StaffEntity findByStaffName(String staffName);

}
