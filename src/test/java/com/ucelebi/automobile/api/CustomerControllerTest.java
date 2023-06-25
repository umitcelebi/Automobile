package com.ucelebi.automobile.api;

import com.ucelebi.automobile.auth.CustomerRegisterRequest;
import com.ucelebi.automobile.auth.RegisterRequest;
import com.ucelebi.automobile.dto.CustomerDTO;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.enums.UserType;
import com.ucelebi.automobile.utils.JsonMapperUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: ucelebi
 * Date: 22.06.2023
 * Time: 07:28
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private CustomerRegisterRequest registerRequestTwo;
    @BeforeAll
    void setUp() throws Exception {
        // Given
        CustomerRegisterRequest registerRequestOne = new CustomerRegisterRequest("Umit Celebi",
                "Umit Celebi",
                "umitclebi",
                "password1234",
                Role.CUSTOMER,
                "05345556677",
                "umitclebi@gmail.com",
                UserType.BIREYSEL);
        registerRequestTwo = new CustomerRegisterRequest("Emin Yilmaz",
                "Emin Yilmaz",
                "emin.yilmaz",
                "password.12",
                Role.CUSTOMER,
                "05334446677",
                "emin.yilmaz@gmail.com",
                UserType.BIREYSEL);
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JsonMapperUtil.objectToJson(registerRequestOne))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JsonMapperUtil.objectToJson(this.registerRequestTwo))))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldSelectAllCustomer() throws Exception {

        //When
        MvcResult result = mockMvc.perform(get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        MockHttpServletResponse response = result.getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        assertThat(jsonObject.get("totalPages")).isEqualTo(1);
        assertThat(jsonObject.get("totalElements")).isEqualTo(2);
        assertThat(jsonObject.get("number")).isEqualTo(0);
        assertThat(jsonObject.get("size")).isEqualTo(30);
        assertThat(jsonObject.get("content")).isNotNull();
    }

    @Test
    void itShouldSelectAllCustomerWithPageAndSize() throws Exception {
        //When
        MvcResult result = mockMvc.perform(get("/api/v1/customers?page=0&size=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        MockHttpServletResponse response = result.getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        assertThat(jsonObject.get("totalPages")).isEqualTo(1);
        assertThat(jsonObject.get("totalElements")).isEqualTo(1);
        assertThat(jsonObject.get("number")).isEqualTo(0);
        assertThat(jsonObject.get("size")).isEqualTo(1);
        assertThat(jsonObject.get("content")).isNotNull();
    }

    @Test
    void itShouldSelectCustomerByUsernameParameter() throws Exception {
        //When
        MvcResult result = mockMvc.perform(get("/api/v1/customers/details?username=emin.yilmaz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        CustomerDTO response = (CustomerDTO) JsonMapperUtil.jsonToObject(
                result.getResponse().getContentAsString(), CustomerDTO.class);
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(RegisterRequest.class,CustomerDTO.class)
                .isEqualTo(registerRequestTwo);

    }

    @Test
    void itShouldNotSelectCustomerByUsernameParameterWhenUsernameDoesNotExist() throws Exception {
        //When
        MvcResult result = mockMvc.perform(get("/api/v1/customers/details?username=taylan_karaca")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        CustomerDTO response = (CustomerDTO) JsonMapperUtil.jsonToObject(
                result.getResponse().getContentAsString(), CustomerDTO.class);
        assertThat(response).isNull();
    }

    @Test
    void itShouldUpdateCustomerSuccessfully() throws Exception {
        //Given
        CustomerDTO updatableRegisterRequest = new CustomerDTO(Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                true,
                "umitclebi",
                "Celebi Umit",
                "umitclebi",
                "05345556677",
                "",
                Role.CUSTOMER);
        //When
        MvcResult result = mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(JsonMapperUtil.objectToJson(updatableRegisterRequest))))
                .andExpect(status().isOk())
                .andReturn();
        //Then
        CustomerDTO customerDTO = (CustomerDTO) JsonMapperUtil.jsonToObject(result.getResponse().getContentAsString(), CustomerDTO.class);

        assertThat(customerDTO).usingRecursiveComparison()
                .ignoringFields("creationTime","modifiedTime")
                .isEqualTo(updatableRegisterRequest);
    }

    @Test
    void itShouldNotUpdateCustomerWhenUidDoesNotExist() throws Exception {
        //Given
        CustomerDTO updatableRegisterRequest = new CustomerDTO(Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                true,
                "umit.clebi",
                "Celebi Umit",
                "umitclebi",
                "05345556677",
                "",
                Role.CUSTOMER);
        //When
        MvcResult result = mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JsonMapperUtil.objectToJson(updatableRegisterRequest))))
                .andExpect(status().isOk())
                .andReturn();
        //Then
        CustomerDTO customerDTO = (CustomerDTO) JsonMapperUtil.jsonToObject(result.getResponse().getContentAsString(), CustomerDTO.class);

        assertThat(customerDTO).isNull();
    }
}