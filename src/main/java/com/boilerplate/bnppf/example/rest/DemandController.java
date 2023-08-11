package com.boilerplate.bnppf.example.rest;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.MaritalStatus;
import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.service.DemandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Validated /*Validated is used on class level when we use groups validations, else we can use @Valid directly at method level. See more: https://www.baeldung.com/spring-valid-vs-validated */
@RestController
public class DemandController {

    @Autowired
    private DemandService demandService;

    @PostMapping(path = "/demands", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveDemand(@Valid @RequestBody DemandDetails demandDetails) {
        UUID demandId = this.demandService.saveNewDemand(demandDetails);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("demandId", demandId);
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @GetMapping(path = "/demands/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Demand> getDemandById(@PathVariable(value = "id") UUID id) {
        Demand demand = demandService.getById(id);
        return ResponseEntity.ok(demand);
    }

    @GetMapping(path = "/demands", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Demand>> getAllDemands(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({@SortDefault(sort = "psp", direction = Sort.Direction.ASC)}) Pageable pageable,
            @RequestParam(name = "maritalStatus", required = false) MaritalStatus maritalStatus,
            @RequestParam(name = "civilStatus", required = false) CivilStatus civilStatus) {

        Page<Demand> resultingDemands;
        if (civilStatus != null) {
            if (maritalStatus != null) {
                resultingDemands = this.demandService.findAllDemandsByCivilStatusAndMaritalStatus(civilStatus, maritalStatus, pageable);
            } else {
                resultingDemands = demandService.findAllDemandsByCivilStatus(civilStatus, pageable);
            }
        } else {
            if (maritalStatus != null) {
                resultingDemands = this.demandService.findAllDemandsByMaritalStatus(maritalStatus, pageable);
            } else {
                resultingDemands = demandService.findAllDemands(pageable);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(resultingDemands);
    }
}
