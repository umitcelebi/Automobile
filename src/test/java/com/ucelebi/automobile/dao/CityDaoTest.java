package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * User: ucelebi
 * Date: 4.06.2023
 * Time: 12:45
 */
@DataJpaTest
@ActiveProfiles("test")
class CityDaoTest {

    @Autowired
    private CityDao underTest;

    @Test
    void itShouldSaveCitySuccessfully() {
        //Given
        City city = new City("34","Istanbul",null);

        //When
        City savedCity = underTest.save(city);

        //Then
        assertThat(savedCity).isNotNull();
        assertThat(savedCity.getId()).isNotNull();

        Optional<City> optionalCity = underTest.findById(savedCity.getId());

        assertThat(optionalCity)
                .isPresent()
                .hasValueSatisfying(c-> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("creation_time","modified_time")
                        .isEqualTo(city));
    }

    @Test
    void itShouldSaveCitySuccessfullyWhenCountryDoesNotExistInDb() {
        //Given
        Country country = new Country("TR", "Turkey");
        City city = new City("34","Istanbul",country);

        //When
        City savedCity = underTest.save(city);

        //Then
        assertThat(savedCity).isNotNull();
        assertThat(savedCity.getId()).isNotNull();

        Optional<City> optionalCity = underTest.findById(savedCity.getId());

        assertThat(optionalCity)
                .isPresent()
                .hasValueSatisfying(c-> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("creation_time","modified_time")
                        .isEqualTo(city));
    }

    @Test
    void itShouldNotSaveCityWhenCodeExist() {
        //Given
        String cityCode = "34";
        City existCity = new City(cityCode,"Istanbul",null);
        underTest.save(existCity);

        City city = new City(cityCode, "Samsun",null);

        //When
        //Then
        assertThatThrownBy(()->underTest.save(city))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement [Unique index or primary key violation");
    }

    @Test
    void itShouldNotSaveCityWhenCodeNull() {
        //Given
        City city = new City(null,"Turkey",null);

        //When
        //Then
        assertThatThrownBy(()->underTest.save(city))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.City.code");

    }

    @Test
    void itShouldNotSaveCityWhenNameNull() {
        //Given
        City city = new City("34",null,new Country("TR","Turkey"));

        //When
        //Then
        assertThatThrownBy(()->underTest.save(city))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.City.name");

    }

    @Test
    void itShouldSelectSavedCityByCode() {
        //Given
        String cityCode = "TR";
        City city = new City(cityCode,"Istanbul", null);

        underTest.save(city);

        //When
        Optional<City> optionalCity = underTest.findByCode(cityCode);

        //Then
        assertThat(optionalCity).isPresent()
                .hasValueSatisfying(c-> assertThat(c)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(city));
    }

    @Test
    void itShouldNotSelectWhenCityCodeDoesNotExist() {
        //Given
        //When
        Optional<City> optionalCity = underTest.findByCode("545454");

        //Then
        assertThat(optionalCity).isNotPresent();
    }
}