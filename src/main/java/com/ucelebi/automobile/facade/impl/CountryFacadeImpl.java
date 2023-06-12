package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.CountryDTO;
import com.ucelebi.automobile.facade.CountryFacade;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Country;
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
public class CountryFacadeImpl implements CountryFacade {

    private final CountryService countryService;
    private final CityService cityService;
    private final ModelMapper modelMapper;
    public static Logger log = Logger.getLogger(CountryFacadeImpl.class);

    @Autowired
    public CountryFacadeImpl(CountryService countryService, CityService cityService, ModelMapper modelMapper) {
        this.countryService = countryService;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CountryDTO save(CountryDTO entity) {
        Country country = modelMapper.map(entity, Country.class);
        Country savedCountry = countryService.save(country);
        return modelMapper.map(savedCountry, CountryDTO.class);
    }

    @Override
    public List<CountryDTO> findAll() {
        return countryService.findAll().stream().map(c -> modelMapper.map(c, CountryDTO.class)).toList();
    }

    @Override
    public Page<CountryDTO> findAll(Pageable pageable) {
        Page<Country> countryPage = countryService.findAll(pageable);
        List<CountryDTO> countryList = countryPage.getContent().stream().map(c -> modelMapper.map(c, CountryDTO.class)).toList();
        return new PageImpl<>(countryList, pageable, countryList.size());
    }

    @Override
    public CountryDTO findCountryByCode(String code) {
        Optional<Country> countryOptional = countryService.findCountryByCode(code);
        if (countryOptional.isEmpty()) {
            return null;
        }
        Country country = countryOptional.get();
        return modelMapper.map(country, CountryDTO.class);
    }

    @Override
    public CountryDTO update(CountryDTO entity) {
        Optional<Country> countryOptional = countryService.findCountryByCode(entity.getCode());
        if (countryOptional.isEmpty()) return null;
        Country country = countryOptional.get();
        country.setActive(entity.isActive());
        country.setName(entity.getName());
        if (entity.getCities() != null && !entity.getCities().isEmpty()) {
            List<City> cities = entity.getCities()
                    .stream()
                    .map(c->cityService.findCityByCode(c.getCode()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            country.setCities(cities);
        }
        Country updatedCountry = countryService.update(country);
        return modelMapper.map(updatedCountry, CountryDTO.class);
    }

    @Override
    public void delete(CountryDTO entity) {
        Optional<Country> countryOptional = countryService.findCountryByCode(entity.getCode());
        if (countryOptional.isEmpty()) return;
        Country country = countryOptional.get();
        try {
            countryService.delete(country);
        } catch (RuntimeException e) {
            log.error("Error while deleting country.", e);
        }
    }
}
