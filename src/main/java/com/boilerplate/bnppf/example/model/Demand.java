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
public class Demand {

    @Id /* Mandatory annotation to indicate the primary key of an entity definition */
    @GeneratedValue(strategy = GenerationType.UUID) /*Type of generator used to generate the ID */
    private UUID id; /* Unique key generated on save of a new Demand in a UUID format */

    private String productId;

    private String firstname;

    private String lastname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private CivilStatus civilStatus;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
}
