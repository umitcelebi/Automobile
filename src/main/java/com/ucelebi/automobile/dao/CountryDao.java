package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Country;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryDao extends AbstractItemDao<Country, Long> {
    Optional<Country> findByCode(String code);
}
