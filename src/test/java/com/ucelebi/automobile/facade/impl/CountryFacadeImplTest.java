package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.CountryDTO;
import com.ucelebi.automobile.facade.CountryFacade;
import com.ucelebi.automobile.model.Country;
import com.ucelebi.automobile.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

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
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CountryFacadeImpl(countryService,modelMapper);
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

}