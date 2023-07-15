/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.model;

import com.vadimzu.webpcstore.entity.StaffEntity;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author vadimzubchenko
 */
// Model creats Staff version for representation on Client side
public class Staff {

private Long staffId;
private String staffName;
private List<Order> orders;

public static Staff toModel(StaffEntity staff){

Staff model = new Staff();

model.setStaffId(staff.getStaffID());
model.setStaffName(staff.getStaffName());
model.setOrders(staff.getOrders().stream().map(Order::toModel).collect(Collectors.toList()));

return model;


}

    public Staff() {
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    
}
