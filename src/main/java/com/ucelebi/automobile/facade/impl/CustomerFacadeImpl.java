package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.CustomerDTO;
import com.ucelebi.automobile.dto.CustomerListDTO;
import com.ucelebi.automobile.exception.PermissionException;
import com.ucelebi.automobile.facade.CustomerFacade;
import com.ucelebi.automobile.model.Customer;
import com.ucelebi.automobile.model.Partner;
import com.ucelebi.automobile.model.User;
import com.ucelebi.automobile.service.CustomerService;
import com.ucelebi.automobile.service.PartnerService;
import com.ucelebi.automobile.util.ApplicationUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class CustomerFacadeImpl implements CustomerFacade {
    public static Logger log = Logger.getLogger(CustomerFacadeImpl.class);
    private final CustomerService customerService;
    private final PartnerService partnerService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerFacadeImpl(CustomerService customerService, PartnerService partnerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.partnerService = partnerService;
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

    @Override
    public boolean addToFavorite(String username) {
        if(username == null || username.isEmpty())
            return false;
        try {
            User currentUser = ApplicationUtils.getCurrentUser();
            if (currentUser instanceof Partner)
                throw new PermissionException("Partner cannot add a different partner to favorites.");


            Optional<Partner> partnerOpt = partnerService.findByUid(username);
            if (partnerOpt.isEmpty()) {
                log.error("Partner could not found for favorite.");
                return false;
            }
            Predicate<Customer> predicate = c -> Objects.equals(c.getId(), currentUser.getId());
            boolean isExist = partnerOpt.get().getFavoriteCustomers().stream().anyMatch(predicate);
            if (isExist){
                log.error("Partner could not be added to favorites. Already added to favorites.");
                return false;
            }
            Customer customer = (Customer) currentUser;
            customer.getFavoritePartners().add(partnerOpt.get());

            customerService.update(customer);
        }catch (PermissionException e) {
            log.error("You are not allowed to perform this action. ", e);
            throw e;
        }

        return false;
    }
}
