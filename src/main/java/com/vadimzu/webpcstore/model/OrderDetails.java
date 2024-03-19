package com.vadimzu.webpcstore.model;

import com.vadimzu.webpcstore.entity.OrderDetailsEntity;
import com.vadimzu.webpcstore.entity.PartEntity;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author vadimzubchenko
 */
public class OrderDetails {

    private Long orderDetailID;
    private Long orderDetailQuantity;
    private Double orderDetailsPrice;
    private PartEntity part;
    private String partName;

    public OrderDetails() {
    }

    public Long getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(Long orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Long getOrderDetailQuantity() {
        return orderDetailQuantity;
    }

    public void setOrderDetailQuantity(Long orderDetailQuantity) {
        this.orderDetailQuantity = orderDetailQuantity;
    }

    public Double getOrderDetailsPrice() {
        return orderDetailsPrice;
    }

    public void setOrderDetailsPrice(Double orderDetailsPrice) {
        this.orderDetailsPrice = orderDetailsPrice;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public static OrderDetails toModel(OrderDetailsEntity orderDetailsEntity) {
        OrderDetails model = new OrderDetails();

        model.setOrderDetailID(orderDetailsEntity.getOrderDetailID());
        model.setOrderDetailQuantity(orderDetailsEntity.getOrderDetailQuantity());
        model.setOrderDetailsPrice(orderDetailsEntity.getOrderDetailPrice());
        model.setPartName(orderDetailsEntity.getPart().getPartName());

        return model;
    }

}
