package com.ucelebi.automobile.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucelebi.automobile.auth.AuthenticationRequest;
import com.ucelebi.automobile.auth.AuthenticationResponse;
import com.ucelebi.automobile.auth.RegisterRequest;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.enums.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: ucelebi
 * Date: 18.06.2023
 * Time: 23:10
 */

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void itShouldRegisterSuccessfully() throws Exception {
        //Given
        String username = "umitclebi3";
        String password = "password1234";
        Role role = Role.CUSTOMER;
        RegisterRequest request = new RegisterRequest("Ümit Çelebi",
                "Ümit Çelebi",
                username,
                password,
                role,
                "05345556677",
                "umitclebi@gmail.com",
                UserType.BIREYSEL,
                42.456,
                28.453,
                false,
                null);

        AuthenticationResponse expectedResponse = new AuthenticationResponse(null, username, role);

        MvcResult registerResult = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(request))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        AuthenticationResponse actualResponse = (AuthenticationResponse) jsonToObject(registerResult.getResponse().getContentAsString(), AuthenticationResponse.class);

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .ignoringFields("token")
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("It should not Register When username, password, name, displayName, phoneNumber are null")
    void itShouldNotRegisterWhenUserPropertyNull() throws Exception {
        //Given
        String username = "umitclebi3";
        Role role = Role.CUSTOMER;
        RegisterRequest request = new RegisterRequest("Ümit Çelebi",
                "Ümit Çelebi",
                username,
                "password1234",
                role,
                null,
                "umitclebi@gmail.com",
                UserType.BIREYSEL,
                42.456,
                28.453,
                false,
                null);

        MvcResult registerResult = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(request))))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        AuthenticationResponse actualResponse = (AuthenticationResponse) jsonToObject(registerResult.getResponse().getContentAsString(), AuthenticationResponse.class);

        assertNull(actualResponse);
    }

    @Test
    void itShouldAuthenticateSuccessfully() throws Exception {
        //Given
        String username = "umitclebi";
        String password = "password1234";
        Role role = Role.CUSTOMER;
        RegisterRequest registerRequest = new RegisterRequest("Ümit Çelebi",
                "Ümit Çelebi",
                username,
                password,
                role,
                "05347773344",
                "umitclebi@gmail.com",
                UserType.BIREYSEL,
                42.456,
                28.453,
                false,
                null);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(registerRequest))))
                .andExpect(status().isOk())
                .andReturn();

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(username, password);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(null, username, role);

        //When
        MvcResult result = mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(authenticationRequest))))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        AuthenticationResponse response = (AuthenticationResponse) jsonToObject(result.getResponse().getContentAsString(), AuthenticationResponse.class);
        assertThat(response).usingRecursiveComparison()
                .ignoringFields("token")
                .isEqualTo(authenticationResponse);
        assertThat(response.getToken()).isNotNull();
    }

    @Test
    void itShouldNotAuthenticateWhenUsernameOrPasswordWrong() throws Exception {
        //Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("umitclebi", "password");

        //When
        MvcResult result = mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(authenticationRequest))))
                .andExpect(status().isUnauthorized())
                .andReturn();

        //Then
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Bad credentials");
    }

    private Object jsonToObject(String json, Class<?> type) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(json, type);
        } catch (JsonProcessingException e) {
            fail("Failed to convert json to object");
            return null;
        }
    }

    private String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to json");
            return null;
        }
    }
}