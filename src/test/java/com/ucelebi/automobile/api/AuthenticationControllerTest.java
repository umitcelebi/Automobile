package com.ucelebi.automobile.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucelebi.automobile.auth.RegisterRequest;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.enums.UserType;
import com.ucelebi.automobile.model.User;
import com.ucelebi.automobile.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: ucelebi
 * Date: 18.06.2023
 * Time: 23:10
 */

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    void itShouldRegisterSuccessfully() throws Exception {
        //Given
        RegisterRequest request = new RegisterRequest("Ümit Çelebi",
                "Ümit Çelebi",
                "umitclebi3",
                "password1234",
                Role.CUSTOMER,
                "05345556677",
                "umitclebi@gmail.com",
                UserType.BIREYSEL,
                42.456,
                28.453,
                false,
                null);

        ResultActions registerResultActions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(request))));

        registerResultActions.andExpect(status().isOk());

        Optional<User> optionalUser = userService.findUserByUid("umitclebi");
        assertThat(optionalUser)
                .isPresent()
                .hasValueSatisfying(u -> assertThat(u)
                        .usingRecursiveComparison()
                        .comparingOnlyFields("name","displayName")
                        .isEqualTo(request));

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