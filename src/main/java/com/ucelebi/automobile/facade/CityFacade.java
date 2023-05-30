package com.ucelebi.automobile.facade;

import com.ucelebi.automobile.dto.CityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityFacade {
    CityDTO save(CityDTO entity);
    List<CityDTO> findAll();
    Page<CityDTO> findAll(Pageable pageable);
    CityDTO findCityByCode(String code);
    CityDTO update(CityDTO entity);
    void delete(CityDTO entity);
}
