package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * User: ucelebi
 * Date: 4.06.2023
 * Time: 11:36
 */
@DataJpaTest
@ActiveProfiles("test")
class AddressDaoTest {

    @Autowired
    private AddressDao underTest;

    @Test
    void itShouldSaveAddressSuccessfully() {
        //Given
        Address address = new Address("ADR123",
                "34000",
                null,
                null,
                null,
                "Sehit Muhtar Cad.",
                "İmam Adnan Sk. No:44",
                "");
        //When
        Address savedAddress = underTest.save(address);

        //Then
        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isNotNull();
        Optional<Address> optionalAddress = underTest.findById(savedAddress.getId());
        assertThat(optionalAddress)
                .isPresent()
                .hasValueSatisfying(a -> assertThat(a)
                        .usingRecursiveComparison()
                        .ignoringFields("creationTime","modifiedTime")
                        .isEqualTo(address));

    }

    @Test
    void itShouldNotSaveAddressWhenCodeExist() {
        //Given
        String addressCode = "ADR123";
        Address address = new Address(addressCode,
                "34000",
                null,
                null,
                null,
                "Sehit Muhtar Cad.",
                "İmam Adnan Sk. No:44",
                "");
        underTest.save(address);

        Address testAddress = new Address(addressCode,
                "34332",
                null,
                null,
                null,
                "Merkez Mahallesi.",
                "387. Sk. No:45",
                "");
        //When
        //Then
        assertThatThrownBy(()->underTest.save(testAddress)).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement [Unique index or primary key violation");
    }

    @Test
    void itShouldNotSaveAddressWhenCodeNull() {
        //Given
        Address address = new Address(null,
                "34000",
                null,
                null,
                null,
                "Sehit Muhtar Cad.",
                "İmam Adnan Sk. No:44",
                "");

        //When
        //Then
        assertThatThrownBy(()->underTest.save(address)).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Address.code");
    }

    @Test
    void itShouldSelectAddressByCode() {
        //Given
        String addressCode = "BGC34";
        Address address = new Address(addressCode,
                "34000",
                null,
                null,
                null,
                "Sehit Muhtar Cad.",
                "İmam Adnan Sk. No:44",
                "");
        underTest.save(address);

        //When
        Optional<Address> optionalAddress = underTest.findByCode(addressCode);

        //Then
        assertThat(optionalAddress)
                .isPresent()
                .hasValueSatisfying(a-> assertThat(a)
                        .usingRecursiveComparison()
                        .ignoringFields("creationTime","modifiedTime")
                        .isEqualTo(address));

    }

    @Test
    void itShouldNotSelectAddressWhenAddressCodeDoesNotExist() {
        //Given
        //When
        Optional<Address> optionalAddress = underTest.findByCode("BG212");

        //Then
        assertThat(optionalAddress).isNotPresent();

    }
}