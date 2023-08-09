package com.boilerplate.bnppf.example.exception;

import java.util.UUID;

public class DemandNotFoundException extends RuntimeException{

    public DemandNotFoundException(UUID demandId) {
        super("Could not find demand " + demandId);
    }
}
