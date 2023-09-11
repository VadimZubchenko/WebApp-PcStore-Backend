/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import net.bytebuddy.asm.Advice;

/**
 *
 * @author vadimzubchenko
 */
@Entity
@Table(name = "parts")
//to fix infinity exception caused by OneToMany and ManyToOne
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "partID")
public class PartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partID;
    private String partName;
    private Long partPrice;
    private Long stockQuantity;
    private String partType;
    private String shelfNumber;
    
    //connect to orderDetails table via it's property named 'part'  
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "part")
    private List<OrderDetailsEntity> orderDetails;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "part")
    private List<PackageDetailsEntity> packageDetails;
    
    public PartEntity() {
    }

    public Long getPartID() {
        return partID;
    }

    public void setPartID(Long partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Long getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(Long partPrice) {
        this.partPrice = partPrice;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public String getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(String shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public List<OrderDetailsEntity> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsEntity> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<PackageDetailsEntity> getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(List<PackageDetailsEntity> packageDetails) {
        this.packageDetails = packageDetails;
    }

   
    

}
