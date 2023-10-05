/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author vadimzubchenko
 */
@Entity
@Table(name = "orderDetails")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderDetailID")
public class OrderDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailID;

    private Long orderDetailQuantity;
    private Double orderDetailPrice;

    @ManyToOne (cascade=CascadeType.DETACH)
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;

    @ManyToOne (cascade=CascadeType.DETACH)
    @JoinColumn(name = "part_id")
    private PartEntity part;

    @ManyToOne (cascade=CascadeType.REMOVE)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public OrderDetailsEntity() {
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

    public Double getOrderDetailPrice() {
        return orderDetailPrice;
    }

    public void setOrderDetailPrice(Double orderDetailPrice) {
        this.orderDetailPrice = orderDetailPrice;
    }

    public PackageEntity getPackageEntity() {
        return packageEntity;
    }

    public void setPackageEntity(PackageEntity packageEntity) {
        this.packageEntity = packageEntity;
    }

    public PartEntity getPart() {
        return part;
    }

    public void setPart(PartEntity part) {
        this.part = part;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

}
