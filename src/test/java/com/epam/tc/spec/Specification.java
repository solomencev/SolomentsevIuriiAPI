package com.epam.tc.spec;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class Specification {

    private static String headerNameContentType = "Content-Type";
    private static String expectedHeaderContentType = "application/json; charset=utf-8";

    public static final String BASE_ENDPOINT = "https://api.trello.com";

    public static RequestSpecification baseRequestSpecification = setUpRequest();
    public static ResponseSpecification okResponse = setUpResponse();

    private static RequestSpecification setUpRequest() {
        baseRequestSpecification = new RequestSpecBuilder()
            .setBaseUri(BASE_ENDPOINT)
            .addQueryParam("key", System.getenv("key"))
            .addQueryParam("token", System.getenv("token"))
            .setContentType(ContentType.JSON)
            .build();
        return baseRequestSpecification;
    }

    private static ResponseSpecification setUpResponse() {
        okResponse = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(HttpStatus.SC_OK)
            .expectHeader(headerNameContentType, expectedHeaderContentType)
            .build();
        return okResponse;
    }
}
