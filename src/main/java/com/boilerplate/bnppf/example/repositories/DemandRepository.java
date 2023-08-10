package com.boilerplate.bnppf.example.repositories;

import com.boilerplate.bnppf.example.enums.MaritalStatus;
import com.boilerplate.bnppf.example.model.Demand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/* Interface that you need to define to be able to access, read and write in your database instance.
 * In this project, I'll be setting up a H2 in-memory database.
 * You need to extend the relevant implementation of an existing repository (dependency : spring-boot-starter-data-jpa)
 *
 * As this is an interface and not a class, you only need to declare the methods, the body is already implemented by default in the interface you're extending
 * You can also redefine the behavior of these methods and add a body - the method then becomes 'default' - for example to add logging.
 *
 * There's also a way to define methods that are not yet present in the interface implemented that doesn't require to switch them to default methods either,
 * that mechanism is called a 'QueryMethod' and allows you to use the properties of an entity (see example below)
 * More info: https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
 * */
@Repository
public interface DemandRepository extends JpaRepository<Demand, UUID> {
    /* Find a particular demand and puts it in a specific Java object called Optional
    * That optional can be either empty - meaning no demand is found with the given ID
    * Or it can be filled - a demand was found */
    @Override
    Optional<Demand> findById(UUID uuid);

    @Override
    List<Demand> findAll();

    /* Finds demands saved with a limit of returned results + sorted in a particular way using one or more properties of the entity */
    @Override
    Page<Demand> findAll(Pageable pageable);

    /* Query method example - finds all entries with a given marital status */
    List<Demand> findAllByMaritalStatus(MaritalStatus maritalStatus);
}
