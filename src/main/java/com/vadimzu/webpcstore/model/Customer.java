/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.model;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author vadimzubchenko
 */

// Model creats version of customer for representation on Client side
public class Customer {

    private long customerID;
    private String customerName;
    private String address;
    private String email;
    private List<Order> orders;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //converter, which modifies CustomerEntity into model with needed properties for Client-side
    public static Customer toModel(CustomerEntity customerEntity) {
        Customer model = new Customer();
        
        model.setCustomerID(customerEntity.getCustomerID());
        model.setCustomerName(customerEntity.getCustomerName());
        model.setAddress(customerEntity.getAddress());
        model.setEmail(customerEntity.getEmail());
        model.setOrders(customerEntity.getOrders().stream().map(Order::toModel).collect(Collectors.toList()));

        return model;
    }

    public Customer() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
