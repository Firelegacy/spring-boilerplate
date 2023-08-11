package com.boilerplate.bnppf.example.rest;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.MaritalStatus;
import com.boilerplate.bnppf.example.enums.NumberType;
import com.boilerplate.bnppf.example.exception.DemandNotFoundException;
import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.model.PhoneAddress;
import com.boilerplate.bnppf.example.service.DemandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemandController.class)
class DemandControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DemandService demandService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
    } /* setup all mocks needed for all methods (common) */

    @Test
    void whenSaveDemand_givenValidDemand_thenShouldBeSuccess() throws Exception {
        DemandDetails demandDetails = DemandDetails.builder()
                .psp("2658")
                .smid("0123456789")
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.SINGLE)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .domiciledIncome(true)
                .build();

        UUID uuid = UUID.fromString("de1bf279-97f6-43de-9325-f31b99fb9199");

        when(demandService.saveNewDemand(demandDetails)).thenReturn(uuid);

        mockMvc.perform(post("/demands")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(demandDetails)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.demandId").value(uuid.toString()))
                .andDo(print());
    }

    @Test
    void whenSaveDemand_givenInvalidDemand_thenShouldBeBadRequest400() throws Exception {
        DemandDetails demandDetails = DemandDetails.builder()
                .psp("2658")
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .domiciledIncome(true)
                .build();

        mockMvc.perform(post("/demands")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(demandDetails)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void whenSaveDemand_givenInvalidDemand_thenShould405() throws Exception {
        DemandDetails demandDetails = DemandDetails.builder()
                .psp("2658")
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .domiciledIncome(true)
                .build();

        mockMvc.perform(put("/demands")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(demandDetails)))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());
    }


    @Test
    void whenGetDemandById_givenValidDemand_thenShouldBeSuccess() throws Exception {
        UUID id = UUID.fromString("af89d37d-e8d1-46db-8b08-6e3c34532a89");
        Demand demand = Demand.builder()
                .id(id)
                .psp("2658")
                .smid("0123456789")
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.SINGLE)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .domiciledIncome(true)
                .build();

        when(this.demandService.getById(id)).thenReturn(demand);

        mockMvc.perform(get("/demands/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.psp").value(demand.getPsp()))
                .andExpect(jsonPath("$.smid").value(demand.getSmid()))
                .andExpect(jsonPath("$.firstname").value(demand.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(demand.getLastname()))
                .andExpect(jsonPath("$.birthdate").value("2000-12-30"))
                .andExpect(jsonPath("$.civilStatus").value(demand.getCivilStatus().toString()))
                .andExpect(jsonPath("$.phoneAddress.phoneType").value(demand.getPhoneAddress().getPhoneType().toString()))
                .andExpect(jsonPath("$.phoneAddress.phoneNumber").value(demand.getPhoneAddress().getPhoneNumber()))
                .andExpect(jsonPath("$.domiciledIncome").value(demand.isDomiciledIncome()))
                .andDo(print());
    }

    @Test
    void whenGetDemandById_givenIdNotFound_thenShouldBeNotFound404() throws Exception {
        UUID id = UUID.fromString("af89d37d-e8d1-46db-8b08-6e3c34532a89");

        when(this.demandService.getById(id)).thenThrow(DemandNotFoundException.class);

        mockMvc.perform(get("/demands/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void whenGetAllDemands_givenNoParams_thenShouldBeSuccess() throws Exception {
        Demand demand1 = Demand.builder()
                .id(UUID.fromString("af89d37d-e8d1-46db-8b08-6e3c34532a89"))
                .psp("2658")
                .smid("0123456789")
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.MARRIED)
                .maritalStatus(MaritalStatus.LEGAL_SYSTEM)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .domiciledIncome(true)
                .build();

        Demand demand2 = Demand.builder()
                .id(UUID.fromString("af89d37d-e8d1-46db-8b08-6e3c34532ac5"))
                .psp("8")
                .smid("9876543210")
                .firstname("Charly")
                .lastname("Saroch")
                .birthdate(LocalDate.of(1995, 10, 12))
                .civilStatus(CivilStatus.SINGLE)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32489560023")
                        .build())
                .domiciledIncome(true)
                .build();

        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.asc("psp")));
        Page<Demand> demands = new PageImpl<>(List.of(demand1, demand2), pageable, 2);

        when(this.demandService.findAllDemands(pageable)).thenReturn(demands);

        mockMvc.perform(get("/demands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.size").value(pageable.getPageSize()))
                .andExpect(jsonPath("$.number").value(pageable.getPageNumber()))
                .andExpect(jsonPath("$.sort.sorted").value(true))
                .andDo(print());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(this.demandService).findAllDemands(pageableCaptor.capture());
        PageRequest pageableUsed = (PageRequest) pageableCaptor.getValue();

        assertThat(pageableUsed).usingRecursiveAssertion().isEqualTo(pageable);
    }

    @Test
    void whenGetAllDemands_givenPageableAndCivilStatusParameters_thenShouldBeSuccess() throws Exception {
        Demand demand = Demand.builder()
                .id(UUID.fromString("af89d37d-e8d1-46db-8b08-6e3c34532ac5"))
                .psp("8")
                .smid("9876543210")
                .firstname("Charly")
                .lastname("Saroch")
                .birthdate(LocalDate.of(1995, 10, 12))
                .civilStatus(CivilStatus.SINGLE)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32489560023")
                        .build())
                .domiciledIncome(true)
                .build();

        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.asc("psp")));
        Page<Demand> demands = new PageImpl<>(List.of(demand), pageable, 1);

        when(this.demandService.findAllDemandsByCivilStatus(CivilStatus.SINGLE, pageable)).thenReturn(demands);

        mockMvc.perform(get("/demands")
                        .param("page", "0")
                        .param("size", "2")
                        .param("civilStatus", "SINGLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.size").value(pageable.getPageSize()))
                .andExpect(jsonPath("$.number").value(pageable.getPageNumber()))
                .andExpect(jsonPath("$.sort.sorted").value(true))
                .andDo(print());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(this.demandService).findAllDemandsByCivilStatus(eq(CivilStatus.SINGLE), pageableCaptor.capture());
        PageRequest pageableUsed = (PageRequest) pageableCaptor.getValue();

        assertThat(pageableUsed).usingRecursiveAssertion().isEqualTo(pageable);
    }

    @Test
    void whenGetAllDemands_givenMaritalStatusParameters_thenShouldBeSuccess() throws Exception {
        Demand demand = Demand.builder()
                .id(UUID.fromString("af89d37d-e8d1-46db-8b08-6e3c34532a89"))
                .psp("2658")
                .smid("0123456789")
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.MARRIED)
                .maritalStatus(MaritalStatus.LEGAL_SYSTEM)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .domiciledIncome(true)
                .build();

        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.asc("psp")));
        Page<Demand> demands = new PageImpl<>(List.of(demand), pageable, 1);

        when(this.demandService.findAllDemandsByMaritalStatus(MaritalStatus.LEGAL_SYSTEM, pageable)).thenReturn(demands);

        mockMvc.perform(get("/demands")
                        .param("maritalStatus", "LEGAL_SYSTEM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.size").value(pageable.getPageSize()))
                .andExpect(jsonPath("$.number").value(pageable.getPageNumber()))
                .andExpect(jsonPath("$.sort.sorted").value(true))
                .andDo(print());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(this.demandService).findAllDemandsByMaritalStatus(eq(MaritalStatus.LEGAL_SYSTEM), pageableCaptor.capture());
        PageRequest pageableUsed = (PageRequest) pageableCaptor.getValue();

        assertThat(pageableUsed).usingRecursiveAssertion().isEqualTo(pageable);
    }

    @Test
    void whenGetAllDemands_givenCivilStatusAndMaritalStatusParameters_thenShouldBeSuccess() throws Exception {
        Demand demand = Demand.builder()
                .id(UUID.fromString("af89d37d-e8d1-46db-8b08-6e3c34532a89"))
                .psp("2658")
                .smid("0123456789")
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.MARRIED)
                .maritalStatus(MaritalStatus.LEGAL_SYSTEM)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .domiciledIncome(true)
                .build();

        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.asc("psp")));
        Page<Demand> demands = new PageImpl<>(List.of(demand), pageable, 1);

        when(this.demandService.findAllDemandsByCivilStatusAndMaritalStatus(CivilStatus.MARRIED, MaritalStatus.LEGAL_SYSTEM, pageable)).thenReturn(demands);

        mockMvc.perform(get("/demands")
                        .param("civilStatus", "MARRIED")
                        .param("maritalStatus", "LEGAL_SYSTEM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.size").value(pageable.getPageSize()))
                .andExpect(jsonPath("$.number").value(pageable.getPageNumber()))
                .andExpect(jsonPath("$.sort.sorted").value(true))
                .andDo(print());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(this.demandService).findAllDemandsByCivilStatusAndMaritalStatus(eq(CivilStatus.MARRIED), eq(MaritalStatus.LEGAL_SYSTEM), pageableCaptor.capture());
        PageRequest pageableUsed = (PageRequest) pageableCaptor.getValue();

        assertThat(pageableUsed).usingRecursiveAssertion().isEqualTo(pageable);
    }
}