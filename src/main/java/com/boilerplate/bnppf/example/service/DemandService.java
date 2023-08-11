package com.boilerplate.bnppf.example.service;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.MaritalStatus;
import com.boilerplate.bnppf.example.exception.DemandNotFoundException;
import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.repositories.DemandRepository;
import com.google.common.base.Preconditions;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/* Validation of parameters using Google Guava (https://www.baeldung.com/guava-preconditions)
 * Can also be done with an if clause instead and throwing an exception
 */
@Service
public class DemandService {

    @Autowired
    ConversionService conversionService;

    @Autowired
    private DemandRepository demandRepository;

    public UUID saveNewDemand(@Valid DemandDetails demandDetails) {
        /* Without Guava => if (demandDetails == null) throw new InvalidParameterException("demandDetails can't be null");*/
        Preconditions.checkArgument(demandDetails != null, "demandDetails can't be null");

        Demand demand = conversionService.convert(demandDetails, Demand.class);
        Preconditions.checkState(demand != null, "demand is null after conversion but shouldn't be");

        Demand savedDemand = this.demandRepository.save(demand);
        return savedDemand.getId();
    }

    public Demand getById(UUID id) {
        Preconditions.checkArgument(id != null, "id cannot be null");

        Optional<Demand> d = this.demandRepository.findById(id);
        return d.orElseThrow(() -> new DemandNotFoundException(id));
    }

    public Page<Demand> findAllDemands(Pageable pageable) {
        Preconditions.checkArgument(pageable != null, "pageable cannot be null");

        return this.demandRepository.findAll(pageable);
    }

    public Page<Demand> findAllDemandsByCivilStatus(CivilStatus civilStatus, Pageable pageable) {
        Preconditions.checkArgument(civilStatus != null, "civilStatus cannot be null");
        Preconditions.checkArgument(pageable != null, "pageable cannot be null");

        return this.demandRepository.findAllByCivilStatus(civilStatus, pageable);
    }

    public Page<Demand> findAllDemandsByMaritalStatus(MaritalStatus maritalStatus, Pageable pageable) {
        Preconditions.checkArgument(maritalStatus != null, "maritalStatus cannot be null");
        Preconditions.checkArgument(pageable != null, "pageable cannot be null");

        return this.demandRepository.findAllByMaritalStatus(maritalStatus, pageable);
    }

    public Page<Demand> findAllDemandsByCivilStatusAndMaritalStatus(CivilStatus civilStatus, MaritalStatus maritalStatus, Pageable pageable) {
        Preconditions.checkArgument(civilStatus != null, "civilStatus cannot be null");
        Preconditions.checkArgument(maritalStatus != null, "maritalStatus cannot be null");
        Preconditions.checkArgument(pageable != null, "pageable cannot be null");

        return this.demandRepository.findAllPeopleWithCivilStatusAndMaritalStatus(civilStatus, maritalStatus, pageable);
    }
}
