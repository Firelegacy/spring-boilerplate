package com.boilerplate.bnppf.example.model;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.MaritalStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/* Validation examples https://www.baeldung.com/java-validation#:~:text=%40NotNull%20validates%20that%20the%20annotated,%2C%20Map%2C%20and%20array%20properties. */
@Builder
@Data
public class DemandDetails {
    @NotBlank
    private String productId;

    @Size(min = 2)
    private String firstname;

    @Size(min = 2)
    private String lastname;

    @NotNull
    @Past
    private LocalDate birthdate;

    @NotNull
    private CivilStatus civilStatus;

    private MaritalStatus maritalStatus;

    @AssertTrue
    private boolean isAcceptedMaritalStatus() {
        if (civilStatus.equals(CivilStatus.MARRIED)) {
            return !maritalStatus.equals(MaritalStatus.SEPERATED_ESTATE) && !maritalStatus.equals(MaritalStatus.UNIVERSAL_COMMUNITY);
        } else {
            return true;
        }
    }
}
