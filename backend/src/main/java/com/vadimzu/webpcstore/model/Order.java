/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.model;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import com.vadimzu.webpcstore.entity.OrderDetailsEntity;
import com.vadimzu.webpcstore.entity.OrderEntity;
import com.vadimzu.webpcstore.entity.StaffEntity;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author vadimzubchenko
 */
// Model creats Order version for representation on Client side
public class Order {

    private CustomerEntity customer;
    private StaffEntity staff;
    private Long orderID;
    private String customerName;
    private Long customerID;
    private Date orderDate;
    private Double totalPrice;
    private List<OrderDetails> orderDetails;
    private String staffName;

    public static Order toModel(OrderEntity orderEntity) {

        Order model = new Order();

        model.setOrderID(orderEntity.getOrderID());
        model.setCustomerID(orderEntity.getCustomer().getCustomerID());
        model.setCustomerName(orderEntity.getCustomer().getCustomerName());
        model.setOrderDate(orderEntity.getOrderDate());
        model.setTotalPrice(orderEntity.getTotalPrice());
        model.setStaffName(orderEntity.getStaff().getStaffName());
        model.setOrderDetails(orderEntity.getOrderDetails().stream().map(OrderDetails::toModel).collect(Collectors.toList()));
        

        return model;

    }

    public Order() {
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

   
    
    

}
