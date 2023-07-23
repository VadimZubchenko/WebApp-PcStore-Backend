/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security;

import lombok.extern.slf4j.Slf4j;
import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.repository.StaffRepo;
import com.vadimzu.webpcstore.security.jwt.JwtStaff;
import com.vadimzu.webpcstore.security.jwt.JwtStaffFactory;
import com.vadimzu.webpcstore.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service
@Slf4j
public class JwtStaffDetailsService implements UserDetailsService {

    public final StaffService staffService;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    public JwtStaffDetailsService(StaffService staffService) {
        this.staffService = staffService;
    }

    @Override
    public UserDetails loadUserByUsername(String staffLogin) throws UsernameNotFoundException {
        // create staff object using coming login name from client side 
        StaffEntity staff = staffRepo.findByLogin(staffLogin);
        
        if (staff == null) {
            throw new UsernameNotFoundException("Staff with login " + staffLogin + " not found");
        }
        // creating jwtStaff via JwtStaffFactory as a userDetails, 
        // which is used for authorization
        JwtStaff jwtStaff = JwtStaffFactory.create(staff);

        log.info("IN loadUserByUsername jwtStaff with LOGIN: {} successfully created", staffLogin);

        return jwtStaff;
    }

}
