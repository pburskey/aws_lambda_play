package com.burskey.property.api;

import com.burskey.property.dao.Dynamo;

public class AbstractLambda {


    protected static final String ENV_PROPERTY_TABLE = "PROPERTY_TABLE";
    protected final Dynamo dynamo = new Dynamo(getFromEnvironment(ENV_PROPERTY_TABLE));

    protected String getFromEnvironment(String key) {
        String value = System.getenv(key);
        return value;
    }
}
