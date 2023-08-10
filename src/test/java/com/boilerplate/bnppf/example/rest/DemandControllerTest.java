package com.boilerplate.bnppf.example.rest;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.MaritalStatus;
import com.boilerplate.bnppf.example.enums.NumberType;
import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.model.PhoneAddress;
import com.boilerplate.bnppf.example.service.DemandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void setup() { /* setup all mocks needed for all methods (common) */

    }

    @Test
    void whenSaveDemand_givenValidDemand_thenShouldBeSuccess() throws Exception {
        DemandDetails demandDetails = DemandDetails.builder()
                .firstname("Marcus")
                .lastname("Shire")
                .birthdate(LocalDate.of(1989, 5, 15))
                .civilStatus(CivilStatus.WIDOW)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .productId("DIRECT_RESERVE")
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

    /* TODO complete later
    @Test
    void shouldReturnTutorial() throws Exception {
        long id = 1L;
        Tutorial tutorial = new Tutorial(id, "Spring Boot @WebMvcTest", "Description", true);

        when(tutorialRepository.findById(id)).thenReturn(Optional.of(tutorial));
        mockMvc.perform(get("/api/tutorials/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(tutorial.getTitle()))
                .andExpect(jsonPath("$.description").value(tutorial.getDescription()))
                .andExpect(jsonPath("$.published").value(tutorial.isPublished()))
                .andDo(print());
    }

    @Test
    void shouldReturnNotFoundTutorial() throws Exception {
        long id = 1L;

        when(tutorialRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/tutorials/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldReturnListOfTutorials() throws Exception {
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

        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial(1, "Spring Boot @WebMvcTest 1", "Description 1", true),
                        new Tutorial(2, "Spring Boot @WebMvcTest 2", "Description 2", true),
                        new Tutorial(3, "Spring Boot @WebMvcTest 3", "Description 3", true)));

        when(tutorialRepository.findAll()).thenReturn(tutorials);
        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()))
                .andDo(print());
    }

    @Test
    void shouldReturnListOfTutorialsWithFilter() throws Exception {
        List<Tutorial> tutorials = new ArrayList<>(
                Arrays.asList(new Tutorial(1, "Spring Boot @WebMvcTest", "Description 1", true),
                        new Tutorial(3, "Spring Boot Web MVC", "Description 3", true)));

        String title = "Boot";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("title", title);

        when(tutorialRepository.findByTitleContaining(title)).thenReturn(tutorials);
        mockMvc.perform(get("/api/tutorials").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()))
                .andDo(print());

        tutorials = Collections.emptyList();

        when(tutorialRepository.findByTitleContaining(title)).thenReturn(tutorials);
        mockMvc.perform(get("/api/tutorials").params(paramsMap))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void shouldReturnNoContentWhenFilter() throws Exception {
        String title = "BezKoder";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("title", title);

        List<Tutorial> tutorials = Collections.emptyList();

        when(tutorialRepository.findByTitleContaining(title)).thenReturn(tutorials);
        mockMvc.perform(get("/api/tutorials").params(paramsMap))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
    */
}