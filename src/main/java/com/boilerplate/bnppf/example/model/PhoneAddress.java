package com.boilerplate.bnppf.example.model;

import com.boilerplate.bnppf.example.enums.NumberType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* @Embedabble is used with @Embedded in the parent class, it is used to declare a sub-object structurally (json structure)
 * More info: https://www.baeldung.com/jpa-embedded-embeddable
 * */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PhoneAddress {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "phoneType", nullable = false)
    private NumberType phoneType;

    /* A regex is used to validate a string format, it's useful for phones, bank accounts, emails etc.
     * To find existing regexes, build your own and test it, use this website → https://regex101.com/
     */
    @NotNull
    @Column(name = "phoneNumber", nullable = false)
    @Pattern(regexp = "^(((\\+|00)32' '?(?:\\(0\\)' '?)?)|0)(4(60|[789]\\d)/?(\\s?\\d{2}\\.?){2}(\\s?\\d{2})|(\\d/?\\s?\\d{3}|\\d{2}/?\\s?\\d{2})(\\.?\\s?\\d{2}){2})$", message = "Invalid belgian phone number format")
    private String phoneNumber;
}
