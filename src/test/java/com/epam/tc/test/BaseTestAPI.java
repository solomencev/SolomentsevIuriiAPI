package com.epam.tc.test;

/*import static com.epam.tc.utils.PropertiesExtractor.getApiKeyFromPropertiesExtractor;
import static com.epam.tc.utils.PropertiesExtractor.getApiTokenFromPropertiesExtractor;*/
import static io.restassured.RestAssured.given;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.entities.LabelEntity;
import com.epam.tc.entities.ListEntity;
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
            .addQueryParam("key", System.getenv("key"))
            .addQueryParam("token", System.getenv("token"))
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
            .extract().body().as(BoardEntity.class);
    }

    protected ListEntity createListOnTheBoard(String boardId, String listName) {
        return given()
            .spec(baseRequestSpecification)
            .basePath(Endpoints.LISTS)
            .queryParam("name", listName).queryParam("idBoard", boardId)
            .when()
            .post()
            .then().spec(okResponse)
            .extract().body().as(ListEntity.class);
    }

    protected LabelEntity createLabelOnTheBoard(String boardId, String labelName, String color) {
        return given()
            .spec(baseRequestSpecification)
            .basePath(Endpoints.LABELS)
            .queryParam("name", labelName)
            .queryParam("idBoard", boardId)
            .queryParam("color", color)
            .when()
            .post()
            .then().spec(okResponse)
            .extract().body().as(LabelEntity.class);
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
