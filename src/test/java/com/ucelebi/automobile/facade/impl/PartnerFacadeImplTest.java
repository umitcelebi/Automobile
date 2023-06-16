package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.PartnerDTO;
import com.ucelebi.automobile.dto.PartnerListDTO;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.facade.PartnerFacade;
import com.ucelebi.automobile.model.Partner;
import com.ucelebi.automobile.modelFilter.PartnerFilter;
import com.ucelebi.automobile.service.PartnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * User: ucelebi
 * Date: 14.06.2023
 * Time: 08:20
 */
class PartnerFacadeImplTest {
    private PartnerFacade underTest;
    @Mock
    private PartnerService partnerService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new PartnerFacadeImpl(partnerService, modelMapper);
    }

    @Test
    void itShouldSavePartnerSuccessfully() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        PartnerDTO partnerDTO = new PartnerDTO(null,
                null,
                true,
                "celebiOtoMekanik",
                "Celebi Oto Mekanik",
                "Celebi Oto Mekanik",
                "05446347799",
                "/profile-photo/celebiOtoMekanik/fotograf.png",
                0.0,
                42.345,
                28.546,
                null,
                null,
                Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"),
                null,
                null,
                false,
                Role.PARTNER);
        Partner partner = new Partner.builder()
                .creationTime(null)
                .modifiedTime(null)
                .active(true)
                .uid("celebiOtoMekanik")
                .name("Celebi Oto Mekanik")
                .displayName("Celebi Oto Mekanik")
                .phoneNumber("05446347799")
                .latitude(42.345)
                .longitude(28.546)
                .userRating(0.0)
                .openingTimes(Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"))
                .profilePhoto("/profile-photo/celebiOtoMekanik/fotograf.png")
                .sundayOpen(false)
                .role(Role.PARTNER)
                .build();
        Partner savedPartner = new Partner.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("celebiOtoMekanik")
                .name("Celebi Oto Mekanik")
                .displayName("Celebi Oto Mekanik")
                .phoneNumber("05446347799")
                .latitude(42.345)
                .longitude(28.546)
                .userRating(0.0)
                .openingTimes(Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"))
                .profilePhoto("/profile-photo/celebiOtoMekanik/fotograf.png")
                .sundayOpen(false)
                .role(Role.PARTNER)
                .build();
        PartnerDTO savedPartnerDTO = new PartnerDTO(createdTime,
                modifiedTime,
                true,
                "celebiOtoMekanik",
                "Celebi Oto Mekanik",
                "Celebi Oto Mekanik",
                "05446347799",
                "/profile-photo/celebiOtoMekanik/fotograf.png",
                0.0,
                42.345,
                28.546,
                null,
                null,
                Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"),
                null,
                null,
                false,
                Role.PARTNER);

        given(modelMapper.map(partnerDTO,Partner.class)).willReturn(partner);
        given(partnerService.save(partner)).willReturn(savedPartner);
        given(modelMapper.map(savedPartner,PartnerDTO.class)).willReturn(savedPartnerDTO);
        //When
        PartnerDTO result = underTest.save(partnerDTO);

        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("creationTime","modifiedTime")
                .isEqualTo(partnerDTO);
    }

    @Test
    void itShouldSelectAllPartnerAsPartnerDTO() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        Partner firstPartner = new Partner.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("celebiOtoMekanik")
                .name("Celebi Oto Mekanik")
                .displayName("Celebi Oto Mekanik")
                .phoneNumber("05446347799")
                .latitude(42.345)
                .longitude(28.546)
                .userRating(0.0)
                .openingTimes(Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"))
                .profilePhoto("/profile-photo/celebiOtoMekanik/fotograf.png")
                .sundayOpen(false)
                .role(Role.PARTNER)
                .build();
        Partner secondPartner = new Partner.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("yilmazEgzoz")
                .name("Yılmaz Oto Egzoz")
                .displayName("Yılmaz Oto Egzoz")
                .phoneNumber("05447634722")
                .latitude(42.345)
                .longitude(28.546)
                .userRating(0.0)
                .openingTimes(Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"))
                .profilePhoto("/profile-photo/yilmazEgzoz/fotograf.png")
                .sundayOpen(false)
                .role(Role.PARTNER)
                .build();

        PartnerListDTO firstPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "umitclebi",
                "Ümit Çelebi",
                "/profile-photo/umitclebi.png",
                0.0,
                42.546,
                28.456);
        PartnerListDTO secondPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "umitclebi",
                "Ümit Çelebi",
                "/profile-photo/umitclebi.png",
                0.0,
                42.546,
                28.456);

        given(partnerService.findAll()).willReturn(Arrays.asList(firstPartner,secondPartner));
        given(modelMapper.map(firstPartner,PartnerListDTO.class)).willReturn(firstPartnerDTO);
        given(modelMapper.map(secondPartner,PartnerListDTO.class)).willReturn(secondPartnerDTO);

        List<PartnerListDTO> expected = Arrays.asList(firstPartnerDTO, secondPartnerDTO);
        //When
        List<PartnerListDTO> result = underTest.findAll();

        //Then
        assertEquals(2,result.size());
        assertThat(result).isEqualTo(expected).isNotEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void itShouldSelectAllPagedPartnerAsPartnerListDTO() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        PartnerListDTO firstPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "umitclebi",
                "Ümit Çelebi",
                "/profile-photo/umitclebi.png",
                0.0,
                42.546,
                28.456);
        PartnerListDTO secondPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "umitclebi",
                "Ümit Çelebi",
                "/profile-photo/umitclebi.png",
                0.0,
                42.546,
                28.456);

        List<PartnerListDTO> partners = Arrays.asList(firstPartnerDTO, secondPartnerDTO);

        PageRequest pageable = PageRequest.of(0, 5);
        PartnerFilter partnerFilter = new PartnerFilter(null, null, null, 42.453, 28.546);

        given(partnerService.findAllByDistance(partnerFilter.getLatitude(), partnerFilter.getLongitude(), pageable)).willReturn(new PageImpl<>(partners, pageable, partners.size()));

        //When
        Page<PartnerListDTO> result = underTest.findAll(pageable, partnerFilter);

        //Then
        assertEquals(2,result.getTotalElements());
        assertThat(result.getContent()).isEqualTo(partners).isNotEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void itShouldSelectAllPagedPartnerBySector() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        PartnerListDTO firstPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "umitclebi",
                "Ümit Çelebi",
                "/profile-photo/umitclebi.png",
                0.0,
                42.546,
                28.456);
        PartnerListDTO secondPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "umitclebi",
                "Ümit Çelebi",
                "/profile-photo/umitclebi.png",
                0.0,
                42.546,
                28.456);

        List<PartnerListDTO> partners = Arrays.asList(firstPartnerDTO, secondPartnerDTO);

        PageRequest pageable = PageRequest.of(0, 5);
        String sectorCode = "EGZ";
        PartnerFilter partnerFilter = new PartnerFilter(sectorCode, null, null, 42.453, 28.546);

        given(partnerService.findAllBySectors(partnerFilter.getLatitude(), partnerFilter.getLongitude(), partnerFilter.getSectorCode(), pageable)).willReturn(new PageImpl<>(partners, pageable, partners.size()));

        //When
        Page<PartnerListDTO> result = underTest.findAll(pageable, partnerFilter);
        Page<PartnerListDTO> result2 = underTest.findAllBySectors(partnerFilter.getLatitude(), partnerFilter.getLongitude(), sectorCode, pageable);

        //Then
        assertEquals(2,result.getTotalElements());
        assertEquals(2,result2.getTotalElements());
        assertThat(result.getContent()).isEqualTo(partners).isNotEqualTo(Collections.EMPTY_LIST);
        assertThat(result).isEqualTo(result2).isNotEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void itShouldSelectAllPagedPartnerByTownCode() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        PartnerListDTO firstPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "eminYilmaz",
                "Emin Yılmaz",
                "/profile-photo/eminYilmaz/deneme.png",
                0.0,
                42.546,
                28.456);
        PartnerListDTO secondPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "Ahmet.Kara",
                "Ahmet Kara",
                "/profile-photo/Ahmet.Kara/photo.png",
                0.0,
                42.546,
                28.456);
        PartnerListDTO thirdPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "eymur.orhan",
                "Orhan Eymur",
                "/profile-photo/eymur.orhan/photo.png",
                0.0,
                42.546,
                28.456);

        List<PartnerListDTO> partners = Arrays.asList(firstPartnerDTO, secondPartnerDTO, thirdPartnerDTO);

        PageRequest pageable = PageRequest.of(0, 5);
        String townCode = "3427";
        PartnerFilter partnerFilter = new PartnerFilter(null, null, townCode, 42.453, 28.546);

        given(partnerService.findAllByTown(partnerFilter.getLatitude(), partnerFilter.getLongitude(), partnerFilter.getTownCode(), pageable)).willReturn(new PageImpl<>(partners, pageable, partners.size()));

        //When
        Page<PartnerListDTO> result = underTest.findAll(pageable, partnerFilter);
        Page<PartnerListDTO> result2 = underTest.findAllByTown(partnerFilter.getLatitude(), partnerFilter.getLongitude(), townCode, pageable);

        //Then
        assertEquals(3,result.getTotalElements());
        assertEquals(3,result2.getTotalElements());
        assertThat(result.getContent())
                .isEqualTo(partners)
                .isEqualTo(result2.getContent())
                .isNotEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void itShouldSelectAllPagedPartnerByCityCode() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        PartnerListDTO firstPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "selim_den",
                "Selim Deniz",
                "/profile-photo/selim_den/deneme.png",
                0.0,
                42.546,
                28.456);
        PartnerListDTO secondPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "SinanKurt",
                "Sinan Kurt",
                "/profile-photo/SinanKurt/photo.png",
                0.0,
                42.546,
                28.456);
        PartnerListDTO thirdPartnerDTO = new PartnerListDTO(createdTime,
                modifiedTime,
                true,
                "furkngokce",
                "Furkan Gokce",
                "/profile-photo/furkngokce/photo.png",
                0.0,
                42.546,
                28.456);

        List<PartnerListDTO> partners = Arrays.asList(firstPartnerDTO, secondPartnerDTO, thirdPartnerDTO);

        PageRequest pageable = PageRequest.of(0, 5);
        String cityCode = "34";
        PartnerFilter partnerFilter = new PartnerFilter(null, cityCode, null, 42.453, 28.546);

        given(partnerService.findAllByCity(partnerFilter.getLatitude(), partnerFilter.getLongitude(), partnerFilter.getCityCode(), pageable)).willReturn(new PageImpl<>(partners, pageable, partners.size()));

        //When
        Page<PartnerListDTO> result = underTest.findAll(pageable, partnerFilter);
        Page<PartnerListDTO> result2 = underTest.findAllByCity(partnerFilter.getLatitude(), partnerFilter.getLongitude(), cityCode, pageable);

        //Then
        assertEquals(3,result.getTotalElements());
        assertEquals(3,result2.getTotalElements());
        assertThat(result.getContent())
                .isEqualTo(partners)
                .isEqualTo(result2.getContent())
                .isNotEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void itShouldSelectPartnerByUid() {
        //Given
        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        String partnerUid = "umitclebi";
        Partner partner = new Partner.builder()
                .creationTime(creationTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid(partnerUid)
                .name("Celebi Oto Mekanik")
                .displayName("Celebi Oto Mekanik")
                .phoneNumber("05446347799")
                .latitude(42.345)
                .longitude(28.546)
                .userRating(0.0)
                .openingTimes(Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"))
                .profilePhoto("/profile-photo/celebiOtoMekanik/fotograf.png")
                .sundayOpen(false)
                .role(Role.PARTNER)
                .build();

        PartnerDTO partnerDTO = new PartnerDTO(creationTime,
                modifiedTime,
                true,
                partnerUid,
                "Oto Mekanik",
                "Oto Mekanik",
                "05446347799",
                "/profile-photo/celebiOtoMekanik/fotograf342.png",
                0.0,
                42.345,
                28.546,
                null,
                null,
                Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 13:00"),
                null,
                null,
                false,
                Role.PARTNER);

        given(partnerService.findByUid(partnerUid)).willReturn(Optional.ofNullable(partner));
        given(modelMapper.map(partner,PartnerDTO.class)).willReturn(partnerDTO);

        //When
        PartnerDTO result = underTest.findByUid(partnerUid);

        //Then
        assertEquals(partnerDTO,result);
    }

    @Test
    void itShouldNotSelectPartnerByUid() {
        //Given
        String partnerUid = "umitclebi";
        given(partnerService.findByUid(partnerUid)).willReturn(Optional.empty());

        //When
        PartnerDTO result = underTest.findByUid(partnerUid);

        //Then
        assertThat(result).isNull();
        verify(modelMapper,never()).map(any(),any());
    }

    @Test
    void itShouldUpdatePartnerSuccessfully() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        PartnerDTO partnerDTO = new PartnerDTO(createdTime,
                modifiedTime,
                true,
                "celebiOtoMekanik",
                "Oto Mekanik",
                "Oto Mekanik",
                "05446347799",
                "/profile-photo/celebiOtoMekanik/fotograf342.png",
                0.0,
                42.345,
                28.546,
                null,
                null,
                Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 13:00"),
                null,
                null,
                false,
                Role.PARTNER);

        Partner existPartner = new Partner.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("celebiOtoMekanik")
                .name("Celebi Oto Mekanik")
                .displayName("Celebi Oto Mekanik")
                .phoneNumber("05446347799")
                .latitude(42.345)
                .longitude(28.546)
                .userRating(0.0)
                .openingTimes(Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"))
                .profilePhoto("/profile-photo/celebiOtoMekanik/fotograf.png")
                .sundayOpen(false)
                .role(Role.PARTNER)
                .build();
        PartnerDTO updatedPartnerDTO = new PartnerDTO(createdTime,
                Timestamp.from(Instant.now()),
                true,
                "celebiOtoMekanik",
                "Oto Mekanik",
                "Oto Mekanik",
                "05446347799",
                "/profile-photo/celebiOtoMekanik/fotograf342.png",
                0.0,
                42.345,
                28.546,
                null,
                null,
                Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 13:00"),
                null,
                null,
                false,
                Role.PARTNER);
        given(partnerService.findByUid("celebiOtoMekanik")).willReturn(Optional.of(existPartner));
        given(partnerService.update(existPartner)).willReturn(existPartner);
        given(modelMapper.map(existPartner, PartnerDTO.class)).willReturn(updatedPartnerDTO);

        //When
        PartnerDTO result = underTest.update(partnerDTO);

        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("modifiedTime")
                .isEqualTo(partnerDTO);
    }

    @Test
    void itShouldNotUpdatePartnerWhenPartnerDoesNotExist() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        PartnerDTO partnerDTO = new PartnerDTO(createdTime,
                modifiedTime,
                true,
                "celebiOtoMekanik",
                "Oto Mekanik",
                "Oto Mekanik",
                "05446347799",
                "/profile-photo/celebiOtoMekanik/fotograf342.png",
                0.0,
                42.345,
                28.546,
                null,
                null,
                Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 13:00"),
                null,
                null,
                false,
                Role.PARTNER);

        given(partnerService.findByUid("celebiOtoMekanik")).willReturn(Optional.empty());

        //When
        PartnerDTO result = underTest.update(partnerDTO);

        //Then
        assertThat(result).isEqualTo(partnerDTO);
    }

    @Test
    void itShouldDeletePartnerByUidSuccessfully() {
        //Given
        String partnerUid = "umitcelebi55";
        //When
        underTest.deleteByUid(partnerUid);
        //Then
        verify(partnerService, Mockito.times(1)).deleteByUid(partnerUid);
        verifyNoMoreInteractions(partnerService);
    }

    @Test
    void itShouldNotDeletePartnerByUidWhenThrowException() {
        //Given
        String partnerUid = "umitcelebi55";
        doThrow(RuntimeException.class).when(partnerService).deleteByUid(partnerUid);
        //When
        underTest.deleteByUid(partnerUid);
        //Then
        verify(partnerService, Mockito.times(1)).deleteByUid(partnerUid);
        verifyNoMoreInteractions(partnerService);
    }

    @Test
    void itShouldDeletePartnerByPartnerDTOSuccessfully() {
        //Given
        String partnerUid = "celebiOtoMekanik";
        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());
        PartnerDTO partnerDTO = new PartnerDTO(creationTime,
                modifiedTime,
                true,
                partnerUid,
                "Oto Mekanik",
                "Oto Mekanik",
                "05446347799",
                "/profile-photo/celebiOtoMekanik/fotograf342.png",
                0.0,
                42.345,
                28.546,
                null,
                null,
                Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 13:00"),
                null,
                null,
                false,
                Role.PARTNER);

        Partner partner = new Partner.builder()
                .creationTime(creationTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid(partnerUid)
                .name("Celebi Oto Mekanik")
                .displayName("Celebi Oto Mekanik")
                .phoneNumber("05446347799")
                .latitude(42.345)
                .longitude(28.546)
                .userRating(0.0)
                .openingTimes(Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"))
                .profilePhoto("/profile-photo/celebiOtoMekanik/fotograf.png")
                .sundayOpen(false)
                .role(Role.PARTNER)
                .build();

        given(partnerService.findByUid(partnerUid)).willReturn(Optional.ofNullable(partner));

        //When
        underTest.delete(partnerDTO);

        //Then
        verify(partnerService,Mockito.times(1)).findByUid(partnerUid);
        verify(partnerService,Mockito.times(1)).delete(partner);
        verifyNoMoreInteractions(partnerService);
    }

    @Test
    void itShouldNotDeletePartnerByPartnerDTODoesNotExist() {
        //Given
        String partnerUid = "celebiOtoMekanik";
        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());
        PartnerDTO partnerDTO = new PartnerDTO(creationTime,
                modifiedTime,
                true,
                partnerUid,
                "Oto Mekanik",
                "Oto Mekanik",
                "05446347799",
                "/profile-photo/celebiOtoMekanik/fotograf342.png",
                0.0,
                42.345,
                28.546,
                null,
                null,
                Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 13:00"),
                null,
                null,
                false,
                Role.PARTNER);

        given(partnerService.findByUid(partnerUid)).willReturn(Optional.empty());

        //When
        underTest.delete(partnerDTO);

        //Then
        verify(partnerService,Mockito.times(1)).findByUid(partnerUid);
        verify(partnerService,never()).delete(any());
        verifyNoMoreInteractions(partnerService);
    }

    @Test
    void itShouldNotDeletePartnerByPartnerDTOWhenThrowException() {
        //Given
        String partnerUid = "celebiOtoMekanik";
        Timestamp creationTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());
        PartnerDTO partnerDTO = new PartnerDTO(creationTime,
                modifiedTime,
                true,
                partnerUid,
                "Oto Mekanik",
                "Oto Mekanik",
                "05446347799",
                "/profile-photo/celebiOtoMekanik/fotograf342.png",
                0.0,
                42.345,
                28.546,
                null,
                null,
                Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 13:00"),
                null,
                null,
                false,
                Role.PARTNER);

        Partner partner = new Partner.builder()
                .creationTime(creationTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid(partnerUid)
                .name("Celebi Oto Mekanik")
                .displayName("Celebi Oto Mekanik")
                .phoneNumber("05446347799")
                .latitude(42.345)
                .longitude(28.546)
                .userRating(0.0)
                .openingTimes(Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"))
                .profilePhoto("/profile-photo/celebiOtoMekanik/fotograf.png")
                .sundayOpen(false)
                .role(Role.PARTNER)
                .build();

        given(partnerService.findByUid(partnerUid)).willReturn(Optional.ofNullable(partner));
        doThrow(RuntimeException.class).when(partnerService).delete(partner);

        //When
        underTest.delete(partnerDTO);

        //Then
        verify(partnerService,Mockito.times(1)).findByUid(partnerUid);
        verify(partnerService,Mockito.times(1)).delete(partner);
    }

    @Test
    void itShouldAddProfilePhotoSuccessfully() {
        //Given
        String partnerUid = "umitclebi";
        MultipartFile multipartFile = new MockMultipartFile("fotograf.png","fotograf.png","image/png",new byte[60]);
        Partner partner = new Partner.builder()
                .creationTime(Timestamp.from(Instant.now()))
                .modifiedTime(Timestamp.from(Instant.now()))
                .active(true)
                .uid(partnerUid)
                .name("Celebi Oto Mekanik")
                .displayName("Celebi Oto Mekanik")
                .phoneNumber("05446347799")
                .latitude(42.345)
                .longitude(28.546)
                .userRating(0.0)
                .openingTimes(Arrays.asList("Haftaiçi 10:00 - 20:00", "Haftasonu 10:00 - 14:00"))
                .profilePhoto("/profile-photo/celebiOtoMekanik/fotograf.png")
                .sundayOpen(false)
                .role(Role.PARTNER)
                .build();

        given(partnerService.findByUid(partnerUid)).willReturn(Optional.ofNullable(partner));

        //When
        boolean result = underTest.addProfilePhoto(partnerUid, multipartFile);

        //Then
        assertThat(result).isTrue();
        verify(partnerService,Mockito.times(1)).findByUid(partnerUid);
        verify(partnerService,Mockito.times(1)).update(partner);

    }

    @ParameterizedTest
    @MethodSource("checkMultipartNullOrEmpty")
    void itShouldNotAddProfilePhotoWhenMultipartFileNullOrEmpty(String partnerUid, MultipartFile photo, boolean expected) {

        //When
        boolean result = underTest.addProfilePhoto(partnerUid, photo);
        //Then
        assertEquals(expected,result);
    }

    static Stream<Arguments> checkMultipartNullOrEmpty() {
        return Stream.of(
                Arguments.of("umitclebi",null,false),
                Arguments.of("eminyilmaz",new MockMultipartFile("photo2.png","photo2.png","image/png",new byte[0]),false)
        );
    }



    @Test
    void itShouldNotAddProfilePhotoWhenPartnerDoesNotExist() {
        //Given
        String partnerUid = "umitclebi";
        MultipartFile multipartFile = new MockMultipartFile("fotograf.png","fotograf.png","image/png",new byte[60]);

        given(partnerService.findByUid(partnerUid)).willReturn(Optional.empty());

        //When
        boolean result = underTest.addProfilePhoto(partnerUid, multipartFile);

        //Then
        assertThat(result).isFalse();
        verify(partnerService,Mockito.times(1)).findByUid(partnerUid);
        verify(partnerService,never()).update(any());
    }

    @Test
    void itShouldNotAddProfilePhotoWhenThrowException() {
        //Given
        String partnerUid = "umitclebi";
        MultipartFile multipartFile = new MockMultipartFile("fotograf.png","fotograf.png","image/png",new byte[60]);

        given(partnerService.findByUid(partnerUid)).willReturn(Optional.of(new Partner()));

        doThrow(RuntimeException.class).when(partnerService).update(any());

        //When
        underTest.addProfilePhoto(partnerUid,multipartFile);
        //Then
    }
}