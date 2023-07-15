/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.model;
import com.vadimzu.webpcstore.entity.OrderEntity;
import java.util.Date;

/**
 *
 * @author vadimzubchenko
 */

// Model creats Order version for representation on Client side
public class Order {

    private Long orderID;
    private Date orderDate;
    private Double totalPrice;

    public static Order toModel(OrderEntity orderEntity) {

        Order model = new Order();

        model.setOrderID(orderEntity.getOrderID());
        model.setOrderDate(orderEntity.getOrderDate());
        model.setTotalPrice(orderEntity.getTotalPrice());

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
