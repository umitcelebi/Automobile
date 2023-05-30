package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.TownDao;
import com.ucelebi.automobile.model.Town;
import com.ucelebi.automobile.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TownServiceImpl extends AbstractServiceImpl<Town, Long> implements TownService {

    private final TownDao townDao;

    @Autowired
    public TownServiceImpl(TownDao townDao) {
        super(townDao);
        this.townDao = townDao;
    }

    @Override
    public Optional<Town> findByCode(String code) {
        return townDao.findByCode(code);
    }

    @Override
    public List<Town> findAllByCityCode(String cityCode) {
        return townDao.findAllByCityCode(cityCode);
    }
}
