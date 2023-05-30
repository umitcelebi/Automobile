package com.ucelebi.automobile.facade;

import com.ucelebi.automobile.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryFacade {
    CountryDTO save(CountryDTO entity);
    List<CountryDTO> findAll();
    Page<CountryDTO> findAll(Pageable pageable);
    CountryDTO findCountryByCode(String code);
    CountryDTO update(CountryDTO entity);
    void delete(CountryDTO entity);
}
