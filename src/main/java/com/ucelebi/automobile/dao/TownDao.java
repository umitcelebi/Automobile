package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Town;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TownDao extends AbstractItemDao<Town, Long> {
    Optional<Town> findByCode(String code);
    List<Town> findAllByCityCode(String cityCode);
}
