package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.SectorDTO;
import com.ucelebi.automobile.facade.SectorFacade;
import com.ucelebi.automobile.model.Sector;
import com.ucelebi.automobile.service.SectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


/**
 * User: ucelebi
 * Date: 17.06.2023
 * Time: 15:48
 */
class SectorFacadeImplTest {

    private SectorFacade underTest;
    @Mock
    private SectorService sectorService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new SectorFacadeImpl(sectorService,modelMapper);
    }

    @Test
    void itShouldSaveSectorSuccessfully() {
        //Given
        SectorDTO sectorDTO = new SectorDTO(null,null,true,"EGZ","Egzoz");
        Sector sector = new Sector(null,null,null,true,"EGZ","Egzoz",null);

        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());
        Sector savedSector = new Sector(1L, creationTime, modifiedTime,true,"EGZ","Egzoz",null);
        SectorDTO savedSectorDTO = new SectorDTO(creationTime,modifiedTime,true,"EGZ","Egzoz");

        given(modelMapper.map(sectorDTO, Sector.class)).willReturn(sector);
        given(sectorService.save(sector)).willReturn(savedSector);
        given(modelMapper.map(savedSector, SectorDTO.class)).willReturn(savedSectorDTO);

        //When
        SectorDTO result = underTest.save(sectorDTO);

        //Then
        verify(sectorService, Mockito.times(1)).save(sector);
        verify(modelMapper,Mockito.times(2)).map(any(),any());
        assertEquals(savedSectorDTO,result);
    }

    @Test
    void itShouldSelectAllSector() {
        //Given
        //When
        //Then
    }

    @Test
    void itShouldSelectAllSectorPageable() {
        //Given
        //When
        //Then
    }

    @Test
    void itShouldUpdateSectorSuccessfully() {
        //Given
        //When
        //Then
    }

    @Test
    void itShouldNotUpdateSectorWhenSectorCodeDoesNotExist() {
        //Given
        //When
        //Then
    }

    @Test
    void itShouldDeleteSector() {
        //Given
        //When
        //Then
    }

    @Test
    void itShouldNotDeleteSectorWhenSectorCodeDoesNotExist() {
        //Given
        //When
        //Then
    }

    @Test
    void itShouldNotDeleteSectorWhenThrowException() {
        //Given
        //When
        //Then
    }
}