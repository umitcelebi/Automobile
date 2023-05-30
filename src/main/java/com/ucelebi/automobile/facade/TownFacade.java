package com.ucelebi.automobile.facade;

import com.ucelebi.automobile.dto.TownDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TownFacade {
    TownDTO save(TownDTO entity);
    List<TownDTO> findAll();
    Page<TownDTO> findAll(Pageable pageable);
    List<TownDTO> findAllByCityCode(String cityCode);
    TownDTO findTownByCode(String code);
    TownDTO update(TownDTO entity);
    void delete(TownDTO entity);
}
