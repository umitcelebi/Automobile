package com.ucelebi.automobile.service;


import com.ucelebi.automobile.model.Customer;

import java.util.Optional;

public interface CustomerService extends AbstractService<Customer, Long> {
    Optional<Customer> findByUid(String uid);
    void deleteByUid(String uid);
}
