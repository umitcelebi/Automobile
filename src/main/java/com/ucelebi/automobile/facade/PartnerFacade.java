package com.ucelebi.automobile.facade;

import com.ucelebi.automobile.modelFilter.PartnerFilter;
import com.ucelebi.automobile.dto.PartnerDTO;
import com.ucelebi.automobile.dto.PartnerListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PartnerFacade {
    PartnerDTO save(PartnerDTO entity);
    List<PartnerListDTO> findAll();
    Page<PartnerListDTO> findAll(Pageable pageable, PartnerFilter partnerFilter);
    PartnerDTO update(PartnerDTO entity);
    Page<PartnerListDTO> findAllBySectors(double latitude, double longitude, String sectorCode, Pageable pageable);
    Page<PartnerListDTO> findAllByCity(double latitude, double longitude, String cityCode, Pageable pageable);
    Page<PartnerListDTO> findAllByTown(double latitude, double longitude, String townCode, Pageable pageable);
    PartnerDTO findByUid(String uid);
    boolean addProfilePhoto(String uid, MultipartFile multipartFile);
    void deleteByUid(String uid);
    void delete(PartnerDTO entity);
}
