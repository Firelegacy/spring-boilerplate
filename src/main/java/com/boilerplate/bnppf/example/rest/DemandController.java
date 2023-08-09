package com.boilerplate.bnppf.example.rest;

import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Validated
@RestController("demands")
public class DemandController {

    @Autowired
    private DemandService demandService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<UUID> saveDemand(DemandDetails demandDetails) {
        UUID demandId = this.demandService.saveNewDemand(demandDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(demandId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demand> getDemandById(@PathVariable UUID id) {
        Demand demand = demandService.getById(id);
        return ResponseEntity.ok(demand);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Demand>> getAllDemands() {
        List<Demand> demands = demandService.findAllDemands();
        return ResponseEntity.status(HttpStatus.OK).body(demands);
    }
}
