package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.Town;

import java.util.List;
import java.util.Optional;

public interface TownService extends AbstractService<Town, Long> {
    Optional<Town> findByCode(String code);
    List<Town> findAllByCityCode(String cityCode);

}
