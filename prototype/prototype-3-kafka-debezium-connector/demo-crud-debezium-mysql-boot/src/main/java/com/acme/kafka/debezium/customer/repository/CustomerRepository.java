package com.acme.kafka.debezium.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.acme.kafka.debezium.customer.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
}
