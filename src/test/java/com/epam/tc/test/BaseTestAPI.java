package com.epam.tc.test;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.entities.CardEntity;
import com.epam.tc.entities.LabelEntity;
import com.epam.tc.entities.ListEntity;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTestAPI  {

    protected String boardName;
    protected String listName;
    protected String boardDescription;
    protected String cardName;
    protected String labelName;
    protected String labelColor;

    public static final String BASE_ENDPOINT = "https://api.trello.com";

    private String headerNameContentType = "Content-Type";
    private String expectedHeaderContentType = "application/json; charset=utf-8";

    BoardEntity boardEntity = new BoardEntity();
    ListEntity listEntity = new ListEntity();
    CardEntity cardEntity = new CardEntity();
    LabelEntity labelEntity = new LabelEntity();

    public static RequestSpecification baseRequestSpecification;
    public static ResponseSpecification okResponse;

    @BeforeClass
    public void baseSetup() {
        RestAssured.baseURI = BASE_ENDPOINT;


        baseRequestSpecification = new RequestSpecBuilder()
            .addQueryParam("key", System.getenv("key"))
            .addQueryParam("token", System.getenv("token"))
            .setContentType(ContentType.JSON)
            .build();

        okResponse = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(HttpStatus.SC_OK)
            .expectHeader(headerNameContentType, expectedHeaderContentType)
            .build();
    }

    @AfterClass
    public void teardown() {
        RestAssured.reset();
        baseRequestSpecification = null;
        okResponse = null;
    }

}
