package com.boilerplate.bnppf.example.service;

import com.boilerplate.bnppf.example.exception.DemandNotFoundException;
import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.repositories.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DemandService {

    @Autowired
    ConversionService conversionService;

    @Autowired
    private DemandRepository demandRepository;

    public Demand getById(UUID id) {
        Optional<Demand> d = this.demandRepository.findById(id);
        return d.orElseThrow(() -> new DemandNotFoundException(id));
    }

    public List<Demand> findAllDemands() {
        return this.demandRepository.findAll();
    }

    public Page<Demand> findAllDemandsPaged() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "productId"));
        Pageable pageable = PageRequest.of(0, 5, sort);
        return this.demandRepository.findAll(pageable);
    }

    public List<Demand> findAllDemandsForAProduct(String productId) {
        return this.demandRepository.findAllByProductId(productId);
    }

    public UUID saveNewDemand(DemandDetails demandDetails) {
        if (demandDetails == null) throw new InvalidParameterException("demandDetails should not be empty");
        Demand demand = conversionService.convert(demandDetails, Demand.class);
        if (demand == null) throw new InvalidParameterException("Conversion to Demand went wrong");

        Demand savedDemand = this.demandRepository.save(demand);
        return savedDemand.getId();
    }
}
