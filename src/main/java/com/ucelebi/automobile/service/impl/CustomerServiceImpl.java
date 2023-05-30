package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.CustomerDao;
import com.ucelebi.automobile.model.Customer;
import com.ucelebi.automobile.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl extends AbstractServiceImpl<Customer, Long> implements CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        super(customerDao);
        this.customerDao = customerDao;
    }

    @Override
    public Optional<Customer> findByUid(String uid) {
        return customerDao.findByUid(uid);
    }

    @Override
    public void deleteByUid(String uid) {
        try {
            customerDao.deleteCustomerByUid(uid);
        }catch (Exception e) {

        }
    }
}
