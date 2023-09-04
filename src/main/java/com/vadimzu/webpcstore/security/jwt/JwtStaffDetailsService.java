/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

import lombok.extern.slf4j.Slf4j;
import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author vadimzubchenko
 */
@Service("jwtStaffDetailsService")
@Slf4j
public class JwtStaffDetailsService implements UserDetailsService {

    private final StaffRepo staffRepo;

    @Autowired
    public JwtStaffDetailsService(StaffRepo staffRepo) {
        this.staffRepo = staffRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String staffLogin) throws UsernameNotFoundException {
        // create staff object with passed login name from client side 
        StaffEntity staff = staffRepo.findByLogin(staffLogin);

        if (staff == null) {
            throw new UsernameNotFoundException("Staff with login " + staffLogin + " not found");
        }
        log.info("IN loadUserByUsername jwtStaff with LOGIN: {} successfully created", staffLogin);

        return JwtStaff.fromUser(staff);
    }

}
