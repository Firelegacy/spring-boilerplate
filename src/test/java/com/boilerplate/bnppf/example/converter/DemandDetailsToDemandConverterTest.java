package com.boilerplate.bnppf.example.converter;

import com.boilerplate.bnppf.example.enums.CivilStatus;
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
        conversionService.addConverter(new DemandToDemandDetailsConverter());
    }

    @Test
    public void whenConvertDemandDetailsToDemand_thenSuccess() {
        Demand expectedDemand = Demand.builder()
                .id(null)
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.SINGLE)
                .phoneAddress(PhoneAddress.builder()
                        .type(NumberType.MOBILE)
                        .number("+32470123045")
                        .build())
                .productId("DIRECT_RESERVE")
                .build();

        DemandDetails toConvert = DemandDetails.builder()
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.SINGLE)
                .phoneAddress(PhoneAddress.builder()
                        .type(NumberType.MOBILE)
                        .number("+32470123045")
                        .build())
                .productId("DIRECT_RESERVE")
                .build();

        Demand result = conversionService.convert(toConvert, Demand.class);

        assertThat(result).usingRecursiveAssertion().isEqualTo(expectedDemand);
    }

    @Test
    public void whenConvertDemandToDemandDetails_thenSuccess() {
        DemandDetails expectedDemandDetails = DemandDetails.builder()
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.SINGLE)
                .phoneAddress(PhoneAddress.builder()
                        .type(NumberType.MOBILE)
                        .number("+32470123045")
                        .build())
                .productId("DIRECT_RESERVE")
                .build();

        Demand toConvert = Demand.builder()
                .id(null)
                .firstname("Louis")
                .lastname("Marcovic")
                .birthdate(LocalDate.of(2000, 12, 30))
                .civilStatus(CivilStatus.SINGLE)
                .phoneAddress(PhoneAddress.builder()
                        .type(NumberType.MOBILE)
                        .number("+32470123045")
                        .build())
                .productId("DIRECT_RESERVE")
                .build();

        DemandDetails result = conversionService.convert(toConvert, DemandDetails.class);

        assertThat(result).usingRecursiveAssertion().isEqualTo(expectedDemandDetails);
    }
}