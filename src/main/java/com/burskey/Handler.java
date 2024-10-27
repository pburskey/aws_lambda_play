package com.burskey;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler implements RequestHandler<String, String> {
    @Override
    public String handleRequest(String o, Context context) {
        return java.util.UUID.randomUUID().toString();
    }


    protected String getFromEnvironment(String key){
        String value = System.getenv(key);
        return value;
    }
}
