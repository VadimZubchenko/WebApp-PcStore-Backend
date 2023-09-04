/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vadimzu.webpcstore.entity.StaffEntity;
import java.util.Arrays;
import java.util.Collection;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author vadimzubchenko
 */
@Data
public class JwtStaff implements UserDetails {

    private final Long staffID;
    private final String staffName;
    private final String login;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public JwtStaff(
            Long staffID,
            String staffName,
            String login,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled
            
    ) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    @JsonIgnore
    public Long getStaffID() {
        return staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    //create Spring Security UserDetails type user 
    public static UserDetails fromUser(StaffEntity staff){
        return new org.springframework.security.core.userdetails.User(
                staff.getLogin(),
                staff.getPassword(),
                //This method provides a convenient way to create a fixed-size list
                Arrays.asList(new SimpleGrantedAuthority(staff.getRole()))
                //grantedAuthorities(staff.getRole()),
                
        );
    }

}
