package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Address;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressDao extends AbstractItemDao<Address, Long> {
    Optional<Address> findByCode(String code);
}
