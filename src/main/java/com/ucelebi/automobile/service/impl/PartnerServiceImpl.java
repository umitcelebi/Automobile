package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.PartnerDao;
import com.ucelebi.automobile.dto.PartnerListDTO;
import com.ucelebi.automobile.model.Partner;
import com.ucelebi.automobile.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartnerServiceImpl extends AbstractServiceImpl<Partner, Long> implements PartnerService {

    private final PartnerDao partnerDao;

    @Autowired
    public PartnerServiceImpl(PartnerDao partnerDao) {
        super(partnerDao);
        this.partnerDao = partnerDao;
    }

    @Override
    public Page<PartnerListDTO> findAllByDistance(double latitude, double longitude, Pageable pageable) {
        return partnerDao.findAllByDistance(latitude,longitude,pageable);
    }

    @Override
    public Page<PartnerListDTO> findAllBySectors(double latitude, double longitude, String sectorCode, Pageable pageable) {
        return partnerDao.findAllBySectorsAndDistance(latitude,longitude,sectorCode,pageable);
    }

    @Override
    public Page<PartnerListDTO> findAllByCity(double latitude, double longitude, String cityCode, Pageable pageable) {
        return partnerDao.findByAddress_City_CodeByDistance(latitude,longitude,cityCode,pageable);
    }

    @Override
    public Page<PartnerListDTO> findAllByTown(double latitude, double longitude, String townCode, Pageable pageable) {
        return partnerDao.findByAddress_Town_CodeByDistance(latitude,longitude,townCode,pageable);
    }

    @Override
    public Optional<Partner> findByUid(String uid) {
        return partnerDao.findByUid(uid);
    }

    @Override
    public void deleteByUid(String uid) {

    }
}
