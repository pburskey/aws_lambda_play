package com.burskey.property.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.burskey.property.dao.Dynamo;

public class Save implements RequestHandler<APIGateWayRequest, APIGatewayResponse> {

    public static final String ENV_PROPERTY_TABLE= "PROPERTY_TABLE";
    private final Dynamo dynamo = new Dynamo(getFromEnvironment(ENV_PROPERTY_TABLE));

    @Override
    public APIGatewayResponse handleRequest(APIGateWayRequest o, Context context) {

        return new APIGatewayResponse(200, "trying to save....");

    }

    protected String getFromEnvironment(String key) {
        String value = System.getenv(key);
        return value;
    }
}
