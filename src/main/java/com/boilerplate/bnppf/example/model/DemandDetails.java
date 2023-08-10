package com.boilerplate.bnppf.example.model;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.MaritalStatus;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/* Validation examples https://www.baeldung.com/java-validation#:~:text=%40NotNull%20validates%20that%20the%20annotated,%2C%20Map%2C%20and%20array%20properties. */
@Builder
@Data
public class DemandDetails {

    @NotBlank
    @Size(min = 1, max = 8)
    private String psp;

    @NotBlank
    @Length(min = 10, max = 10)
    private String smid;

    @NotNull
    @Size(min = 2, max = 50)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastname;

    @NotNull
    @Past
    private LocalDate birthdate;

    @NotNull
    private CivilStatus civilStatus;

    private MaritalStatus maritalStatus;

    /* @Embedded is used with @Embeddable in the child class, it is used to declare a sub-object structurally (json structure)
     *
     * Note that the embedded properties will have the parent property name as a prefix like such: phone_address_number,
     * if you wish to remove it, you should add this annotation: @AttributeOverrides({ @AttributeOverride( name = "phoneNumber", column = @Column(name = "phone_address_number")) })
     *
     * More info: https://www.baeldung.com/jpa-embedded-embeddable
     * */
    @NotNull
    @Embedded
    private PhoneAddress phoneAddress;

    @NotNull
    private boolean domiciledIncome;

    @AssertTrue
    private boolean isAcceptedMaritalStatus() {
        if (civilStatus.equals(CivilStatus.MARRIED)) {
            return !maritalStatus.equals(MaritalStatus.SEPERATED_ESTATE) && !maritalStatus.equals(MaritalStatus.UNIVERSAL_COMMUNITY);
        } else {
            return true;
        }
    }
}
