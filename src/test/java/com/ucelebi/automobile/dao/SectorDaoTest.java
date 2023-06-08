package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Sector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * User: ucelebi
 * Date: 8.06.2023
 * Time: 15:58
 */
@DataJpaTest
@ActiveProfiles("test")
class SectorDaoTest {

    @Autowired
    private SectorDao underTest;

    @Test
    void itShouldSaveSectorSuccessfully() {
        //Given
        Sector sector = new Sector("EGZ", "Egzoz");
        //When
        Sector savedSector = underTest.save(sector);
        //Then
        assertThat(savedSector).isNotNull();
        assertThat(savedSector.getId()).isNotNull();

        Optional<Sector> sectorOptional = underTest.findById(savedSector.getId());
        assertThat(sectorOptional).isPresent()
                .hasValueSatisfying(s->assertThat(s).usingRecursiveComparison().isEqualTo(sector));
    }

    @Test
    void itShouldSelectSectorByCode() {
        //Given
        Sector sector = new Sector("EGZ", "Egzoz");
        Sector savedSector = underTest.save(sector);
        //When
        Optional<Sector> sectorOptional = underTest.findByCode(savedSector.getCode());
        //Then

        assertThat(sectorOptional).isPresent()
                .hasValueSatisfying(s->assertThat(s).usingRecursiveComparison().isEqualTo(sector));
    }

    @Test
    void itShouldNotSelectSectorByCodeWhenCodeDoesNotExist() {
        //Given
        String code = "KPT";
        //When
        Optional<Sector> optionalSector = underTest.findByCode(code);
        //Then
        assertThat(optionalSector).isNotPresent();
    }
}