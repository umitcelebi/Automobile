package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.CityDTO;
import com.ucelebi.automobile.dto.CountryDTO;
import com.ucelebi.automobile.facade.CountryFacade;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Country;
import com.ucelebi.automobile.service.CityService;
import com.ucelebi.automobile.service.CountryService;
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
import java.util.Arrays;
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
 * Date: 12.06.2023
 * Time: 00:13
 */
class CountryFacadeImplTest {

    private CountryFacade underTest;
    @Mock
    private CountryService countryService;
    @Mock
    private CityService cityService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CountryFacadeImpl(countryService, cityService, modelMapper);
    }

    @Test
    void itShouldSaveCountrySuccessfully() {
        //Given
        CountryDTO countryDTO = new CountryDTO(null,null,true,"TR","Turkiye",null);

        Country country = new Country(null,null, null,true,"TR","Turkiye",null);

        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());
        Country savedCountry = new Country(1L, creationTime, modifiedTime,true,"TR","Turkiye",null);
        CountryDTO savedCountryDTO = new CountryDTO(creationTime,modifiedTime,true,"TR","Turkiye",null);

        given(modelMapper.map(countryDTO,Country.class)).willReturn(country);
        given(countryService.save(country)).willReturn(savedCountry);
        given(modelMapper.map(savedCountry,CountryDTO.class)).willReturn(savedCountryDTO);

        //When
        CountryDTO result = underTest.save(countryDTO);

        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(savedCountryDTO);
    }

    @Test
    void itShouldSelectAllCountryAsCountryDTO() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        Country firstCountry = new Country(1L, firstCreationTime, firstModifiedTime,true,"TR","Turkey",null);

        Timestamp secondCreationTime = Timestamp.from(Instant.now());
        Timestamp secondModifiedTime = Timestamp.from(Instant.now());
        Country secondCountry = new Country(2L, secondCreationTime, secondModifiedTime,true,"USA","United State",null);


        CountryDTO firstCountryDTO = new CountryDTO(firstCreationTime,firstModifiedTime,true,"TR","Turkey",null);
        CountryDTO secondCountryDTO = new CountryDTO(secondCreationTime,secondModifiedTime,true,"USA","United State",null);

        given(countryService.findAll()).willReturn(List.of(firstCountry,secondCountry));
        given(modelMapper.map(firstCountry,CountryDTO.class))
                .willReturn(firstCountryDTO);
        given(modelMapper.map(secondCountry,CountryDTO.class))
                .willReturn(secondCountryDTO);

        //When
        List<CountryDTO> Countrylist = underTest.findAll();

        //Then
        assertEquals(2,Countrylist.size());
        assertEquals(firstCountryDTO,Countrylist.get(0));
        assertEquals(secondCountryDTO,Countrylist.get(1));
    }

    @Test
    void itShouldSelectAllCountryAsPageableCountryDTO() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        Country firstCountry = new Country(1L, firstCreationTime, firstModifiedTime,true,"TR","Turkey",null);

        Timestamp secondCreationTime = Timestamp.from(Instant.now());
        Timestamp secondModifiedTime = Timestamp.from(Instant.now());
        Country secondCountry = new Country(2L, secondCreationTime, secondModifiedTime,true,"USA","United State",null);


        CountryDTO firstCountryDTO = new CountryDTO(firstCreationTime,firstModifiedTime,true,"TR","Turkey",null);
        CountryDTO secondCountryDTO = new CountryDTO(secondCreationTime,secondModifiedTime,true,"USA","United State",null);

        PageRequest pageable = PageRequest.of(0, 5);
        PageImpl<Country> countryPage = new PageImpl<>(List.of(firstCountry, secondCountry), pageable, 2);
        given(countryService.findAll(pageable)).willReturn(countryPage);
        given(modelMapper.map(firstCountry,CountryDTO.class))
                .willReturn(firstCountryDTO);
        given(modelMapper.map(secondCountry,CountryDTO.class))
                .willReturn(secondCountryDTO);

        //When
        Page<CountryDTO> pagedCountry = underTest.findAll(pageable);

        //Then
        assertEquals(2,pagedCountry.getTotalElements());
        assertEquals(firstCountryDTO,pagedCountry.getContent().get(0));
        assertEquals(secondCountryDTO,pagedCountry.getContent().get(1));
    }

    @Test
    void itShouldSelectByCountryCode() {
        //Given
        Timestamp firstCreationTime = Timestamp.from(Instant.now());
        Timestamp firstModifiedTime = Timestamp.from(Instant.now());
        String countryCode = "TR";
        Country country = new Country(1L, firstCreationTime, firstModifiedTime,true, countryCode,"Turkey",null);

        CountryDTO countryDTO = new CountryDTO(firstCreationTime,firstModifiedTime,true, countryCode,"Turkey",null);

        given(countryService.findCountryByCode(countryCode)).willReturn(Optional.of(country));
        given(modelMapper.map(country,CountryDTO.class))
                .willReturn(countryDTO);

        //When
        CountryDTO CountryByCode = underTest.findCountryByCode(countryCode);

        //Then
        assertThat(CountryByCode).usingRecursiveComparison().isEqualTo(countryDTO);
    }

    @Test
    void itShouldNotSelectByCountryCodeWhenCountryCodeDoesNotExist() {
        //Given
        String countryCode = "34";
        given(countryService.findCountryByCode(countryCode)).willReturn(Optional.empty());

        //When
        CountryDTO CountryByCode = underTest.findCountryByCode(countryCode);

        //Then
        assertThat(CountryByCode).isNull();
    }

    @Test
    void itShouldUpdateCountrySuccessfully() {
        //Given
        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        CountryDTO countryDTO = new CountryDTO(creationTime,modifiedTime,false,"TR","Turkiye",null);
        Country country = new Country(1L,creationTime, modifiedTime,true,"TR","Turkey",null);

        Country updatedCountry = new Country(1L, creationTime, modifiedTime,false,"TR","Turkiye",null);
        CountryDTO updatedCountryDTO = new CountryDTO(creationTime,modifiedTime,false,"TR","Turkiye",null);

        given(countryService.findCountryByCode(countryDTO.getCode())).willReturn(Optional.of(country));
        given(countryService.update(country)).willReturn(updatedCountry);

        given(modelMapper.map(updatedCountry,CountryDTO.class)).willReturn(countryDTO);

        //When
        CountryDTO result = underTest.update(countryDTO);

        //Then
        then(countryService).should().update(country);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(updatedCountryDTO);
    }

    @Test
    void itShouldUpdateCountrySuccessfullyWhenCountryHasCiy() {
        //Given
        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        CityDTO cityDTOOne = new CityDTO(creationTime, modifiedTime, true, "34", "Istanbul", "TR");
        CityDTO cityDTOTwo = new CityDTO(creationTime, modifiedTime, true, "55", "Samsun", "TR");
        CityDTO cityDTOThree = new CityDTO(creationTime, modifiedTime, true, "35", "Izmir", "TR");
        CityDTO cityDTOFour = new CityDTO(creationTime, modifiedTime, true, "46", "Maras", "TR");
        City cityOne =  new City(1L,creationTime,modifiedTime,true,"34","Istanbul",null,null);
        City cityTwo =  new City(1L,creationTime,modifiedTime,true,"55","Samsun",null,null);
        City cityThree =  new City(1L,creationTime,modifiedTime,true,"46","Maras",null,null);

        CountryDTO countryDTO = new CountryDTO(creationTime,modifiedTime,false,"TR","Turkiye", Arrays.asList(cityDTOOne,cityDTOTwo,cityDTOThree,cityDTOFour));
        Country country = new Country(1L,creationTime, modifiedTime,true,"TR","Turkey",null);

        Country updatedCountry = new Country(1L, creationTime, modifiedTime,false,"TR","Turkiye",Arrays.asList(cityOne,cityTwo,cityThree));
        CountryDTO updatedCountryDTO = new CountryDTO(creationTime,modifiedTime,false,"TR","Turkiye",Arrays.asList(cityDTOOne,cityDTOTwo,cityDTOFour));

        given(countryService.findCountryByCode(countryDTO.getCode())).willReturn(Optional.of(country));
        given(countryService.update(country)).willReturn(updatedCountry);
        given(cityService.findCityByCode(cityDTOOne.getCode())).willReturn(Optional.of(cityOne));
        given(cityService.findCityByCode(cityDTOTwo.getCode())).willReturn(Optional.of(cityTwo));
        given(cityService.findCityByCode(cityDTOThree.getCode())).willReturn(Optional.of(cityThree));
        given(cityService.findCityByCode(cityDTOFour.getCode())).willReturn(Optional.empty());

        given(modelMapper.map(updatedCountry,CountryDTO.class)).willReturn(updatedCountryDTO);

        //When
        CountryDTO result = underTest.update(countryDTO);

        //Then
        then(countryService).should().update(country);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(updatedCountryDTO);
    }

    @Test
    void itShouldNotUpdateWhenCountryCodeDoesNotExist() {
        //Given
        String countryCode = "GB";
        CountryDTO countryDTO = new CountryDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,countryCode,"England",null);
        given(countryService.findCountryByCode(countryCode)).willReturn(Optional.empty());

        //When
        CountryDTO updatedCountry = underTest.update(countryDTO);

        //Then
        assertNull(updatedCountry);
    }

    @Test
    void itShouldDeleteSuccessfully() {
        //Given
        String countryCode = "TR";
        CountryDTO countryDTO = new CountryDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,countryCode,"Turkey",null);

        Country country = new Country(1L, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), true, countryCode, "Turkey", null);
        given(countryService.findCountryByCode(countryCode)).willReturn(Optional.of(country));

        //When
        underTest.delete(countryDTO);

        //Then
        then(countryService).should().delete(country);
    }

    @Test
    void itShouldNotDeleteWhenCountryCodeDoesNotExist() {
        //Given
        String countryCode = "GB";
        CountryDTO countryDTO = new CountryDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,countryCode,"England",null);

        given(countryService.findCountryByCode(countryCode)).willReturn(Optional.empty());

        //When
        underTest.delete(countryDTO);
        //Then
        then(countryService).should(never()).delete(any());
    }

    @Test
    void itShouldNotDeleteWhenThrowException() {
        //Given
        String countryCode = "US";
        CountryDTO CountryDTO = new CountryDTO(Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,countryCode,"United States",null);

        Country country = new Country(1L, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), true, countryCode, "United States",  null);
        given(countryService.findCountryByCode(countryCode)).willReturn(Optional.of(country));

        doThrow(RuntimeException.class).when(countryService).delete(country);

        //When
        //Then
        underTest.delete(CountryDTO);
    }
}