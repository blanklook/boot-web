package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Customer;
import com.example.service.CustomerService;

@RestController
@RequestMapping("api/customers")
public class CustomerRestController {
	@Autowired
	CustomerService service;
	
	@RequestMapping(method=RequestMethod.GET)
	Page<Customer> customers(@PageableDefault Pageable pageable) {
		return service.findAll(pageable);
	}
		
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	Customer getCustomer(@PathVariable("id") Long id) {
		return service.findOne(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	Customer postCustomer(@RequestBody Customer customer) {
		return service.create(customer);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	Customer putCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		Customer findCustomer = service.findOne(id);
		Customer updateCustomer = findCustomer.updateName(customer.getFirstName(), customer.getLastName());
		return service.update(updateCustomer);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	void deleteCustomer(@PathVariable Long id) {
		service.delete(id);
	}
}
