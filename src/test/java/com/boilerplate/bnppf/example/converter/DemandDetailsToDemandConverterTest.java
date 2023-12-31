package com.boilerplate.bnppf.example.converter;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.MaritalStatus;
import com.boilerplate.bnppf.example.enums.NumberType;
import com.boilerplate.bnppf.example.model.Demand;
import com.boilerplate.bnppf.example.model.DemandDetails;
import com.boilerplate.bnppf.example.model.PhoneAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.support.GenericConversionService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DemandDetailsToDemandConverterTest {

    GenericConversionService conversionService = new GenericConversionService();

    @BeforeEach
    public void setup() {
        conversionService.addConverter(new DemandDetailsToDemandConverter());
    }

    @Test
    public void whenConvertDemandDetailsToDemand_thenSuccess() {
        Demand expectedDemand = Demand.builder()
                .id(null)
                .psp("2658")
                .smid("0123456789")
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.MARRIED)
                .maritalStatus(MaritalStatus.LEGAL_SYSTEM)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .domiciledIncome(true)
                .build();

        DemandDetails toConvert = DemandDetails.builder()
                .psp("2658")
                .smid("0123456789")
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.MARRIED)
                .maritalStatus(MaritalStatus.LEGAL_SYSTEM)
                .phoneAddress(PhoneAddress.builder()
                        .phoneType(NumberType.MOBILE)
                        .phoneNumber("+32470123045")
                        .build())
                .domiciledIncome(true)
                .build();

        Demand result = conversionService.convert(toConvert, Demand.class);

        assertThat(result).usingRecursiveAssertion().isEqualTo(expectedDemand);
    }
}