package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.CityDTO;
import com.ucelebi.automobile.facade.CityFacade;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Country;
import com.ucelebi.automobile.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;

/**
 * User: ucelebi
 * Date: 10.06.2023
 * Time: 14:59
 */
class CityFacadeImplTest {

    private CityFacade underTest;
    @Mock
    private CityService cityService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CityFacadeImpl(cityService,modelMapper);
    }

    @Test
    void itShouldSaveCitySuccessfully() {
        //Given
        CityDTO cityDTO = new CityDTO(null,null,true,"34","Istanbul","TR");

        City city = new City(null,null, null,true,"34","Istanbul",new Country("TR","Turkiye"),null);

        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());
        City savedCity = new City(1L, creationTime, modifiedTime,true,"34","Istanbul",new Country("TR","Turkiye"),null);
        CityDTO savedCityDTO = new CityDTO(creationTime,modifiedTime,true,"34","Istanbul","TR");

        given(modelMapper.map(cityDTO,City.class)).willReturn(city);
        given(cityService.save(city)).willReturn(savedCity);
        given(modelMapper.map(savedCity,CityDTO.class)).willReturn(savedCityDTO);

        //When
        CityDTO result = underTest.save(cityDTO);

        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(savedCityDTO);
    }

    @Test
    void itShouldSelectAllCityAsCityDTO() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        City firstCity = new City(1L, firstCreationTime, firstModifiedTime,true,"34","Istanbul",new Country("TR","Turkiye"),null);

        Timestamp secondCreationTime = Timestamp.from(Instant.now());
        Timestamp secondModifiedTime = Timestamp.from(Instant.now());
        City secondCity = new City(2L, secondCreationTime, secondModifiedTime,true,"35","Izmir",new Country("TR","Turkiye"),null);


        CityDTO firstCityDTO = new CityDTO(firstCreationTime,firstModifiedTime,true,"34","Istanbul","TR");
        CityDTO secondCityDTO = new CityDTO(secondCreationTime,secondModifiedTime,true,"35","Izmir","TR");

        given(cityService.findAll()).willReturn(List.of(firstCity,secondCity));
        given(modelMapper.map(firstCity,CityDTO.class))
                .willReturn(firstCityDTO);
        given(modelMapper.map(secondCity,CityDTO.class))
                .willReturn(secondCityDTO);

        //When
        List<CityDTO> citylist = underTest.findAll();

        //Then
        assertEquals(2,citylist.size());
        assertEquals(firstCityDTO,citylist.get(0));
        assertEquals(secondCityDTO,citylist.get(1));
    }

    @Test
    void itShouldSelectAllCityAsPageableCityDTO() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        City firstCity = new City(1L, firstCreationTime, firstModifiedTime,true,"34","Istanbul",new Country("TR","Turkiye"),null);

        Timestamp secondCreationTime = Timestamp.from(Instant.now());
        Timestamp secondModifiedTime = Timestamp.from(Instant.now());
        City secondCity = new City(2L, secondCreationTime, secondModifiedTime,true,"35","Izmir",new Country("TR","Turkiye"),null);


        CityDTO firstCityDTO = new CityDTO(firstCreationTime,firstModifiedTime,true,"34","Istanbul","TR");
        CityDTO secondCityDTO = new CityDTO(secondCreationTime,secondModifiedTime,true,"35","Izmir","TR");

        PageRequest pageable = PageRequest.of(0, 5);
        given(cityService.findAll(pageable)).willReturn(new PageImpl<>(List.of(firstCity,secondCity),pageable,2));
        given(modelMapper.map(firstCity,CityDTO.class))
                .willReturn(firstCityDTO);
        given(modelMapper.map(secondCity,CityDTO.class))
                .willReturn(secondCityDTO);

        //When
        Page<CityDTO> pagedCity = underTest.findAll(pageable);

        //Then
        assertEquals(2,pagedCity.getTotalElements());
        assertEquals(firstCityDTO,pagedCity.getContent().get(0));
        assertEquals(secondCityDTO,pagedCity.getContent().get(1));
    }


    @Test
    void itShouldSelectByCityCode() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        City city = new City(1L, firstCreationTime, firstModifiedTime,true,"34","Istanbul",new Country("TR","Turkiye"),null);

        CityDTO cityDTO = new CityDTO(firstCreationTime,firstModifiedTime,true,"34","Istanbul","TR");

        given(cityService.findCityByCode("34")).willReturn(Optional.of(city));
        given(modelMapper.map(city,CityDTO.class))
                .willReturn(cityDTO);

        //When
        CityDTO cityByCode = underTest.findCityByCode("34");

        //Then
        assertThat(cityByCode).usingRecursiveComparison().isEqualTo(cityDTO);
    }

    @Test
    void itShouldNotSelectByCityCodeWhenCityCodeDoesNotExist() {
        //Given
        String cityCode = "34";
        given(cityService.findCityByCode(cityCode)).willReturn(Optional.empty());

        //When
        CityDTO cityByCode = underTest.findCityByCode(cityCode);

        //Then
        assertThat(cityByCode).isNull();
    }


    @Test
    void itShouldUpdateCitySuccessfully() {
        //Given
        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        CityDTO cityDTO = new CityDTO(creationTime,modifiedTime,false,"34","istanbul","TR");
        City city = new City(1L,creationTime, modifiedTime,true,"34","Istanbul",new Country("TR","Turkiye"),null);

        City updatedCity = new City(1L, creationTime, modifiedTime,false,"34","istanbul",new Country("TR","Turkiye"),null);
        CityDTO updatedCityDTO = new CityDTO(creationTime,modifiedTime,false,"34","istanbul","TR");

        given(cityService.findCityByCode(cityDTO.getCode())).willReturn(Optional.of(city));
        given(cityService.update(city)).willReturn(updatedCity);

        given(modelMapper.map(updatedCity,CityDTO.class)).willReturn(cityDTO);

        //When
        CityDTO result = underTest.update(cityDTO);

        //Then
        then(cityService).should().update(city);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(updatedCityDTO);
    }


    @Test
    void itShouldNotUpdateWhenCityCodeDoesNotExist() {
        //Given
        String cityCode = "34";
        CityDTO cityDTO = new CityDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,cityCode,"Istanbul","TR");
        given(cityService.findCityByCode(cityCode)).willReturn(Optional.empty());

        //When
        CityDTO updatedCity = underTest.update(cityDTO);

        //Then
        assertNull(updatedCity);
    }

    @Test
    void itShouldDeleteSuccessfully() {
        //Given
        String cityCode = "34";
        CityDTO cityDTO = new CityDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,cityCode,"Istanbul","TR");

        City city = new City(1L, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), true, cityCode, "Istanbul", new Country("TR","Turkiye"), null);
        given(cityService.findCityByCode(cityCode)).willReturn(Optional.of(city));

        //When
        underTest.delete(cityDTO);

        //Then
        then(cityService).should().delete(city);
    }

    @Test
    void itShouldNotDeleteWhenCityCodeDoesNotExist() {
        //Given
        String cityCode = "34";
        CityDTO cityDTO = new CityDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,cityCode,"Istanbul","TR");

        given(cityService.findCityByCode(cityCode)).willReturn(Optional.empty());

        //When
        underTest.delete(cityDTO);
        //Then
        then(cityService).should(never()).delete(any());
    }

    @Test
    void itShouldNotDeleteWhenThrowException() {
        //Given
        String cityCode = "34";
        CityDTO cityDTO = new CityDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,cityCode,"Istanbul","TR");

        City city = new City(1L, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), true, cityCode, "Istanbul", new Country("TR","Turkiye"), null);
        given(cityService.findCityByCode(cityCode)).willReturn(Optional.of(city));

        doThrow(RuntimeException.class).when(cityService).delete(city);

        //When
        //Then
        underTest.delete(cityDTO);
    }
}