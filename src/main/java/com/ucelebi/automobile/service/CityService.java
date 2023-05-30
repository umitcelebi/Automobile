package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.City;

import java.util.Optional;

public interface CityService extends AbstractService<City, Long> {
    Optional<City> findCityByCode(String code);
}
