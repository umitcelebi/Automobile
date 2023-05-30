package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.CityDao;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityServiceImpl extends AbstractServiceImpl<City, Long> implements CityService {

    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao) {
        super(cityDao);
        this.cityDao = cityDao;
    }

    @Override
    public Optional<City> findCityByCode(String code) {
        return cityDao.findByCode(code);
    }
}
