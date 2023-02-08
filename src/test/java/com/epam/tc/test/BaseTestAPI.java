package com.epam.tc.test;

import static com.epam.tc.utils.PropertiesExtractor.getApiKeyFromPropertiesExtractor;
import static com.epam.tc.utils.PropertiesExtractor.getApiTokenFromPropertiesExtractor;
import static io.restassured.RestAssured.given;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.entities.CardEntity;
import com.epam.tc.utils.Endpoints;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTestAPI {

    protected String expectedBoardName;
    protected RequestSpecification baseRequestSpecification;
    protected ResponseSpecification okResponse;
    protected ResponseSpecification notFoundResponse;
    BoardEntity board;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = Endpoints.BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        baseRequestSpecification = new RequestSpecBuilder()
            .addQueryParam("key", getApiKeyFromPropertiesExtractor())
            .addQueryParam("token", getApiTokenFromPropertiesExtractor())
            .setContentType(ContentType.JSON)
            .build();

        okResponse = new ResponseSpecBuilder()
            .expectStatusCode(HttpStatus.SC_OK)
            .expectContentType(ContentType.JSON)
            .build();

        notFoundResponse = new ResponseSpecBuilder()
            .expectStatusCode(HttpStatus.SC_NOT_FOUND)
            .build();
    }

    @AfterClass
    public void close() {
        RestAssured.reset();
        baseRequestSpecification = null;
        okResponse = null;
    }

    protected BoardEntity createBoard(String boardName) {
        return given()
            .spec(baseRequestSpecification)
            .when()
            .queryParam("name", boardName)
            .basePath(Endpoints.BOARDS)
            .post()
            .then()
            //.spec(okResponse)
            .extract().body().as(BoardEntity.class);
    }

    protected CardEntity createCardInTheList(String idList, String cardName) {
        return given()
            .spec(baseRequestSpecification)
            .when()
            .queryParam("idList", idList)
            .queryParam("name", cardName)
            .basePath(Endpoints.CARDS)
            .post()
            .then().spec(okResponse)
            .extract().body().as(CardEntity.class);
    }

    protected void deleteBoard(String boardId) {
        given()
            .spec(baseRequestSpecification)
            .when().basePath(Endpoints.BOARD_ID)
            .pathParam("id", boardId)
            .delete()
            .then()
            .log().all()
            .spec(okResponse);
    }

}