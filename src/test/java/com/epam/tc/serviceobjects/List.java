package com.epam.tc.serviceobjects;

import static io.restassured.RestAssured.given;

import com.epam.tc.test.BaseTestAPI;
import io.restassured.response.Response;

public class List {

    private static final String LISTS_PATH = "/1/lists";
    private static final String ARCHIVE_LIST_PATH = "/1/lists/{id}/closed";

    //path parameters
    private static String pathParamId = "id";

    //query parameters
    private static String queryParamName = "name";
    private static String queryParamIdBoard = "idBoard";
    private static String queryParamValue = "value";

    //list body keys
    public static String listBodyKeyName = "name";
    public static String listBodyKeyIdBoard = "idBoard";
    public static String listBodyKeyClosed = "closed";

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

    public static Response archiveList(String listId, String isListClosed) {
        return given()
            .spec(BaseTestAPI.baseRequestSpecification)
            .when()
            .basePath(ARCHIVE_LIST_PATH)
            .pathParam(pathParamId, listId)
            .queryParam(queryParamValue, isListClosed)
            .put()
            .then()
            .extract().response();
    }

    public static Response createListOnTheBoard(String boardId, String listName) {
        return given()
            .spec(BaseTestAPI.baseRequestSpecification)
            .when()
            .basePath(LISTS_PATH)
            .queryParam(boardId)
            .queryParam(listName)
            .put()
            .then()
            .extract().response();
    }

}
