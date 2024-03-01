/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vadimzu.webpcstore.repository;

import com.vadimzu.webpcstore.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author vadimzubchenko
 */
public interface StaffRepo extends JpaRepository<StaffEntity, Long> {

    StaffEntity findByStaffName(String staffName);
    StaffEntity findByLogin(String staffLogin);

}
