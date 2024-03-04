/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import com.vadimzu.webpcstore.entity.OrderDetailsEntity;
import com.vadimzu.webpcstore.entity.OrderEntity;
import com.vadimzu.webpcstore.entity.PartEntity;
import com.vadimzu.webpcstore.entity.StaffEntity;
import com.vadimzu.webpcstore.model.Order;
import com.vadimzu.webpcstore.repository.OrderRepo;
import com.vadimzu.webpcstore.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import com.vadimzu.webpcstore.repository.CustomerRepo;
import com.vadimzu.webpcstore.repository.OrderDetailsRepo;
import com.vadimzu.webpcstore.repository.PartRepo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

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
    @Autowired
    private OrderDetailsRepo orderDetailsRepo;
    @Autowired
    private PartRepo partRepo;

    private Date orderDate;

    public Order createOrder(Map<String, Object> body) {

        Object custList = body.get("customer");

        List<Object> list = (ArrayList<Object>) custList;

        System.out.println("\n\nInner List: " + list + " size :" + list.size());

        CustomerEntity customer = new CustomerEntity();

        for (Object o : list) {
            if (o instanceof Map) {
                Map< String, Object> map = (Map< String, Object>) o;
                System.out.println("Inside list: " + map + " size :" + map.size());
                int i = 0;
                for (Map.Entry< String, Object> entry : map.entrySet()) {
                    if (entry.getKey().equals("customerName")) {
                        customer.setCustomerName((String) entry.getValue());
                    }
                    if (entry.getKey().equals("address")) {
                        customer.setAddress((String) entry.getValue());
                    }
                    if (entry.getKey().equals("email")) {
                        customer.setEmail((String) entry.getValue());
                    }

                    i++;
                }
            }
        }
        // I. save new customer in db
        customerRepo.save(customer);

        // create current time object
        orderDate = new Date();

        //extact staff's name from the body
        String staffName = (String) body.get("staff");
        //find staff from repo by id   
        StaffEntity staff = staffRepo.findByLogin(staffName);

        //extract totalPrice from the body
        Double price = (Double) body.get("totalPrice");
        Double totalPrice = new Double(price);

        // Creat new order and insert all related data in
        OrderEntity order = new OrderEntity();

        //set the time into order object     
        order.setOrderDate(orderDate);
        //relate the order with customer              
        order.setCustomer(customer);
        //relate the order with staff        
        order.setStaff(staff);
        //set the totalPrice into the order
        order.setTotalPrice(totalPrice);

        // II.Save order in DB
        orderRepo.save(order);

        // create new OrderDetails
        //OrderDetailsEntity orderDetails = new OrderDetailsEntity();
        //extract list of ordered part from the request body
        List<Object> orderedParts = (ArrayList<Object>) body.get("orderedParts");
        System.out.println("\nOuter list of orderedParts: " + orderedParts + " size: " + orderedParts.size());

        //loop Outer scope of JSON orderedParts list and relate with the order
        for (Object details1 : orderedParts) {

            List<Object> orderedPart = (ArrayList<Object>) details1;
            System.out.println("Inner list of orderedPart: " + orderedPart + " size: " + orderedPart.size());

            //loop Inner scope of JSON orderedParts list and relate with the order
            for (Object details : orderedPart) {
                // create new OrderDetails
                OrderDetailsEntity orderDetails = new OrderDetailsEntity();
                System.out.println("\nNew OrderDetail line been created");

                if (details instanceof Map) {
                    Map< String, Object> map = (Map< String, Object>) details;
                    System.out.println("Inside details: " + map + " size " + map.size());

                    for (Map.Entry< String, Object> entry : map.entrySet()) {
                        if (entry.getKey().equals("partID")) {
                            Integer id = (Integer) entry.getValue();
                            Long id2 = new Long(id);
                            PartEntity part = partRepo.findByPartID(id2);
                            orderDetails.setPart(part);
                            System.out.println("Set partId: " + entry.getValue());
                        }
                        if (entry.getKey().equals("partQuantity")) {
                            Integer partQ = (Integer) entry.getValue();
                            Long quantity = new Long(partQ);
                            orderDetails.setOrderDetailQuantity(quantity);
                            System.out.println("Set quantity: " + entry.getValue());
                        }
                        if (entry.getKey().equals("partPrice")) {
                            Double priceOne = (Double) entry.getValue();
                            Double priceDouble = new Double(priceOne);
                            orderDetails.setOrderDetailPrice(priceDouble);
                            System.out.println("Set Price: " + entry.getValue());
                        }

                    }
                }
                orderDetails.setOrder(order);
                //decrease quantity of part
                if(orderDetails.getPart() != null){
                    PartEntity part = partRepo.findByPartID(orderDetails.getPart().getPartID());
                    part.setStockQuantity(part.getStockQuantity() - orderDetails.getOrderDetailQuantity());
                    partRepo.save(part);
                }
                System.out.println("Set order: " + order.getOrderID());
                orderDetailsRepo.save(orderDetails);
                System.out.println("Save line ID: " + orderDetails.getOrderDetailID() + " into DB\n");

            }

        }

        // saving created new order into repo          
        return Order.toModel(orderRepo.save(order));
    }
}
