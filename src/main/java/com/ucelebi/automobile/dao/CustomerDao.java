package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDao extends AbstractItemDao<Customer, Long> {
    Optional<Customer> findByUid(String uid);
    void deleteCustomerByUid(String uid);
}
