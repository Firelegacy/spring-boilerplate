package com.boilerplate.bnppf.example.model;

import com.boilerplate.bnppf.example.enums.CivilStatus;
import com.boilerplate.bnppf.example.enums.MaritalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "demands")
public class Demand {

    @Id /* Mandatory annotation to indicate the primary key of an entity definition */
    @GeneratedValue(strategy = GenerationType.UUID) /*Type of generator used to generate the ID */
    private UUID id; /* Unique key generated on save of a new Demand in a UUID format */

    @Column(name = "psp", length = 8, unique = true, nullable = false)
    private String psp;

    @Column(name = "smid", length = 10, unique = true, nullable = false)
    private String smid;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "civilStatus", nullable = false)
    private CivilStatus civilStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "maritalStatus")
    private MaritalStatus maritalStatus;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "phoneType", column = @Column(name = "phone_type")),
            @AttributeOverride(name = "phone", column = @Column(name = "phone_number"))
    })
    private PhoneAddress phoneAddress;

    @Column(name = "domiciledIncome", nullable = false)
    private boolean domiciledIncome;
}
