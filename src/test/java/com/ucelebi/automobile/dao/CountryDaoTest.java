package com.ucelebi.automobile.dao;

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
 * Date: 3.06.2023
 * Time: 23:40
 */
@DataJpaTest
@ActiveProfiles("test")
class CountryDaoTest {

    @Autowired
    private CountryDao underTest;

    @Test
    void itShouldSaveCountrySuccessfully() {
        //Given
        Country country = new Country("TR","Turkey");
        long countryId = 1L;
        country.setId(countryId);
        //When
        underTest.save(country);
        //Then
        Optional<Country> optionalCountry = underTest.findById(countryId);
        assertThat(optionalCountry)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison().isEqualTo(c));
    }

    @Test
    void itShouldNotSaveCountryWhenCodeExist() {
        //Given
        String countryCode = "TR";
        Country existCountry = new Country(countryCode, "Turkey");
        underTest.save(existCountry);

        Country country = new Country(countryCode, "Türkiye");

        //When
        //Then
        assertThatThrownBy(()->underTest.save(country))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement [Unique index or primary key violation");
    }

    @Test
    void itShouldNotSaveCountryWhenCodeNull() {
        //Given
        Country country = new Country(null,"Turkey");

        //When
        //Then
        assertThatThrownBy(()->underTest.save(country))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Country.code");

    }

    @Test
    void itShouldNotSaveCountryWhenNameNull() {
        //Given
        Country country = new Country("TR",null);

        //When
        //Then
        assertThatThrownBy(()->underTest.save(country))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Country.name");

    }

    @Test
    void itShouldSelectSavedCountryByCode() {
        //Given
        String countryCode = "TR";
        Country country = new Country(countryCode, "Turkey");
        underTest.save(country);

        //When
        Optional<Country> optionalCountry = underTest.findByCode(countryCode);

        //Then
        assertThat(optionalCountry).isPresent()
                .hasValueSatisfying(c-> assertThat(c)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(country));

    }

    @Test
    void itShouldNotSelectWhenCountryCodeDoesNotExist() {
        //Given
        //When
        Optional<Country> optionalCountry = underTest.findByCode("TR");

        //Then
        assertThat(optionalCountry).isNotPresent();

    }
}