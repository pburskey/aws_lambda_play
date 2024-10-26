package com.burskey;

import com.amazonaws.services.lambda.runtime.Context;

public class Handler{
    public String handle(String s, Context context) {
        return java.util.UUID.randomUUID().toString();
    }


    protected String getFromEnvironment(String key){
        String value = System.getenv(key);
        return value;
    }
}
