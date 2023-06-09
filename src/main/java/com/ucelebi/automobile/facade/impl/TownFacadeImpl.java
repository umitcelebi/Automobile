package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.TownDTO;
import com.ucelebi.automobile.facade.TownFacade;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Town;
import com.ucelebi.automobile.service.CityService;
import com.ucelebi.automobile.service.TownService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TownFacadeImpl implements TownFacade {
    public static Logger log = Logger.getLogger(TownFacadeImpl.class);
    private final TownService townService;
    private final CityService cityService;
    private final ModelMapper modelMapper;

    @Autowired
    public TownFacadeImpl(TownService townService, CityService cityService, ModelMapper modelMapper) {
        this.townService = townService;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TownDTO save(TownDTO entity) {
        Town town = modelMapper.map(entity, Town.class);
        Town savedTown = townService.save(town);
        return modelMapper.map(savedTown, TownDTO.class);
    }

    @Override
    public List<TownDTO> findAll() {
        return townService.findAll().stream().map(s -> modelMapper.map(s, TownDTO.class)).toList();
    }

    @Override
    public Page<TownDTO> findAll(Pageable pageable) {
        Page<Town> townPage = townService.findAll(pageable);
        List<TownDTO> townList = townPage.getContent().stream().map(t -> modelMapper.map(t, TownDTO.class)).toList();
        return new PageImpl<>(townList, pageable, townList.size());
    }

    @Override
    public List<TownDTO> findAllByCityCode(String cityCode) {
        return townService.findAllByCityCode(cityCode).stream().map(t -> modelMapper.map(t, TownDTO.class)).toList();
    }

    @Override
    public TownDTO findTownByCode(String code) {
        Optional<Town> townOptional = townService.findByCode(code);
        if (townOptional.isEmpty()) {
            return null;
        }
        Town town = townOptional.get();
        return modelMapper.map(town, TownDTO.class);
    }

    @Override
    public TownDTO update(TownDTO entity) {
        Optional<Town> optionalTown = townService.findByCode(entity.getCode());
        if (optionalTown.isEmpty()) {
            return null;
        }
        Town town = optionalTown.get();
        town.setActive(entity.isActive());
        town.setName(entity.getName());
        if (entity.getCityCode() != null && !entity.getCityCode().isEmpty()) {
            Optional<City> cityByCode = cityService.findCityByCode(entity.getCityCode());
            town.setCity(cityByCode.orElse(null));
        }
        Town updatedTown = townService.update(town);
        return modelMapper.map(updatedTown, TownDTO.class);
    }

    @Override
    public void delete(TownDTO entity) {
        Optional<Town> optionalTown = townService.findByCode(entity.getCode());
        if (optionalTown.isEmpty()) {
            return;
        }
        Town town = optionalTown.get();
        try {
            townService.deleteById(town.getId());
        }catch (RuntimeException e) {
            log.error("Error while deleting town.", e);
        }
    }
}
