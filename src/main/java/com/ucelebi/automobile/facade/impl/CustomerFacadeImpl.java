package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.CustomerDTO;
import com.ucelebi.automobile.dto.CustomerListDTO;
import com.ucelebi.automobile.facade.CustomerFacade;
import com.ucelebi.automobile.model.Customer;
import com.ucelebi.automobile.service.CustomerService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerFacadeImpl implements CustomerFacade {
    public static Logger log = Logger.getLogger(CustomerFacadeImpl.class);
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerFacadeImpl(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO save(CustomerDTO entity) {
        Customer customer = modelMapper.map(entity, Customer.class);
        Customer savedCustomer = customerService.save(customer);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Override
    public List<CustomerListDTO> findAll() {
        return customerService.findAll().stream().map(c -> modelMapper.map(c, CustomerListDTO.class)).toList();
    }

    @Override
    public Page<CustomerListDTO> findAll(Pageable pageable) {
        Page<Customer> customerPage = customerService.findAll(pageable);
        List<CustomerListDTO> customerList = customerPage.getContent().stream().map(c -> modelMapper.map(c, CustomerListDTO.class)).toList();
        return new PageImpl<>(customerList, pageable, customerList.size());
    }

    @Override
    public CustomerDTO findByUid(String uid) {
        Optional<Customer> customerOptional = customerService.findByUid(uid);
        if (customerOptional.isEmpty()) {
            return null;
        }
        Customer customer = customerOptional.get();
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO update(CustomerDTO entity) {
        Optional<Customer> customerOptional = customerService.findByUid(entity.getUid());
        if (customerOptional.isEmpty()) return null;
        Customer customer = customerOptional.get();
        customer.setName(entity.getName());
        customer.setDisplayName(entity.getDisplayName());
        customer.setPhoneNumber(entity.getPhoneNumber());
        customer.setRole(entity.getRole());
        customer.setActive(entity.isActive());
        customer.setProfilePhoto(entity.getProfilePhoto());
        Customer updatedCustomer = customerService.update(customer);
        return modelMapper.map(updatedCustomer, CustomerDTO.class);
    }

    @Override
    public void delete(CustomerDTO entity) {
        Optional<Customer> customerOptional = customerService.findByUid(entity.getUid());
        if (customerOptional.isEmpty()) return;
        Customer customer = customerOptional.get();
        try {
            customerService.delete(customer);
        } catch (RuntimeException e) {
            log.error("Error while deleting customer.", e);
        }
    }

    @Override
    public void deleteByUid(String uid) {
        try {
            customerService.deleteByUid(uid);
        } catch (RuntimeException e) {
            log.error("Error while deleting customer", e);
        }
    }
}
