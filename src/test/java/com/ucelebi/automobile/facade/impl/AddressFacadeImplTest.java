package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.AddressDTO;
import com.ucelebi.automobile.facade.AddressFacade;
import com.ucelebi.automobile.model.Address;
import com.ucelebi.automobile.model.City;
import com.ucelebi.automobile.model.Country;
import com.ucelebi.automobile.model.Town;
import com.ucelebi.automobile.service.AddressService;
import com.ucelebi.automobile.service.CityService;
import com.ucelebi.automobile.service.CountryService;
import com.ucelebi.automobile.service.TownService;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;

/**
 * User: ucelebi
 * Date: 9.06.2023
 * Time: 00:44
 */
class AddressFacadeImplTest {

    private AddressFacade underTest;

    @Mock
    private AddressService addressService;
    @Mock
    private CountryService countryService;
    @Mock
    private CityService cityService;
    @Mock
    private TownService townService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AddressFacadeImpl(addressService,countryService,cityService,townService,modelMapper);
    }

    @Test
    void itShouldSaveAddressSuccessfully() {
        //Given
        String postalCode = "34000";
        String streetName = "Osmanağa caddesi";
        String streetNumber = "387.sokak No:22";

        AddressDTO addressDTO = new AddressDTO(null, postalCode, "TR", "34", "3419", streetName, streetNumber,"");

        Address address = new Address(null,null,null,true,null, postalCode, null, null, null, streetName, streetNumber, "");
        given(modelMapper.map(addressDTO,Address.class))
                .willReturn(address);

        Country country = new Country("TR", "Türkiye");
        City city = new City("34", "Istanbul", null);
        Town town = new Town("3419", "Kadıköy", null);
        given(countryService.findCountryByCode("TR")).willReturn(Optional.of(country));
        given(cityService.findCityByCode("34")).willReturn(Optional.of(city));
        given(townService.findByCode("3419")).willReturn(Optional.of(town));

        Address savedAddress = new Address(1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                true,
                String.valueOf(System.currentTimeMillis()),
                address.getPostalCode(),
                country,
                city,
                town,
                address.getStreetName(),
                address.getStreetNumber(),
                address.getLine());

        given(addressService.save(address)).willReturn(savedAddress);
        given(modelMapper.map(savedAddress,AddressDTO.class))
                .willReturn(
                        new AddressDTO(savedAddress.getCode(),
                                savedAddress.getPostalCode(),
                                savedAddress.getCountry().getCode(),
                                savedAddress.getCity().getCode(),
                                savedAddress.getTown().getCode(),
                                savedAddress.getStreetName(),
                                savedAddress.getStreetNumber(),
                                savedAddress.getLine())
                );
        //When
        Address result = underTest.save(addressDTO);

        //Then
        then(addressService).should().save(address);
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("creationTime","modifiedTime","active","code")
                .isEqualTo(addressDTO);
    }

    @Test
    void itShouldSelectAllAddressAsAddressDTO() {
        //Given
        String postalCode = "34000";
        String streetName = "Osmanağa caddesi";
        String streetNumber = "387.sokak No:22";

        Country country = new Country("TR", "Türkiye");
        City city = new City("34", "Istanbul", null);
        Town town = new Town("3419", "Kadıköy", null);

        String firstAddressCode = String.valueOf(System.currentTimeMillis());
        Address firstAddress = new Address(1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                true,
                firstAddressCode,
                postalCode, country, city, town, streetName, streetNumber, "");

        String secondAddressCode = String.valueOf(System.currentTimeMillis());
        Address secondAddress = new Address(1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                true,
                secondAddressCode,
                "34222", country, city, town,
                "Sarıhasan mahallesi",
                "kutsal sokak No:3 D.6",
                "Supermarketin ust katı");


        AddressDTO firstAddressDTO = new AddressDTO(firstAddressCode, postalCode, country.getCode(), city.getCode(), town.getCode(), streetName, streetNumber,"");
        AddressDTO secondAddressDTO = new AddressDTO(secondAddressCode, "34222", country.getCode(), city.getCode(), town.getCode(), "Sarıhasan mahallesi", "kutsal sokak No:3 D.6","Supermarketin ust katı");

        given(addressService.findAll()).willReturn(List.of(firstAddress,secondAddress));
        given(modelMapper.map(firstAddress,AddressDTO.class))
                .willReturn(firstAddressDTO);
        given(modelMapper.map(secondAddress,AddressDTO.class))
                .willReturn(secondAddressDTO);

        //When
        List<AddressDTO> addressList = underTest.findAll();

        //Then
        assertEquals(2,addressList.size());
        assertEquals(firstAddressDTO,addressList.get(0));
        assertEquals(secondAddressDTO,addressList.get(1));
    }

    @Test
    void itShouldSelectAllAddressAsPageableAddressDTO() {
        //Given
        String postalCode = "34000";
        String streetName = "Osmanağa caddesi";
        String streetNumber = "387.sokak No:22";

        Country country = new Country("TR", "Türkiye");
        City city = new City("34", "Istanbul", null);
        Town town = new Town("3419", "Kadıköy", null);

        String firstAddressCode = String.valueOf(System.currentTimeMillis());
        Address firstAddress = new Address(1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                true,
                firstAddressCode,
                postalCode, country, city, town, streetName, streetNumber, "");

        String secondAddressCode = String.valueOf(System.currentTimeMillis());
        Address secondAddress = new Address(1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                true,
                secondAddressCode,
                "34222", country, city, town,
                "Sarıhasan mahallesi",
                "kutsal sokak No:3 D.6",
                "Supermarketin ust katı");


        AddressDTO firstAddressDTO = new AddressDTO(firstAddressCode, postalCode, country.getCode(), city.getCode(), town.getCode(), streetName, streetNumber,"");
        AddressDTO secondAddressDTO = new AddressDTO(secondAddressCode, "34222", country.getCode(), city.getCode(), town.getCode(), "Sarıhasan mahallesi", "kutsal sokak No:3 D.6","Supermarketin ust katı");


        PageRequest pageable = PageRequest.of(0, 5);
        given(addressService.findAll(pageable)).willReturn(new PageImpl<>(List.of(firstAddress,secondAddress),pageable,2));
        given(modelMapper.map(firstAddress,AddressDTO.class))
                .willReturn(firstAddressDTO);
        given(modelMapper.map(secondAddress,AddressDTO.class))
                .willReturn(secondAddressDTO);

        //When
        Page<AddressDTO> addressList = underTest.findAll(pageable);

        //Then
        assertEquals(2,addressList.getTotalElements());
        assertEquals(firstAddressDTO,addressList.getContent().get(0));
        assertEquals(secondAddressDTO,addressList.getContent().get(1));
    }

    @Test
    void itShouldUpdateAddressSuccessfully() {
        //Given
        String addressCode = String.valueOf(System.currentTimeMillis());
        String postalCode = "34000";
        String streetName = "Osmanağa caddesi";
        String streetNumber = "387.sokak No:22";

        Country country = new Country("TR", "Türkiye");
        City city = new City("34", "Istanbul", country);
        Town town = new Town("3419", "Kadıköy", city);

        AddressDTO addressDTO = new AddressDTO(addressCode, postalCode, "TR", "34", "3419", "New Street", "new street number","");
        addressDTO.setActive(false);
        Address address = new Address(1L,Timestamp.from(Instant.now()),Timestamp.from(Instant.now()),true,addressCode, postalCode, country, city, town, streetName, streetNumber, "");

        given(addressService.findByCode(addressCode)).willReturn(Optional.of(address));
        given(countryService.findCountryByCode(addressDTO.getCountryCode())).willReturn(Optional.of(country));
        given(cityService.findCityByCode(addressDTO.getCityCode())).willReturn(Optional.of(city));
        given(townService.findByCode(addressDTO.getTownCode())).willReturn(Optional.of(town));

        Address updatedAddress = new Address(1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                true,
                addressCode,
                address.getPostalCode(),
                country,
                city,
                town,
                addressDTO.getStreetName(),
                addressDTO.getStreetNumber(),
                addressDTO.getLine());

        given(addressService.update(address)).willReturn(updatedAddress);
        given(modelMapper.map(updatedAddress,AddressDTO.class))
                .willReturn(
                        new AddressDTO(updatedAddress.getCode(),
                                updatedAddress.getPostalCode(),
                                updatedAddress.getCountry().getCode(),
                                updatedAddress.getCity().getCode(),
                                updatedAddress.getTown().getCode(),
                                updatedAddress.getStreetName(),
                                updatedAddress.getStreetNumber(),
                                updatedAddress.getLine())
                );
        //When
        AddressDTO result = underTest.update(addressDTO);

        //Then
        then(addressService).should().update(address);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(addressDTO);
    }

    @Test
    void itShouldNotUpdateWhenAddressCodeDoesNotExist() {
        //Given
        String addressCode = String.valueOf(System.currentTimeMillis());
        String postalCode = "34000";
        String streetName = "Osmanağa caddesi";
        String streetNumber = "387.sokak No:22";

        AddressDTO addressDTO = new AddressDTO(addressCode, postalCode, "TR", "34", "3419", streetName, streetNumber,"");
        addressDTO.setActive(false);

        given(addressService.findByCode(addressCode)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.update(addressDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Guncellenecek adres bilgisi bulunamadi");
    }

    @Test
    void itShouldDeleteSuccessfully() {
        //Given
        String addressCode = String.valueOf(System.currentTimeMillis());
        String postalCode = "34000";
        String streetName = "Osmanağa caddesi";
        String streetNumber = "387.sokak No:22";

        AddressDTO addressDTO = new AddressDTO(addressCode, postalCode, "TR", "34", "3419", streetName, streetNumber,"");
        addressDTO.setActive(false);

        Address address = new Address(1L, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), true, addressCode, postalCode, null, null, null, streetName, streetName, "");
        given(addressService.findByCode(addressCode)).willReturn(Optional.of(address));
        //When
        underTest.delete(addressDTO);
        //Then
        then(addressService).should().delete(address);
    }

    @Test
    void itShouldNotDeleteWhenAddressCodeDoesNotExist() {
        //Given
        String addressCode = String.valueOf(System.currentTimeMillis());
        String postalCode = "34000";
        String streetName = "Osmanağa caddesi";
        String streetNumber = "387.sokak No:22";

        AddressDTO addressDTO = new AddressDTO(addressCode, postalCode, "TR", "34", "3419", streetName, streetNumber,"");
        addressDTO.setActive(false);

        given(addressService.findByCode(addressCode)).willReturn(Optional.empty());

        //When
        underTest.delete(addressDTO);
        //Then
        then(addressService).should(never()).delete(any());
    }

    @Test
    void itShouldNotDeleteWhenThrowException() {
        //Given
        String addressCode = String.valueOf(System.currentTimeMillis());
        String postalCode = "34000";
        String streetName = "Osmanağa caddesi";
        String streetNumber = "387.sokak No:22";

        AddressDTO addressDTO = new AddressDTO(addressCode, postalCode, "TR", "34", "3419", streetName, streetNumber,"");
        addressDTO.setActive(false);

        Address address = new Address(1L, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), true, addressCode, postalCode, null, null, null, streetName, streetName, "");
        given(addressService.findByCode(addressCode)).willReturn(Optional.of(address));

        doThrow(RuntimeException.class).when(addressService).delete(address);
        //When
        //Then
        underTest.delete(addressDTO);
    }

    @Test
    void itShouldSelectByAddressCode() {
        //Given
        String postalCode = "34000";
        String streetName = "Osmanağa caddesi";
        String streetNumber = "387.sokak No:22";

        Country country = new Country("TR", "Türkiye");
        City city = new City("34", "Istanbul", null);
        Town town = new Town("3419", "Kadıköy", null);

        String addressCode = String.valueOf(System.currentTimeMillis());
        Address address = new Address(1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                true,
                addressCode,
                postalCode, country, city, town, streetName, streetNumber, "");

        AddressDTO addressDTO = new AddressDTO(addressCode, postalCode, country.getCode(), city.getCode(), town.getCode(), streetName, streetNumber,"");

        given(addressService.findByCode(addressCode)).willReturn(Optional.of(address));
        given(modelMapper.map(address,AddressDTO.class))
                .willReturn(addressDTO);

        //When
        Optional<AddressDTO> optionalAddress = underTest.findByCode(addressCode);

        //Then
        assertThat(optionalAddress)
                .isPresent()
                .hasValueSatisfying(a->assertThat(a).usingRecursiveComparison().isEqualTo(addressDTO));
    }

    @Test
    void itShouldNotSelectByAddressCodeWhenAddressCodeDoesNotExist() {
        //Given
        String addressCode = "ADD23";
        given(addressService.findByCode(addressCode)).willReturn(Optional.empty());

        //When
        Optional<AddressDTO> optionalAddress = underTest.findByCode(addressCode);

        //Then
        assertThat(optionalAddress).isNotPresent();
    }

}