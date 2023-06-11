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
public class Customer {

    private long id;
    private String customerName;
    private List<Order> orders;


    //converter, which modifies CustomerEntity into model with needed properties for Client-side
    public static Customer toModel(CustomerEntity customerEntity) {
        Customer model;
        model = new Customer();
        model.setId(customerEntity.getCustomerID());
        model.setCustomerName(customerEntity.getCustomerName());
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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
