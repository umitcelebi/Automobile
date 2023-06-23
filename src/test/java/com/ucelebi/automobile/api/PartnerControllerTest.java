package com.ucelebi.automobile.api;

import com.ucelebi.automobile.auth.RegisterRequest;
import com.ucelebi.automobile.dto.PartnerListDTO;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.enums.UserType;
import com.ucelebi.automobile.utils.JsonMapperUtil;
import org.json.JSONArray;
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

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: ucelebi
 * Date: 23.06.2023
 * Time: 22:58
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PartnerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private RegisterRequest registerRequestTwo;

    @BeforeAll
    void setUp() throws Exception {
        // Given
        RegisterRequest registerRequestOne = new RegisterRequest("Yılmaz Oto Egzoz",
                "Yılmam Egzoz",
                "yilmazOtoEgzoz",
                "password12345",
                Role.PARTNER,
                "05345556677",
                "yilmazotoegzoz@gmail.com",
                UserType.KURUMSAL,
                48.234,
                26.356,
                false,
                null);
        registerRequestTwo = new RegisterRequest("Kardesler Motor Mekanik",
                "Kardesler Motor Mekanik",
                "motor.kardesler",
                "password.12",
                Role.PARTNER,
                "05334446677",
                "motormekanikkardersler@gmail.com",
                UserType.KURUMSAL,
                42.456,
                28.453,
                false,
                null);

        RegisterRequest registerRequestThree = new RegisterRequest("Güven Oto servis",
                "Güven OTO servis",
                "guvenOto",
                "password.1234",
                Role.PARTNER,
                "05334442299",
                "guvenOto@gmail.com",
                UserType.KURUMSAL,
                39.126,
                28.453,
                false,
                null);
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JsonMapperUtil.objectToJson(registerRequestOne))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JsonMapperUtil.objectToJson(this.registerRequestTwo))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JsonMapperUtil.objectToJson(registerRequestThree))))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldSelectPartnerDefault() throws Exception{
        //When
        MvcResult result = mockMvc.perform(get("/api/v1/partners")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        //Then
        MockHttpServletResponse response = result.getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        assertThat(jsonObject.get("totalPages")).isEqualTo(1);
        assertThat(jsonObject.get("totalElements")).isEqualTo(3);
        assertThat(jsonObject.get("number")).isEqualTo(0);
        assertThat(jsonObject.get("size")).isEqualTo(30);
        assertThat(jsonObject.get("content")).isNotNull();
    }

    @Test
    void itShouldSelectPartnerWithLocation() throws Exception{
        //When
        MvcResult result = mockMvc.perform(get("/api/v1/partners?latitude=42.456&longitude=28.452")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //Then
        MockHttpServletResponse response = result.getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        assertThat(jsonObject.get("totalPages")).isEqualTo(1);
        assertThat(jsonObject.get("totalElements")).isEqualTo(3);
        assertThat(jsonObject.get("number")).isEqualTo(0);
        assertThat(jsonObject.get("size")).isEqualTo(30);
        assertThat(jsonObject.get("content")).isNotNull();
        JSONArray content = (JSONArray) jsonObject.get("content");
        assertThat(JsonMapperUtil.jsonToObject(content.get(0).toString(),PartnerListDTO.class))
                .usingRecursiveComparison()
                .comparingOnlyFields("latitude","displayName","longitude")
                .isEqualTo(registerRequestTwo);
    }
}