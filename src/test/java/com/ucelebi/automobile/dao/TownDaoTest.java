package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Town;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * User: ucelebi
 * Date: 4.06.2023
 * Time: 13:16
 */
@DataJpaTest
@ActiveProfiles("test")
class TownDaoTest {

    @Autowired
    private TownDao underTest;

    @Autowired
    private CityDao cityDao;

    @Test
    void itShouldSaveTownSuccessfully() {
        //Given
        Town town = new Town("3406","Üsküdar",null);

        //When
        Town savedTown = underTest.save(town);

        //Then
        assertThat(savedTown).isNotNull();
        assertThat(savedTown.getId()).isNotNull();

        Optional<Town> optionalTown = underTest.findById(savedTown.getId());

        assertThat(optionalTown)
                .isPresent()
                .hasValueSatisfying(c-> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("creation_time","modified_time")
                        .isEqualTo(town));
    }

    @Test
    void itShouldNotSaveTownWhenCodeExist() {
        //Given
        String townCode = "34";
        Town existTown = new Town(townCode,"Kadıköy",null);
        underTest.save(existTown);

        Town town = new Town(townCode, "Maltepe",null);

        //When
        //Then
        assertThatThrownBy(()->underTest.save(town))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement [Unique index or primary key violation");
    }

    @Test
    void itShouldNotSaveTownWhenCodeNull() {
        //Given
        Town town = new Town(null,"Kadıköy",null);

        //When
        //Then
        assertThatThrownBy(()->underTest.save(town))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Town.code");

    }

    @Test
    void itShouldNotSaveTownWhenNameNull() {
        //Given
        Town town = new Town("3422",null,null);

        //When
        //Then
        assertThatThrownBy(()->underTest.save(town))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Town.name");

    }

    @Test
    void itShouldSelectSavedTownByCode() {
        //Given
        String townCode = "TR";
        Town town = new Town(townCode,"Bakırköy",null);

        underTest.save(town);

        //When
        Optional<Town> optionalTown = underTest.findByCode(townCode);

        //Then
        assertThat(optionalTown).isPresent()
                .hasValueSatisfying(c-> assertThat(c)
                        .usingRecursiveComparison()
                        .isEqualTo(town));
    }

    @Test
    void itShouldNotSelectWhenCityCodeDoesNotExist() {
        //Given
        //When
        Optional<Town> optionalTown = underTest.findByCode("347682");

        //Then
        assertThat(optionalTown).isNotPresent();
    }

    @Test
    void itShouldSelectTownByCityCode() {
        //Given
        String cityCode = "34";
        City istanbul = new City(cityCode,"Istanbul",null);
        cityDao.save(istanbul);

        Town town = new Town("3422","Kadıköy",istanbul);
        Town town2 = new Town("3423","Bakırköy",istanbul);
        Town town3 = new Town("3424","Başakşehir",istanbul);

        //When
        underTest.saveAll(Arrays.asList(town,town2,town3));

        //Then
        assertEquals(3,underTest.findAllByCityCode(cityCode).size());
    }

    @Test
    @DisplayName("City code does not exist in any town")
    void itShouldNotSelectTownWhenCityCodeDoesNotExist() {
        //Given
        String cityCode = "34";
        City istanbul = new City(cityCode,"Istanbul",null);
        cityDao.save(istanbul);

        Town town = new Town("3422","Kadıköy",istanbul);
        Town town2 = new Town("3423","Bakırköy",istanbul);
        Town town3 = new Town("3424","Başakşehir",istanbul);

        //When
        underTest.saveAll(Arrays.asList(town,town2,town3));

        //Then
        assertEquals(0,underTest.findAllByCityCode("35").size());
    }
}