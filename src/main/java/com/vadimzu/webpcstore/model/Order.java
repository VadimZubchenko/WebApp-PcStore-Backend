/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.model;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import com.vadimzu.webpcstore.entity.OrderEntity;
import java.util.Date;

/**
 *
 * @author vadimzubchenko
 */
public class Order {

private Long orderID;
private Date orderDate;
private Double totalPrice;

public static Order toModel(OrderEntity order){

Order model = new Order();

model.setOrderID(order.getOrderID());
model.setOrderDate(order.getOrderDate());
model.setTotalPrice(order.getTotalPrice());

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


    
}
