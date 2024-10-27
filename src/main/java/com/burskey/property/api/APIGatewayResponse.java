package com.burskey.property.api;

public class APIGatewayResponse {
    public Integer statusCode;
    public String body;

    public APIGatewayResponse(Integer statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }
}
