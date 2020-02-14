package com.ing.githubrepo.service.base;

/**
 * Created by karamans on 13.02.2020.
 */
public enum EndpointType {

    PROD("PROD"),
    TEST("TEST");

    private String type;

    EndpointType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
