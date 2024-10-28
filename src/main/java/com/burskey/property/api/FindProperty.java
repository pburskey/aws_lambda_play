package com.burskey.property.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.burskey.property.dao.Dynamo;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class FindProperty implements RequestHandler<APIGateWayRequest, APIGatewayResponse> {

    public static final String ENV_PROPERTY_TABLE= "PROPERTY_TABLE";
    private final Dynamo dynamo = new Dynamo(getFromEnvironment(ENV_PROPERTY_TABLE));

    @Override
    public APIGatewayResponse handleRequest(APIGateWayRequest request, Context context) {

        String name = request.queryStringParameters.get("name");
        String category = request.queryStringParameters.get("category");
        return new APIGatewayResponse(200, "Searching For Property..... name: " + name + " category: " + category);

    }

    protected String getFromEnvironment(String key) {
        String value = System.getenv(key);
        return value;
    }
}
