package com.ucelebi.automobile.facade.impl;

import com.ucelebi.automobile.dto.CustomerDTO;
import com.ucelebi.automobile.dto.CustomerListDTO;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.facade.CustomerFacade;
import com.ucelebi.automobile.model.Customer;
import com.ucelebi.automobile.service.CustomerService;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * User: ucelebi
 * Date: 13.06.2023
 * Time: 02:04
 */
class CustomerFacadeImplTest {

    private CustomerFacade underTest;
    @Mock
    private CustomerService customerService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CustomerFacadeImpl(customerService, modelMapper);
    }

    @Test
    void itShouldSaveCustomerSuccessfully() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        CustomerDTO customerDTO = new CustomerDTO(null,
                null,
                true,
                "umitclebi",
                "Ümit",
                "Ümit Çelebi",
                "05445556677",
                "/prpfil-photo/umitclebi.png",
                Role.CUSTOMER);
        Customer customer = new Customer.builder()
                .active(true)
                .uid("umitclebi")
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .phoneNumber("05445556677")
                .profilePhoto("/prpfil-photo/umitclebi.png")
                .role(Role.CUSTOMER)
                .build();
        Customer savedCustomer = new Customer.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("umitclebi")
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .phoneNumber("05445556677")
                .profilePhoto("/prpfil-photo/umitclebi.png")
                .role(Role.CUSTOMER)
                .build();
        CustomerDTO savedCustomerDTO = new CustomerDTO(createdTime,
                modifiedTime,
                true,
                "umitclebi",
                "Ümit",
                "Ümit Çelebi",
                "05445556677",
                "/prpfil-photo/umitclebi.png",
                Role.CUSTOMER);

        given(modelMapper.map(customerDTO,Customer.class)).willReturn(customer);
        given(customerService.save(customer)).willReturn(savedCustomer);
        given(modelMapper.map(savedCustomer,CustomerDTO.class)).willReturn(savedCustomerDTO);
        //When
        CustomerDTO result = underTest.save(customerDTO);

        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("creationTime","modifiedTime")
                .isEqualTo(customerDTO);
    }

    @Test
    void itShouldSelectAllCustomerAsCustomerDTO() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        Customer firstCustomer = new Customer.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("umitclebi")
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .phoneNumber("05445556677")
                .profilePhoto("/profile-photo/umitclebi.png")
                .role(Role.CUSTOMER)
                .build();
        Customer secondCustomer = new Customer.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("elf.clb")
                .name("Elif")
                .displayName("Elif Çelebi")
                .phoneNumber("05334442277")
                .profilePhoto("/profile-photo/elf.clb.png")
                .role(Role.CUSTOMER)
                .build();
        Customer thirtCustomer = new Customer.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("eminyilmaz")
                .name("Emin Yılmaz")
                .displayName("Emin Yılmaz")
                .phoneNumber("05344765588")
                .profilePhoto("/profile-photo/eminyilmaz.jpg")
                .role(Role.CUSTOMER)
                .build();
        CustomerListDTO firstCustomerDTO = new CustomerListDTO(createdTime,
                modifiedTime,
                true,
                "umitclebi",
                "Ümit Çelebi",
                "/profile-photo/umitclebi.png");
        CustomerListDTO secondCustomerDTO = new CustomerListDTO(createdTime,
                modifiedTime,
                true,
                "elf.clb",
                "Elif Çelebi",
                "/profile-photo/elf.clb.png");
        CustomerListDTO thirdCustomerDTO = new CustomerListDTO(createdTime,
                modifiedTime,
                true,
                "eminyilmaz",
                "Emin Yılmaz",
                "/profile-photo/eminyilmaz.jpg");

        given(customerService.findAll()).willReturn(Arrays.asList(firstCustomer,secondCustomer,thirtCustomer));
        given(modelMapper.map(firstCustomer,CustomerListDTO.class)).willReturn(firstCustomerDTO);
        given(modelMapper.map(secondCustomer,CustomerListDTO.class)).willReturn(secondCustomerDTO);
        given(modelMapper.map(thirtCustomer,CustomerListDTO.class)).willReturn(thirdCustomerDTO);

        List<CustomerListDTO> expected = Arrays.asList(firstCustomerDTO, secondCustomerDTO, thirdCustomerDTO);
        //When
        List<CustomerListDTO> result = underTest.findAll();

        //Then
        assertEquals(3,result.size());
        assertThat(result).isEqualTo(expected).isNotEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void itShouldSelectAllPagedCustomerAsCustomerDTO() {
        //Given
        Timestamp createdTime = Timestamp.from(Instant.now());
        Timestamp modifiedTime = Timestamp.from(Instant.now());

        Customer firstCustomer = new Customer.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("umitclebi")
                .name("Ümit")
                .displayName("Ümit Çelebi")
                .phoneNumber("05445556677")
                .profilePhoto("/profile-photo/umitclebi.png")
                .role(Role.CUSTOMER)
                .build();
        Customer secondCustomer = new Customer.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("elf.clb")
                .name("Elif")
                .displayName("Elif Çelebi")
                .phoneNumber("05334442277")
                .profilePhoto("/profile-photo/elf.clb.png")
                .role(Role.CUSTOMER)
                .build();
        Customer thirtCustomer = new Customer.builder()
                .creationTime(createdTime)
                .modifiedTime(modifiedTime)
                .active(true)
                .uid("eminyilmaz")
                .name("Emin Yılmaz")
                .displayName("Emin Yılmaz")
                .phoneNumber("05344765588")
                .profilePhoto("/profile-photo/eminyilmaz.jpg")
                .role(Role.CUSTOMER)
                .build();
        CustomerListDTO firstCustomerDTO = new CustomerListDTO(createdTime,
                modifiedTime,
                true,
                "umitclebi",
                "Ümit Çelebi",
                "/profile-photo/umitclebi.png");
        CustomerListDTO secondCustomerDTO = new CustomerListDTO(createdTime,
                modifiedTime,
                true,
                "elf.clb",
                "Elif Çelebi",
                "/profile-photo/elf.clb.png");
        CustomerListDTO thirdCustomerDTO = new CustomerListDTO(createdTime,
                modifiedTime,
                true,
                "eminyilmaz",
                "Emin Yılmaz",
                "/profile-photo/eminyilmaz.jpg");

        List<Customer> customerList = Arrays.asList(firstCustomer, secondCustomer, thirtCustomer);

        PageRequest pageable = PageRequest.of(0,5);
        given(customerService.findAll(pageable)).willReturn(new PageImpl<>(customerList,pageable,customerList.size()));
        given(modelMapper.map(firstCustomer,CustomerListDTO.class)).willReturn(firstCustomerDTO);
        given(modelMapper.map(secondCustomer,CustomerListDTO.class)).willReturn(secondCustomerDTO);
        given(modelMapper.map(thirtCustomer,CustomerListDTO.class)).willReturn(thirdCustomerDTO);

        List<CustomerListDTO> expected = Arrays.asList(firstCustomerDTO, secondCustomerDTO, thirdCustomerDTO);
        //When
        Page<CustomerListDTO> result = underTest.findAll(pageable);

        //Then
        assertEquals(3,result.getTotalElements());
        assertThat(result.getContent()).isEqualTo(expected).isNotEqualTo(Collections.EMPTY_LIST);
    }
}