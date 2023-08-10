package com.boilerplate.bnppf.example.converter;

import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import org.springframework.core.convert.converter.Converter;

public class DemandToDemandDetailsConverter implements Converter<Demand, DemandDetails> {
    @Override
    public DemandDetails convert(Demand demand) {
        return DemandDetails.builder()
                .psp(demand.getPsp())
                .smid(demand.getSmid())
                .firstname(demand.getFirstname())
                .lastname(demand.getLastname())
                .birthdate(demand.getBirthdate())
                .civilStatus(demand.getCivilStatus())
                .maritalStatus(demand.getMaritalStatus())
                .phoneAddress(demand.getPhoneAddress())
                .domiciledIncome(demand.isDomiciledIncome())
                .build();
    }
}
