package com.ucelebi.automobile.api;

import com.ucelebi.automobile.auth.PartnerRegisterRequest;
import com.ucelebi.automobile.dto.PartnerDTO;
import com.ucelebi.automobile.dto.PartnerListDTO;
import com.ucelebi.automobile.dto.PartnerUpdateDTO;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.enums.UserType;
import com.ucelebi.automobile.utils.JsonMapperUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    private PartnerRegisterRequest registerRequestOne;
    private PartnerRegisterRequest registerRequestTwo;
    private PartnerRegisterRequest registerRequestThree;

    @BeforeAll
    void setUp() throws Exception {
        // Given
        registerRequestOne = new PartnerRegisterRequest("Yilmaz Oto Egzoz",
                "Yilmam Egzoz",
                "yilmazOtoEgzoz",
                "password12345",
                Role.PARTNER,
                "05345556677",
                "yilmazotoegzoz@gmail.com",
                UserType.KURUMSAL,
                48.234,
                26.356,
                false,
                null,
                null,
                null);
        registerRequestTwo = new PartnerRegisterRequest("Kardesler Motor Mekanik",
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
                null,
                null,
                null);

        registerRequestThree = new PartnerRegisterRequest("Guven Oto servis",
                "Guven OTO servis",
                "guvenOto",
                "password.1234",
                Role.PARTNER,
                "05334442299",
                "guvenOto@gmail.com",
                UserType.KURUMSAL,
                39.126,
                28.453,
                false,
                null,
                null,
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
    @DisplayName("It should list from the closest partner to the longest partner by location.")
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

        assertThat(JsonMapperUtil.jsonToObject(content.get(1).toString(),PartnerListDTO.class))
                .usingRecursiveComparison()
                .comparingOnlyFields("latitude","displayName","longitude")
                .isEqualTo(registerRequestThree);
    }

    @Test
    @DisplayName("It should list from the closest partner to the longest partner by location with page number and size.")
    void itShouldSelectPartnerWithLocationAndPageSize() throws Exception{
        //When
        MvcResult result = mockMvc.perform(get("/api/v1/partners?latitude=42.456&longitude=28.452&page=1&size=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //Then
        MockHttpServletResponse response = result.getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        assertThat(jsonObject.get("totalPages")).isEqualTo(3);
        assertThat(jsonObject.get("totalElements")).isEqualTo(3);
        assertThat(jsonObject.get("number")).isEqualTo(1);
        assertThat(jsonObject.get("size")).isEqualTo(1);
        assertThat(jsonObject.get("content")).isNotNull();
        JSONArray content = (JSONArray) jsonObject.get("content");
        assertThat(JsonMapperUtil.jsonToObject(content.get(0).toString(),PartnerListDTO.class))
                .usingRecursiveComparison()
                .comparingOnlyFields("latitude","displayName","longitude")
                .isEqualTo(registerRequestThree);
    }

    @Test
    void itShouldSelectPartnerByUid() throws Exception {
        //When
        MvcResult result = mockMvc.perform(get("/api/v1/partners/details?username=yilmazOtoEgzoz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //Then
        MockHttpServletResponse response = result.getResponse();
        PartnerDTO customerResponse = (PartnerDTO) JsonMapperUtil.jsonToObject(response.getContentAsString(), PartnerDTO.class);
        assertThat(customerResponse).isNotNull();
        assertThat(customerResponse)
                .usingRecursiveComparison()
                .comparingOnlyFields("name","displayName","phoneNumber","mail","latitude","longitude")
                .isEqualTo(registerRequestOne);
    }

    @Test
    void itShouldAddProfilePhotoSuccessfully() throws Exception {
        MockMultipartFile photo = new MockMultipartFile("photo","profilPhoto","image/png",new byte[4]);
        mockMvc.perform(multipart("/api/v1/partners/add-pp?uid=yilmazOtoEgzoz").file(photo))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void itShouldNotAddProfilePhotoWhenUserNotFound() throws Exception {
        MockMultipartFile photo = new MockMultipartFile("photo","profilPhoto","image/png",new byte[4]);
        mockMvc.perform(multipart("/api/v1/partners/add-pp?uid=yilmazOtoE").file(photo))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Fail"));
    }

    @Test
    void itShouldUpdatePartnerSuccessfully() throws Exception {
        PartnerUpdateDTO partnerDTO = new PartnerUpdateDTO(false,
                "motor.kardesler",
                "Kardesler Motor Mekanik",
                "Kardesler Mekanik",
                "05556667788",
                "/profile-photo/motor.kardesler.png",
                42.567,
                28.390,
                null,
                Arrays.asList("Haftaici 10:00 - 20:00", "Haftasonu 10:00 - 18:00"),
                null,
                null,
                false,
                Role.ADMIN);
        MvcResult result = mockMvc.perform(post("/api/v1/partners/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JsonMapperUtil.objectToJson(partnerDTO))))
                .andExpect(status().isOk())
                .andReturn();
        PartnerDTO response = (PartnerDTO) JsonMapperUtil.jsonToObject(result.getResponse().getContentAsString(),PartnerDTO.class);

        assertThat(response).usingRecursiveComparison()
                .comparingOnlyFields("active",
                        "uid",
                        "name",
                        "displayName",
                        "phoneNumber",
                        "latitude",
                        "longitude",
                        "openingTimes",
                        "sundayOpen")
                .isEqualTo(partnerDTO);
    }

    @Test
    void itShouldNotUpdatePartnerWhenUidDoesNotExist() throws Exception {
        PartnerUpdateDTO partnerDTO = new PartnerUpdateDTO(false,
                "motor.kardesler12",
                "Kardesler Motor Mekanik",
                "Kardesler Mekanik",
                "05556667788",
                "/profile-photo/motor.kardesler.png",
                42.567,
                28.390,
                null,
                Arrays.asList("Haftaici 10:00 - 20:00", "Haftasonu 10:00 - 18:00"),
                null,
                null,
                false,
                Role.ADMIN);
        MvcResult result = mockMvc.perform(post("/api/v1/partners/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JsonMapperUtil.objectToJson(partnerDTO))))
                .andExpect(status().isOk())
                .andReturn();
        PartnerDTO response = (PartnerDTO) JsonMapperUtil.jsonToObject(result.getResponse().getContentAsString(),PartnerDTO.class);

        assertThat(response).isNull();
    }
}