package com.example.service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.example.App;
import com.example.domain.Customer;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@Transactional
public class TestCustomerService {
	
	@Autowired
	CustomerService service;

	@Test
	public void testServiceIsNotNull() {
		assertThat(service, is(notNullValue()));
	}
	
	@Test
	public void testCreateCustomer() {
		Customer savedCustomer = service.create(new Customer("A", "A1"));
		int size = service.findAll().size();
		assertThat(savedCustomer.getId(), is(notNullValue()));
		assertThat(size, is(165));
	}
	
	@Test
	public void testUpdateCustomer() {
		Customer savedCustomer = service.create(new Customer("A", "A1"));
		Customer updateCustomer = savedCustomer.updateName("Update", "Update1");
		service.update(updateCustomer);
		
		Customer findCustomer = service.findOne(savedCustomer.getId());
		assertThat(updateCustomer.getFirstName(), is(findCustomer.getFirstName()));
	}
	
	@Test
	public void testFindOne() {
		Customer savedCustomer = service.create(new Customer("B", "B1"));
		Customer findCustomer = service.findOne(savedCustomer.getId());
		assertThat(savedCustomer.getFirstName(), is(findCustomer.getFirstName()));
	}
	
	@Test
	public void testGetCustomers() {
		service.create(new Customer("B", "B1"));
		service.create(new Customer("B", "B1"));
		service.create(new Customer("B", "B1"));
		service.create(new Customer("B", "B1"));
		
		int size = service.findByFirstName("B").size();
		assertThat(size, is(45));
	}
	
	
	@Test
	public void testPageable() {
		Pageable pageable = new PageRequest(0, 20);
		Page<Customer> page = service.findAll(pageable);
		
		assertThat(page.getNumber(), is(0));
		assertThat(page.getSize(), is(20));
		assertThat(page.getTotalPages(), is(9));
		
		Pageable nextPageable = pageable.next();
		page = service.findAll(nextPageable);

		assertThat(page.getNumber(), is(1));
		assertThat(page.getSize(), is(20));
		assertThat(page.getTotalPages(), is(9));

		Pageable lastPageable = new PageRequest(8, 20);
		page = service.findAll(lastPageable);

		assertThat(page.getNumber(), is(8));
		assertThat(page.getSize(), is(20));
		assertThat(page.getTotalPages(), is(9));
		assertThat(page.getContent().size(), is(4));
	}
	
	@Test
	public void testPageableSort() {
		Pageable pageable = new PageRequest(0, 20, Sort.Direction.DESC, "id");
		Page<Customer> page = service.findAll(pageable);
		assertThat(page.getContent().get(0).getId(), is(164L));
	}
}
