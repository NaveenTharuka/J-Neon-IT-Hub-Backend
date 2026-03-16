package com.SE.ITHub.exception;

import java.util.UUID;

public class PlanNotFoundException extends BaseException {
    public PlanNotFoundException(UUID id) {
        super("Plan Not found with id : " + id);
    }
}
