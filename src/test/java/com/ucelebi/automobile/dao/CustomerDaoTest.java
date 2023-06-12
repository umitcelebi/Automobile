package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.model.Customer;
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
 * Date: 5.06.2023
 * Time: 23:18
 */
@DataJpaTest
@ActiveProfiles("test")
class CustomerDaoTest {

    @Autowired
    private CustomerDao underTest;

    @Test
    void itShouldSaveCustomerSuccessfully() {
        //Given
        Customer customer = new Customer.builder()
                .uid("umitcelebi@mail.com")
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .password("password123")
                .role(Role.CUSTOMER)
                .phoneNumber("05344869880")
                .build();

        //When
        Customer savedCustomer = underTest.save(customer);

        //Then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();

        Optional<Customer> optionalCustomer = underTest.findById(savedCustomer.getId());

        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison().isEqualTo(customer));
    }

    @Test
    void itShouldNotSaveCustomerWhenUidNull() {
        Customer customer = new Customer.builder()
                .uid(null)
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .password("password123")
                .role(Role.CUSTOMER)
                .phoneNumber("05344869880")
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Customer.uid");

    }

    @Test
    void itShouldNotSaveCustomerWhenUidExist() {
        // Given
        Customer customer = new Customer.builder()
                .uid("umitcelebi@mail.com")
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .password("password123")
                .role(Role.CUSTOMER)
                .phoneNumber("05345556688")
                .build();
        underTest.save(customer);

        Customer existCustomer = new Customer.builder()
                .uid("umitcelebi@mail.com")
                .name("Celebi")
                .displayName("Celebi")
                .password("password123")
                .role(Role.CUSTOMER)
                .phoneNumber("05345556677")
                .build();
        //When
        //Then
        assertThatThrownBy(()->underTest.save(existCustomer))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement [Unique index or primary key violation");

    }

    @Test
    void itShouldNotSaveCustomerWhenNameNull() {
        //Given
        Customer customer = new Customer.builder()
                .uid("umitcelebi1")
                .name(null)
                .displayName("Ümit Çelebi")
                .password("password123")
                .role(Role.CUSTOMER)
                .phoneNumber("05342223355")
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Customer.name");

    }

    @Test
    void itShouldNotSaveCustomerWhenDisplayNameNull() {
        //Given
        Customer customer = new Customer.builder()
                .uid("umitcelebi1")
                .name("Ümit")
                .displayName(null)
                .password("password123")
                .role(Role.CUSTOMER)
                .phoneNumber("05342223355")
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Customer.displayName");

    }

    @Test
    void itShouldNotSaveCustomerWhenPasswordNull() {
        //Given
        Customer customer = new Customer.builder()
                .uid("umitcelebi1")
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .password(null)
                .role(Role.CUSTOMER)
                .phoneNumber("05342223355")
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Customer.password");

    }

    @Test
    void itShouldNotSaveCustomerWhenPhoneNumberNull() {
        //Given
        Customer customer = new Customer.builder()
                .uid("umitcelebi1")
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .password("12345678")
                .role(Role.CUSTOMER)
                .phoneNumber(null)
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : com.ucelebi.automobile.model.Customer.phoneNumber");

    }

    @Test
    void itShouldSelectCustomerByUid() {
        //Given
        String customerUid = "ucelebi";
        Customer customer = new Customer.builder()
                .uid(customerUid)
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .password("password123")
                .role(Role.CUSTOMER)
                .phoneNumber("05344869880")
                .build();

        //When
        underTest.save(customer);

        //Then
        Optional<Customer> optionalCustomer = underTest.findByUid(customerUid);

        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison().isEqualTo(customer));
    }

    @Test
    void itShouldNotSelectCustomerWhenUidDoesNotExist() {
        //Given
        Customer customer = new Customer.builder()
                .uid("ucelebi")
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .password("password123")
                .role(Role.CUSTOMER)
                .phoneNumber("05344869880")
                .build();
        underTest.save(customer);

        //When
        //Then
        Optional<Customer> optionalCustomer = underTest.findByUid("celeb");
        assertThat(optionalCustomer).isNotPresent();
    }

    @Test
    void itShouldDeleteCustomerByUid() {
        //Given
        String customerUid = "ucelebi";
        Customer customer = new Customer.builder()
                .uid(customerUid)
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .password("password123")
                .role(Role.CUSTOMER)
                .phoneNumber("05344869880")
                .build();
        underTest.save(customer);

        assertThat(underTest.findByUid(customerUid)).isPresent();

        //When
        underTest.deleteCustomerByUid(customerUid);

        //Then
        assertThat(underTest.findByUid(customerUid)).isNotPresent();

    }

}