package com.boilerplate.bnppf.example.rest;

import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Validated
@RestController()
public class DemandController {

    @Autowired
    private DemandService demandService;

    @PostMapping(path = "/demands", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveDemand(@RequestBody DemandDetails demandDetails) {
        UUID demandId = this.demandService.saveNewDemand(demandDetails);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("demandId", demandId);
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @GetMapping(path = "/demands/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Demand> getDemandById(@PathVariable UUID id) {
        Demand demand = demandService.getById(id);
        return ResponseEntity.ok(demand);
    }

    @GetMapping(path = "/demands", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Demand>> getAllDemands() {
        List<Demand> demands = demandService.findAllDemands();
        return ResponseEntity.status(HttpStatus.OK).body(demands);
    }
}
