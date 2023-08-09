package com.boilerplate.bnppf.example.rest;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.MaritalStatus;
import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.service.DemandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DemandControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    DemandController demandController;

    @Mock
    DemandService demandService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() { /* setup all mocks needed for all methods (common) */
        mockMvc = MockMvcBuilders.standaloneSetup(demandController).build();

        Demand demand1 = Demand.builder()
                .id(UUID.fromString("369971d5-45d8-4b28-b04b-98165ceeb143"))
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.SINGLE)
                .productId("DIRECT_RESERVE")
                .build();
        Demand demand2 = Demand.builder()
                .id(UUID.fromString("de1bf279-97f6-43de-9325-f31b99fb9199"))
                .firstname("Dani")
                .lastname("Solov")
                .birthdate(LocalDate.of(1990, 1, 22))
                .civilStatus(CivilStatus.MARRIED)
                .maritalStatus(MaritalStatus.LEGAL_SYSTEM)
                .productId("DIRECT_RESERVE")
                .build();
        List<Demand> demands = Arrays.asList(demand1, demand2);

        when(demandService.findAllDemands()).thenReturn(demands);
    }

    @Test
    void testSaveDemand_isOk() throws Exception {
        //given
        DemandDetails demandDetails = DemandDetails.builder()
                .firstname("Marcus")
                .lastname("Shire")
                .birthdate(LocalDate.of(1989, 5, 15))
                .civilStatus(CivilStatus.WIDOW)
                .productId("DIRECT_RESERVE")
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/demands")
                .content(objectMapper.writeValueAsString(demandDetails))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk());
    }

    @Test
    void getDemandById_isOk() {
    }

    @Test
    void getAllDemands_isOk() {

    }
}