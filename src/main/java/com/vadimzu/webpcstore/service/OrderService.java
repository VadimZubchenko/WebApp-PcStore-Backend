/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import com.vadimzu.webpcstore.entity.OrderEntity;
import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.repository.OrderRepo;
import com.vadimzu.webpcstore.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vadimzu.webpcstore.repository.CustomerRepo;
import java.util.Date;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private StaffRepo staffRepo;
    private Date orderDate;

    public OrderEntity createOrder(OrderEntity order, Long customerId, Long staffId) {
//find customer from repo by id         
        CustomerEntity customer = customerRepo.findById(customerId).get();
//find staff from repo by id         
        StaffEntity staff = staffRepo.findById(staffId).get();
//relate the order with customer              
        order.setCustomer(customer);
        order.setStaff(staff);
        orderDate = new Date();
        order.setOrderDate(orderDate);
//save new order into repo        

        return orderRepo.save(order);
    }
}