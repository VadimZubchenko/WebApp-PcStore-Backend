/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

import com.vadimzu.webpcstore.entity.StaffEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author vadimzubchenko
 */
public final class JwtStaffFactory {

    public JwtStaffFactory() {
    }

    public static JwtStaff create(StaffEntity staff) {

        return new JwtStaff(
                staff.getStaffID(),
                staff.getStaffName(),
                staff.getLogin(),
                staff.getPassword(),
                //This method provides a convenient way to create a fixed-size list
                Arrays.asList(new SimpleGrantedAuthority(staff.getRole())),
                //grantedAuthorities(staff.getRole()),
                true
        );

    }

//    private static List<GrantedAuthority> grantedAuthorities(String staffRole) {
//        ArrayList<GrantedAuthority> list = new ArrayList<>();
//        list.add(new SimpleGrantedAuthority(staffRole));
//
//        return list;
//    }
}
