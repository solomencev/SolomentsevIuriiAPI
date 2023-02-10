package com.epam.tc.serviceobjects;

import static io.restassured.RestAssured.given;

import com.epam.tc.test.BaseTestAPI;
import io.restassured.response.Response;

public class List {

    private static final String LISTS_PATH = "/1/lists";

    //path parameters
    private static String pathParamId = "id";

    //query parameters
    private static String queryParamName = "name";
    private static String queryParamIdBoard = "idBoard";

    public static Response createList(String listName, String boardId) {
        return given()
            .spec(BaseTestAPI.baseRequestSpecification)
            .when()
            .basePath(LISTS_PATH)
            .queryParam(queryParamName, listName)
            .queryParam(queryParamIdBoard, boardId)
            .post()
            .then()
            .extract().response();
    }

}
