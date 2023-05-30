package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.Sector;

import java.util.Optional;

public interface SectorService extends AbstractService<Sector, Long> {
    Optional<Sector> findByCode(String code);
}
