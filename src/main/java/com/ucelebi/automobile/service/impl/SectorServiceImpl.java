package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.SectorDao;
import com.ucelebi.automobile.model.Sector;
import com.ucelebi.automobile.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SectorServiceImpl extends AbstractServiceImpl<Sector, Long> implements SectorService {

    private final SectorDao sectorDao;

    @Autowired
    public SectorServiceImpl(SectorDao sectorDao) {
        super(sectorDao);
        this.sectorDao = sectorDao;
    }

    @Override
    public Optional<Sector> findByCode(String code) {
        return sectorDao.findByCode(code);
    }
}
