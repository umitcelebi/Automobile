package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.TownDTO;
import com.ucelebi.automobile.facade.TownFacade;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Town;
import com.ucelebi.automobile.service.TownService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * User: ucelebi
 * Date: 16.06.2023
 * Time: 22:18
 */
class TownFacadeImplTest {

    private TownFacade underTest;
    @Mock
    private TownService townService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new TownFacadeImpl(townService,modelMapper);
    }

    @Test
    void itShouldSaveTownSuccessfully() {
        //Given
        TownDTO townDTO = new TownDTO(null,null,true,"3406","Bakırköy","34");

        Town town = new Town(null,null, null,true,"3406","Bakırköy",null,null);

        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());
        Town savedTown = new Town(1L, creationTime, modifiedTime,true,"3406","Bakırköy",null,null);
        TownDTO savedTownDTO = new TownDTO(creationTime,modifiedTime,true,"3406","Bakırköy",null);

        given(modelMapper.map(townDTO,Town.class)).willReturn(town);
        given(townService.save(town)).willReturn(savedTown);
        given(modelMapper.map(savedTown,TownDTO.class)).willReturn(savedTownDTO);

        //When
        TownDTO result = underTest.save(townDTO);

        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(savedTownDTO);
    }

    @Test
    void itShouldSelectAllTownAsTownDTO() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        Town firstTown = new Town(1L, firstCreationTime, firstModifiedTime,true,"3407","Kadıköy",new City(1L,firstCreationTime,firstModifiedTime,true,"34","İstanbul",null,null),null);

        Timestamp secondCreationTime = Timestamp.from(Instant.now());
        Timestamp secondModifiedTime = Timestamp.from(Instant.now());
        Town secondTown = new Town(2L, firstCreationTime, firstModifiedTime,true,"3408","Maltepe",new City(1L,firstCreationTime,firstModifiedTime,true,"34","İstanbul",null,null),null);


        TownDTO firstTownDTO = new TownDTO(firstCreationTime,firstModifiedTime,true,"3407","Kadıköy","34");
        TownDTO secondTownDTO = new TownDTO(secondCreationTime,secondModifiedTime,true,"3408","Maltepe","34");

        given(townService.findAll()).willReturn(List.of(firstTown,secondTown));
        given(modelMapper.map(firstTown,TownDTO.class))
                .willReturn(firstTownDTO);
        given(modelMapper.map(secondTown,TownDTO.class))
                .willReturn(secondTownDTO);

        //When
        List<TownDTO> townList = underTest.findAll();

        //Then
        assertEquals(2,townList.size());
        assertThat(townList).containsAll(List.of(firstTownDTO,secondTownDTO));
    }
}