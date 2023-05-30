package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.Country;

import java.util.Optional;

public interface CountryService extends AbstractService<Country, Long> {
    Optional<Country> findCountryByCode(String code);
}
