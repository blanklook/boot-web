package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

@Transactional
@Service
public class CustomerService {
	@Autowired
	private CustomerRepository repository;
	
	public List<Customer> findAll() {
		return repository.findAll();
	}
	public Customer findOne(Long id) {
		return repository.findOne(id);
	}
	public Customer create(Customer customer) {
		return repository.save(customer);
	}
	public Customer update(Customer customer) {
		return repository.save(customer);
	}
	public void delete(Long id) {
		repository.delete(id);
	}
	public List<Customer> findByFirstName(String firstName) {
		return repository.findByFirstName(firstName);
	}
}
