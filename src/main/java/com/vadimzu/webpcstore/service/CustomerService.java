/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.service;

import com.vadimzu.webpcstore.entity.CustomerEntity;
import com.vadimzu.webpcstore.exception.ResourceAlreadyExistException;
import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vadimzu.webpcstore.repository.CustomerRepo;
import java.util.List;

/**
 *
 * @author vadimzubchenko
 */
@Service
public class CustomerService {

    // inject repository for calling entity from DB
    @Autowired
    private CustomerRepo customerRepo;

    public CustomerEntity registration(CustomerEntity customer) throws ResourceAlreadyExistException {

        if (customerRepo.findByCustomerName(customer.getCustomerName()) != null) {
            throw new ResourceAlreadyExistException("A customer with same name already exists");
        }

        return customerRepo.save(customer);
    }

    public Customer getOne(Long id) throws ResourceNotFoundException {
        if (!customerRepo.findById(id).isPresent()) {
            throw new ResourceNotFoundException("There's no customer with ID: " + id);

        }
        CustomerEntity customer = customerRepo.findById(id).get();
        return Customer.toModel(customer);
    }

    public List<CustomerEntity> getAll() throws ResourceNotFoundException {
        List<CustomerEntity> customers = customerRepo.findAll();
        if (customers == null) {
            throw new ResourceNotFoundException("There'are no registered customers in DB");

        }
        return customers;
    }

    public Customer deleteUser(Long id) throws ResourceNotFoundException {
        if (!customerRepo.findById(id).isPresent()) {
            throw new ResourceNotFoundException("There's no customer with ID: " + id);

        }
        CustomerEntity customer = customerRepo.findById(id).get();
        customerRepo.deleteById(id);

        return Customer.toModel(customer);

    }

}
