package com.mcq.accountsvc.service;

import com.mcq.accountsvc.entity.Customer;
import com.mcq.accountsvc.exception.DuplicateResourceException;
import com.mcq.accountsvc.exception.ResourceNotFoundException;
import com.mcq.accountsvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final Random random = new Random();

    public Customer createCustomer(Customer customer) {

        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResourceException("Customer with email " + customer.getEmail() + " already exists");
        }

        if (customerRepository.existsBySsn(customer.getSsn())) {
            throw new DuplicateResourceException("Customer with SSN " + customer.getSsn() + " already exists");
        }

        customer.setGullibilityScore(calculateGullibilityScore());

        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
    }

    private int calculateGullibilityScore() {
        return random.nextInt(50) + 50;
    }
}
