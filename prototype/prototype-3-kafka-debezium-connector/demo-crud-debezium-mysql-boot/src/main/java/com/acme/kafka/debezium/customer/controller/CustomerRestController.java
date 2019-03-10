package com.acme.kafka.debezium.customer.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acme.kafka.debezium.constant.CustomerRestControllerConstant;
import com.acme.kafka.debezium.customer.entity.Customer;
import com.acme.kafka.debezium.customer.repository.CustomerRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(CustomerRestControllerConstant.MAPPING)
@Api(value=CustomerRestControllerConstant.API_MESSAGE_VALUE)
public class CustomerRestController {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerRestController.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/")
	public void isAlived() {
		LOG.info("[CustomerRestController] is Alived ...");
	}
		
	@RequestMapping(value=CustomerRestControllerConstant.BASE,method=RequestMethod.GET, produces="application/json")
	@ApiOperation(value="Get the list of customers")
    public List<Customer> getCustomerList() {
		LOG.info("[CustomerRestController] get Customer List...");
        return (List<Customer>) this.customerRepository.findAll();
    }

	@RequestMapping(value=CustomerRestControllerConstant.BASE+"/{id}", method=RequestMethod.GET, produces="application/json")
	@ApiOperation(value="Get a customer by their Id")
    public Customer getCustomerById(@PathVariable Long id, HttpServletResponse res) {
		LOG.info("[CustomerRestController] get Customer by Id : {}", id);
		Optional<Customer> customerFound = this.customerRepository.findById(id);
		if(!customerFound.isPresent()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return customerFound.get();
    }
	
	@RequestMapping(value=CustomerRestControllerConstant.BASE, method=RequestMethod.POST, produces="application/json")
	@ApiOperation(value="Add a new customer")
    public void addCustomer(@RequestBody Customer customer, HttpServletResponse res) throws IOException {
		LOG.info("[CustomerRestController] add Customer...");
		try {
			this.customerRepository.save(customer);
		}
		catch(Exception ex) {
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
    }

	@RequestMapping(value=CustomerRestControllerConstant.BASE+"/{id}", method=RequestMethod.PUT, produces="application/json")
	@ApiOperation(value="Update a customer")
    public Customer updateCustomer(@RequestBody Customer customerNew,  @PathVariable Long id, HttpServletResponse res) throws IOException {
		LOG.info("[CustomerRestController] update Customer...");
		Customer customerOld = this.getCustomerById(id, res);
		if(customerOld != null) { // Found
			customerOld.setFirstName(customerNew.getFirstName());
			customerOld.setLastName(customerNew.getLastName());
			customerOld.setEmail(customerNew.getEmail());
			this.addCustomer(customerOld, res);
		}
		return customerOld;
    }
	
	@RequestMapping(value=CustomerRestControllerConstant.BASE+"/{id}", method=RequestMethod.DELETE, produces="application/json")
	@ApiOperation(value="Delete a customer")
    public void deleteUser(@PathVariable Long id, HttpServletResponse res) throws IOException {
		try {
			this.customerRepository.deleteById(id);
		}
		catch(Exception ex) {
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
    }
	
}