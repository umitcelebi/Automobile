package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.TownDTO;
import com.ucelebi.automobile.facade.TownFacade;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Town;
import com.ucelebi.automobile.service.CityService;
import com.ucelebi.automobile.service.TownService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
    private CityService cityService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new TownFacadeImpl(townService, cityService, modelMapper);
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

    @Test
    void itShouldSelectTownByCityCodeAsTownDTO() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        Town firstTown = new Town(1L, firstCreationTime, firstModifiedTime,true,"3407","Kadıköy",new City(1L,firstCreationTime,firstModifiedTime,true,"34","İstanbul",null,null),null);

        Timestamp secondCreationTime = Timestamp.from(Instant.now());
        Timestamp secondModifiedTime = Timestamp.from(Instant.now());
        Town secondTown = new Town(2L, firstCreationTime, firstModifiedTime,true,"5508","Atakum",new City(2L,firstCreationTime,firstModifiedTime,true,"55","Samsun",null,null),null);

        TownDTO firstTownDTO = new TownDTO(firstCreationTime,firstModifiedTime,true,"3407","Kadıköy","34");
        TownDTO secondTownDTO = new TownDTO(secondCreationTime,secondModifiedTime,true,"5508","Atakum","55");

        given(townService.findAllByCityCode("34")).willReturn(List.of(firstTown));
        given(modelMapper.map(firstTown,TownDTO.class))
                .willReturn(firstTownDTO);
        given(modelMapper.map(secondTown,TownDTO.class))
                .willReturn(secondTownDTO);

        //When
        List<TownDTO> townList = underTest.findAllByCityCode("34");

        //Then
        assertEquals(1,townList.size());
        assertThat(townList).containsAll(List.of(firstTownDTO));
    }

    @Test
    void itShouldSelectAllTownAsPageableTownDTO() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        Town firstTown = new Town(1L, firstCreationTime, firstModifiedTime,true,"3407","Kadıköy",new City(1L,firstCreationTime,firstModifiedTime,true,"34","İstanbul",null,null),null);

        Timestamp secondCreationTime = Timestamp.from(Instant.now());
        Timestamp secondModifiedTime = Timestamp.from(Instant.now());
        Town secondTown = new Town(2L, firstCreationTime, firstModifiedTime,true,"3408","Maltepe",new City(1L,firstCreationTime,firstModifiedTime,true,"34","İstanbul",null,null),null);


        TownDTO firstTownDTO = new TownDTO(firstCreationTime,firstModifiedTime,true,"3407","Kadıköy","34");
        TownDTO secondTownDTO = new TownDTO(secondCreationTime,secondModifiedTime,true,"3408","Maltepe","34");

        Pageable pageable = PageRequest.of(0,5);
        List<Town> townList = List.of(firstTown, secondTown);
        PageImpl<Town> townListDTO = new PageImpl<>(townList, pageable, townList.size());
        given(townService.findAll(pageable)).willReturn(townListDTO);
        given(modelMapper.map(firstTown,TownDTO.class))
                .willReturn(firstTownDTO);
        given(modelMapper.map(secondTown,TownDTO.class))
                .willReturn(secondTownDTO);

        //When
        Page<TownDTO> result = underTest.findAll(pageable);

        //Then
        assertEquals(2,result.getContent().size());
        assertThat(townList).containsAll(townListDTO.getContent());
    }

    @Test
    void itShouldSelectByTownCode() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        String townCode = "3407";
        Town town = new Town(1L, firstCreationTime, firstModifiedTime,true, townCode,"Kadıköy",new City(1L,firstCreationTime,firstModifiedTime,true,"34","İstanbul",null,null),null);

        TownDTO townDTO = new TownDTO(firstCreationTime,firstModifiedTime,true, townCode,"Kadıköy","34");

        given(townService.findByCode(townCode)).willReturn(Optional.of(town));
        given(modelMapper.map(town,TownDTO.class))
                .willReturn(townDTO);

        //When
        TownDTO result = underTest.findTownByCode(townCode);

        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(townDTO);
    }

    @Test
    void itShouldNotSelectByTownCodeWhenTownCodeDoesNotExist() {
        //Given
        String townCode = "3456";
        given(townService.findByCode(townCode)).willReturn(Optional.empty());

        //When
        TownDTO result = underTest.findTownByCode(townCode);

        //Then
        assertThat(result).isNull();
    }

    @Test
    void itShouldUpdateTownSuccessfully() {
        //Given
        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        String townCode = "3406";
        TownDTO townDTO = new TownDTO(creationTime,modifiedTime,false, townCode,"BAKIRKÖY","34");
        Town town = new Town(1L,creationTime,modifiedTime,true, townCode,"Bakırköy",new City("34","Istanbul",null),null);

        Timestamp updatedModifiedTime = Timestamp.from(Instant.now());
        Town updatedTown = new Town(1L, creationTime, updatedModifiedTime,false, townCode,"BAKIRKÖY",new City("34","Istanbul",null),null);
        TownDTO updatedTownDTO = new TownDTO(creationTime,updatedModifiedTime,false, townCode,"BAKIRKÖY","34");

        given(townService.findByCode(townCode)).willReturn(Optional.of(town));
        given(cityService.findCityByCode(townDTO.getCityCode())).willReturn(Optional.of(new City(1L,null,null,true,"34","Istanbul",null,null)));
        given(townService.update(town)).willReturn(updatedTown);
        given(modelMapper.map(updatedTown,TownDTO.class)).willReturn(updatedTownDTO);

        //When
        TownDTO result = underTest.update(townDTO);

        //Then
        then(townService).should().update(town);
        verify(cityService, Mockito.times(1)).findCityByCode(townDTO.getCityCode());
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(updatedTownDTO);
    }

    @Test
    void itShouldNotUpdateWhenTownCodeDoesNotExist() {
        //Given
        String townCode = "3478";
        TownDTO townDTO = new TownDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,townCode,"Başakşehir","34");
        given(townService.findByCode(townCode)).willReturn(Optional.empty());

        //When
        TownDTO updatedTown = underTest.update(townDTO);

        //Then
        assertNull(updatedTown);
    }

    @Test
    void itShouldUpdateTownWithoutCityWhenCityCodeDoesNotExist() {
        //Given
        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        String townCode = "3406";
        TownDTO townDTO = new TownDTO(creationTime,modifiedTime,false, townCode,"BAKIRKÖY",null);
        Town town = new Town(1L,creationTime,modifiedTime,true, townCode,"Bakırköy",new City("34","Istanbul",null),null);

        Timestamp updatedModifiedTime = Timestamp.from(Instant.now());
        Town updatedTown = new Town(1L, creationTime, updatedModifiedTime,false, townCode,"BAKIRKÖY",null,null);
        TownDTO updatedTownDTO = new TownDTO(creationTime,updatedModifiedTime,false, townCode,"BAKIRKÖY",null);

        given(townService.findByCode(townCode)).willReturn(Optional.of(town));
        given(townService.update(town)).willReturn(updatedTown);
        given(modelMapper.map(updatedTown,TownDTO.class)).willReturn(updatedTownDTO);

        //When
        TownDTO result = underTest.update(townDTO);

        //Then
        then(townService).should().update(town);
        verify(cityService,never()).findCityByCode(any());
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(updatedTownDTO);
    }

    @Test
    void itShouldDeleteSuccessfully() {
        //Given
        String townCode = "3412";
        TownDTO townDTO = new TownDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,townCode,"Beylikduzu","34");

        Town town = new Town(1L,Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true, townCode,"Beylikduzu",new City("34","Istanbul",null),null);
        given(townService.findByCode(townCode)).willReturn(Optional.of(town));

        //When
        underTest.delete(townDTO);

        //Then
        then(townService).should().deleteById(town.getId());
    }

    @Test
    void itShouldNotDeleteWhenCityCodeDoesNotExist() {
        //Given
        String townCode = "3412";
        TownDTO townDTO = new TownDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,townCode,"Beylikduzu","34");

        given(townService.findByCode(townCode)).willReturn(Optional.empty());

        //When
        underTest.delete(townDTO);

        //Then
        then(townService).should(never()).deleteById(anyLong());
    }

    @Test
    void itShouldNotDeleteWhenThrowException() {
        //Given
        String townCode = "3412";
        TownDTO townDTO = new TownDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,townCode,"Beylikduzu","34");

        Town town = new Town(1L,Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true, townCode,"Beylikduzu",new City("34","Istanbul",null),null);
        given(townService.findByCode(townCode)).willReturn(Optional.of(town));

        willThrow(RuntimeException.class).given(townService).deleteById(town.getId());

        //When
        //Then
        underTest.delete(townDTO);
    }
}