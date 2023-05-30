package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.CityDTO;
import com.ucelebi.automobile.facade.CityFacade;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.service.CityService;
import com.ucelebi.automobile.service.CountryService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CityFacadeImpl implements CityFacade {

    private final CityService cityService;
    private final CountryService countryService;
    private final ModelMapper modelMapper;
    public static Logger log = Logger.getLogger(CityFacadeImpl.class);

    @Autowired
    public CityFacadeImpl(CityService cityService, CountryService countryService, ModelMapper modelMapper) {
        this.cityService = cityService;
        this.countryService = countryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CityDTO save(CityDTO entity) {
        City city = modelMapper.map(entity, City.class);
        City savedCity = cityService.save(city);
        return modelMapper.map(savedCity, CityDTO.class);
    }

    @Override
    public List<CityDTO> findAll() {
        List<City> cityList = cityService.findAll();
        return cityList.stream().map(c -> modelMapper.map(c, CityDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Page<CityDTO> findAll(Pageable pageable) {
        Page<City> cityPage = cityService.findAll(pageable);
        List<CityDTO> cityDTOList = cityPage.getContent().stream().map(c -> modelMapper.map(c, CityDTO.class)).toList();
        return new PageImpl<>(cityDTOList, pageable, cityDTOList.size());
    }

    @Override
    public CityDTO findCityByCode(String code) {
        Optional<City> cityOptional = cityService.findCityByCode(code);
        if (cityOptional.isEmpty()) {
            return null;
        }
        City city = cityOptional.get();
        return modelMapper.map(city, CityDTO.class);
    }

    @Override
    public CityDTO update(CityDTO entity) {
        Optional<City> cityOptional = cityService.findCityByCode(entity.getCode());
        if (cityOptional.isEmpty()) return null;
        City city = cityOptional.get();
        city.setActive(entity.isActive());
        city.setName(entity.getName());
        City updatedCity = cityService.update(city);
        return modelMapper.map(updatedCity, CityDTO.class);
    }

    @Override
    public void delete(CityDTO entity) {
        Optional<City> cityOptional = cityService.findCityByCode(entity.getCode());
        if (cityOptional.isEmpty()) return;
        City city = cityOptional.get();
        try {
            cityService.delete(city);
        } catch (Exception e) {
            log.error("Error while deleting city.", e);
        }
    }
}
