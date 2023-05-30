package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.Address;

import java.util.Optional;

public interface AddressService extends AbstractService<Address, Long>{
    Optional<Address> findByCode(String code);
}
