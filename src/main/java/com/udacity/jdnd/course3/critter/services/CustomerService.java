package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;


    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> fetchAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomer(long id){
        return customerRepository.getOne(id);
    }

}
