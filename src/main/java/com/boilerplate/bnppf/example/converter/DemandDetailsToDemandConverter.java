package com.boilerplate.bnppf.example.converter;

import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import org.springframework.core.convert.converter.Converter;

public class DemandDetailsToDemandConverter implements Converter<DemandDetails, Demand> {
    @Override
    public Demand convert(DemandDetails demandDetails) {
        return Demand.builder()
                .psp(demandDetails.getPsp())
                .smid(demandDetails.getSmid())
                .firstname(demandDetails.getFirstname())
                .lastname(demandDetails.getLastname())
                .birthdate(demandDetails.getBirthdate())
                .civilStatus(demandDetails.getCivilStatus())
                .maritalStatus(demandDetails.getMaritalStatus())
                .phoneAddress(demandDetails.getPhoneAddress())
                .domiciledIncome(demandDetails.isDomiciledIncome())
                .build();
    }
}
