package com.ucelebi.automobile.facade;

import com.ucelebi.automobile.dto.SectorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SectorFacade {
    SectorDTO save(SectorDTO entity);
    List<SectorDTO> findAll();
    Page<SectorDTO> findAll(Pageable pageable);
    SectorDTO update(SectorDTO entity);
    void delete(SectorDTO entity);
}
