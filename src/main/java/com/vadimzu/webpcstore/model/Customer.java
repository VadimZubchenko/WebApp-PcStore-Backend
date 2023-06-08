/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.model;

import com.vadimzu.webpcstore.entity.CustomerEntity;

/**
 *
 * @author vadimzubchenko
 */
public class Customer {

    private long id;
    private String customerName;

    public static Customer toModel(CustomerEntity customer) {
        Customer model = new Customer();
        model.setId(customer.getCustomerID());
        model.setCustomerName(customer.getCustomerName());

        return model;
    }

    public Customer() {
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
