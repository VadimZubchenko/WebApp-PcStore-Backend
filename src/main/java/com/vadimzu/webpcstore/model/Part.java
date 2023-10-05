/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.model;

import com.vadimzu.webpcstore.entity.PartEntity;

/**
 *
 * @author vadimzubchenko
 */
public class Part {

    private Long partID;
    private String partName;
    private Long partPrice;
    private Long stockQuantity;
    private String partType;
    private String shelf;

    public static Part toModel(PartEntity partEntity) {
        Part model = new Part();

        model.setPartID(partEntity.getPartID());
        model.setPartName(partEntity.getPartName());
        model.setPartPrice(partEntity.getPartPrice());
        model.setStockQuantity(partEntity.getStockQuantity());
        model.setPartType(partEntity.getPartType());
        model.setShelf(partEntity.getShelfNumber());

        return model;
    }

    private Part() {
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

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Long getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(Long partPrice) {
        this.partPrice = partPrice;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

}
