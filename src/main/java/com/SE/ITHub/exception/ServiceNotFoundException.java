package com.SE.ITHub.exception;

import java.util.UUID;


public class ServiceNotFoundException extends BaseException {
    public ServiceNotFoundException(String message) {
        super(message);
    }

    public ServiceNotFoundException(UUID id){
        super("Service Not found with Id : " + id);
    }
}
