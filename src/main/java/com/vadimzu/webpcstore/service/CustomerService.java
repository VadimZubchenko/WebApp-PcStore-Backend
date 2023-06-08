/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import com.vadimzu.webpcstore.exception.CustomerAlreadyExistExeption;
import com.vadimzu.webpcstore.exception.CustomerNotFoundException;
import com.vadimzu.webpcstore.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vadimzu.webpcstore.repository.CustomerRepo;


/**
 *
 * @author vadimzubchenko
 */
@Service
public class CustomerService {

    // inject repository for calling entity from DB
    @Autowired
    private CustomerRepo customerRepo;

    public CustomerEntity registration(CustomerEntity customer) throws CustomerAlreadyExistExeption {
        
        if (customerRepo.findByCustomerName(customer.getCustomerName()) != null) {
            throw new CustomerAlreadyExistExeption("A customer with same name already exists");
        }

        return customerRepo.save(customer);
    }

    public Customer getOne(Long id) throws CustomerNotFoundException {
        CustomerEntity customer = customerRepo.findById(id).get();
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with name is not found");

        }
        return Customer.toModel(customer);
    }

    public Customer deleteUser(Long id) {
       CustomerEntity customer = customerRepo.findById(id).get();
       customerRepo.deleteById(id);

        return Customer.toModel(customer);

    }

}
