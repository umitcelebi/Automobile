package com.ucelebi.automobile.service;

import com.ucelebi.automobile.dto.PartnerListDTO;
import com.ucelebi.automobile.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PartnerService extends AbstractService<Partner, Long> {
    Page<PartnerListDTO> findAllByDistance(double latitude, double longitude, Pageable pageable);
    Page<PartnerListDTO> findAllBySectors(double latitude, double longitude, String sectorCode, Pageable pageable);
    Page<PartnerListDTO> findAllByCity(double latitude, double longitude, String cityCode, Pageable pageable);
    Page<PartnerListDTO> findAllByTown(double latitude, double longitude, String townCode, Pageable pageable);
    Optional<Partner> findByUid(String uid);
    void deleteByUid(String uid);
}
