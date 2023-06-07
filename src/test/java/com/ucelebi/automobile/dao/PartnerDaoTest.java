package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.dto.PartnerListDTO;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.model.Partner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * User: ucelebi
 * Date: 6.06.2023
 * Time: 18:48
 */
@DataJpaTest
@ActiveProfiles("test")
class PartnerDaoTest {

    @Autowired
    private PartnerDao underTest;

    @Test
    void itShouldSavePartnerSuccessfully() {
        //Given
        Partner partner = new Partner.builder()
                .uid("autoRepairIstanbul")
                .name("Özcanlar Auto")
                .displayName("Özcanlar Auto")
                .role(Role.PARTNER)
                .phoneNumber("05346667788")
                .latitude(42.456)
                .longitude(28.543)
                .sundayOpen(false)
                .password("pass123456")
                .build();

        //When
        Partner savedPartner = underTest.save(partner);

        //Then
        assertThat(savedPartner).isNotNull();
        assertThat(savedPartner.getId()).isNotNull();

        Optional<Partner> optionalPartner = underTest.findById(savedPartner.getId());

        assertThat(optionalPartner)
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p).usingRecursiveComparison().isEqualTo(partner));
    }


    @Test
    void itShouldNotSavePartnerWhenUidNull() {
        //Given
        Partner partner = new Partner.builder()
                .uid(null)
                .name("Yılmaz Auto")
                .displayName("Yılmaz Auto")
                .role(Role.PARTNER)
                .phoneNumber("05332221199")
                .latitude(54.345)
                .longitude(67.435)
                .sundayOpen(false)
                .password("pass123456")
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(partner))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Partner.uid");

    }

    @Test
    void itShouldNotSavePartnerWhenUidExist() {
        // Given
        Partner partner = new Partner.builder()
                .uid("prestijAuto")
                .name("Prestij Auto")
                .displayName("Prestij Auto")
                .role(Role.PARTNER)
                .phoneNumber("05436567786")
                .latitude(14.265)
                .longitude(13.635)
                .sundayOpen(false)
                .password("pass123456")
                .build();
        underTest.save(partner);

        Partner existPartner = new Partner.builder()
                .uid("prestijAuto")
                .name("Prestige Auto")
                .displayName("Prestige Auto")
                .role(Role.PARTNER)
                .phoneNumber("05432126754")
                .latitude(18.265)
                .longitude(11.635)
                .sundayOpen(false)
                .password("pass123456")
                .build();
        //When
        //Then
        assertThatThrownBy(()->underTest.save(existPartner))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement [Unique index or primary key violation");

    }

    @ParameterizedTest
    @MethodSource("checkPartnerNullProperties")
    @DisplayName("When partner's name, displayName, latitude and longitude are null that throw an error")
    void itShouldNotSavePartnerWhenNameNull(Partner partner, String expectedMessage) {

        assertThatThrownBy(()->underTest.save(partner))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining(expectedMessage);

    }
    static Stream<Arguments> checkPartnerNullProperties() {
        return Stream.of(
                Arguments.of(
                        new Partner.builder()
                                        .uid("prestijAuto")
                                        .name(null)
                                        .displayName("Prestij Auto")
                                        .role(Role.PARTNER)
                                        .phoneNumber("05436567786")
                                        .latitude(14.265)
                                        .longitude(13.635)
                                        .sundayOpen(false)
                                        .password("pass123456")
                                        .build(),
                                    "not-null property references a null or transient value : com.ucelebi.automobile.model.Partner.name"),
                Arguments.of(
                        new Partner.builder()
                                    .uid("prestijAuto")
                                    .name("Prestige Auto")
                                    .displayName(null)
                                    .role(Role.PARTNER)
                                    .phoneNumber("05436567786")
                                    .latitude(14.265)
                                    .longitude(13.635)
                                    .sundayOpen(false)
                                    .password("pass123456")
                                    .build(),
                        "not-null property references a null or transient value : com.ucelebi.automobile.model.Partner.displayName"),
                Arguments.of(
                        new Partner.builder()
                                .uid("prestijAuto")
                                .name("Prestige Auto")
                                .displayName("Prestige Auto")
                                .role(Role.PARTNER)
                                .phoneNumber("05436567786")
                                .latitude(null)
                                .longitude(13.635)
                                .sundayOpen(false)
                                .password("pass123456")
                                .build(),
                        "not-null property references a null or transient value : com.ucelebi.automobile.model.Partner.latitude"),
                Arguments.of(
                        new Partner.builder()
                                .uid("prestijAuto")
                                .name("Prestige Auto")
                                .displayName("Prestige Auto")
                                .role(Role.PARTNER)
                                .phoneNumber("05436567786")
                                .latitude(14.265)
                                .longitude(null)
                                .sundayOpen(false)
                                .password("pass123456")
                                .build(),
                        "not-null property references a null or transient value : com.ucelebi.automobile.model.Partner.longitude")
        );
    }


    @Test
    void itShouldSelectPartnerByUid() {
        // Given
        String partnerUid = "prestijAuto";
        Partner partner = new Partner.builder()
                .uid(partnerUid)
                .name("Prestij Auto")
                .displayName("Prestij Auto")
                .role(Role.PARTNER)
                .phoneNumber("05436567786")
                .latitude(14.265)
                .longitude(13.635)
                .sundayOpen(false)
                .password("pass123456")
                .build();
        underTest.save(partner);

        //When
        Optional<Partner> optionalPartner = underTest.findByUid(partnerUid);

        //Then

        assertThat(optionalPartner)
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p).usingRecursiveComparison().isEqualTo(partner));
    }

    @Test
    void itShouldNotSelectPartnerWhenUidDoesNotExist() {
        //Given
        Partner partner = new Partner.builder()
                .uid("prestigeAuto")
                .name("Prestige Auto")
                .displayName("Prestige Auto")
                .role(Role.PARTNER)
                .phoneNumber("05436567786")
                .latitude(14.265)
                .longitude(13.635)
                .sundayOpen(false)
                .password("pass123456")
                .build();
        underTest.save(partner);

        //When
        //Then
        Optional<Partner> optionalPartner = underTest.findByUid("OtoTamirciniz");
        assertThat(optionalPartner).isNotPresent();
    }

    @Test
    void itShouldSelectPartnerByLatitudeAndLongitudeOrderByDistance() {
        //Given
        Partner partner1 = new Partner.builder()
                .uid("prestigeAuto")
                .name("Prestige Auto")
                .displayName("Prestige Auto")
                .role(Role.PARTNER)
                .phoneNumber("05436567786")
                .latitude(14.265)
                .longitude(13.635)
                .sundayOpen(false)
                .password("pass123456")
                .build();

        Partner partner2 = new Partner.builder()
                .uid("prestigeAuto2")
                .name("Prestige Auto")
                .displayName("Prestige Auto")
                .role(Role.PARTNER)
                .phoneNumber("05436567786")
                .latitude(45.265)
                .longitude(23.635)
                .sundayOpen(false)
                .password("pass123456")
                .build();

        Partner partner3 = new Partner.builder()
                .uid("prestigeAuto3")
                .name("Prestige Auto")
                .displayName("Prestige Auto")
                .role(Role.PARTNER)
                .phoneNumber("05436567786")
                .latitude(58.265)
                .longitude(23.635)
                .sundayOpen(false)
                .password("pass123456")
                .build();

        List<Partner> partners = Arrays.asList(partner1, partner2, partner3);

        underTest.saveAll(partners);

        //When
        Page<PartnerListDTO> partnersByDistance = underTest.findAllByDistance(45.300, 25.444, PageRequest.of(0, 5));

        //Then
        assertEquals(3,partnersByDistance.getTotalElements());

        // distance of partner2 is closest
        assertThat(partnersByDistance.getContent().get(0)).usingRecursiveComparison()
                .ignoringFields("distance")
                .isEqualTo(partner2);
    }

}