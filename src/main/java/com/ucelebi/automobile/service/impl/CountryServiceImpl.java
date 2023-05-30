package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.CountryDao;
import com.ucelebi.automobile.model.Country;
import com.ucelebi.automobile.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryServiceImpl extends AbstractServiceImpl<Country, Long> implements CountryService {

    private final CountryDao countryDao;

    @Autowired
    public CountryServiceImpl(CountryDao countryDao) {
        super(countryDao);
        this.countryDao = countryDao;
    }

    @Override
    public Optional<Country> findCountryByCode(String code) {
        return countryDao.findByCode(code);
    }
}
