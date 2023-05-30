package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.AddressDao;
import com.ucelebi.automobile.model.Address;
import com.ucelebi.automobile.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl extends AbstractServiceImpl<Address,Long> implements AddressService {

    private final AddressDao addressDao;

    @Autowired
    public AddressServiceImpl(AddressDao addressDao) {
        super(addressDao);
        this.addressDao = addressDao;
    }

    @Override
    public Optional<Address> findByCode(String code) {
        return addressDao.findByCode(code);
    }
}
