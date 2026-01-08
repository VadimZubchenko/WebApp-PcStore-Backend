/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.entity;

import java.util.List;
import javax.persistence.*;

/**
 *
 * @author vadimzubchenko
 */
@Entity
@Table(name = "staffs")
//anotation @Json... below handles OneToMany/ManyToOne infinite in Json files, 
//if it goes straing from Entity without Model 
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "staffID")
public class StaffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffID;
    private String staffName;
    private String role;
    private String login;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staff")
    private List<OrderEntity> orders;

    public StaffEntity() {
    }

    public Long getStaffID() {
        return staffID;
    }

    public void setStaffID(Long staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

}
