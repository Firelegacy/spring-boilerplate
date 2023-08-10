package com.boilerplate.bnppf.example.converter;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.NumberType;
import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.model.PhoneAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.GenericConversionService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DemandToDemandDetailsConverterTest {

    GenericConversionService conversionService = new GenericConversionService();

    @BeforeEach
    public void setup() {
        conversionService.addConverter(new DemandToDemandDetailsConverter());
    }

    @Test
    public void whenConvertDemandToDemandDetails_thenSuccess() {
        DemandDetails expectedDemandDetails = DemandDetails.builder()
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

        Demand toConvert = Demand.builder()
                .id(null)
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

        DemandDetails result = conversionService.convert(toConvert, DemandDetails.class);

        assertThat(result).usingRecursiveAssertion().isEqualTo(expectedDemandDetails);
    }

}