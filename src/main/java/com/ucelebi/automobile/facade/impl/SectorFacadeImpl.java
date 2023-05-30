package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.SectorDTO;
import com.ucelebi.automobile.facade.SectorFacade;
import com.ucelebi.automobile.model.Sector;
import com.ucelebi.automobile.service.SectorService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SectorFacadeImpl implements SectorFacade {
    public static Logger log = Logger.getLogger(PartnerFacadeImpl.class);
    private final SectorService sectorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SectorFacadeImpl(SectorService sectorService, ModelMapper modelMapper) {
        this.sectorService = sectorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public SectorDTO save(SectorDTO entity) {
        Sector sector = modelMapper.map(entity, Sector.class);
        Sector savedSector = sectorService.save(sector);
        return modelMapper.map(savedSector, SectorDTO.class);
    }

    @Override
    public List<SectorDTO> findAll() {
        return sectorService.findAll().stream().map(s -> modelMapper.map(s, SectorDTO.class)).toList();
    }

    @Override
    public Page<SectorDTO> findAll(Pageable pageable) {
        Page<Sector> sectorPage = sectorService.findAll(pageable);
        List<SectorDTO> sectorList = sectorPage.getContent().stream().map(s -> modelMapper.map(s, SectorDTO.class)).toList();
        return new PageImpl<>(sectorList, pageable, sectorList.size());
    }

    @Override
    public SectorDTO update(SectorDTO entity) {
        return null;
    }

    @Override
    public void delete(SectorDTO entity) {

    }
}
