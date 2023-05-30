package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.City;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityDao extends AbstractItemDao<City, Long> {
    Optional<City> findByCode(String code);
}
