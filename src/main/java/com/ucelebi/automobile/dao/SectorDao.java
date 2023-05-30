package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Sector;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorDao extends AbstractItemDao<Sector, Long> {
    Optional<Sector> findByCode(String code);
}
