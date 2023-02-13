package com.epam.tc.serviceobjects;

import static io.restassured.RestAssured.given;

import com.epam.tc.spec.Specification;
import io.restassured.response.Response;

public class List {
    /** Path. */
    private static final String LISTS_PATH = "/1/lists";

    /** Path parameters. */
    private static String pathParamId = "id";

    /** Query parameters. */
    private static String queryParamName = "name";
    private static String queryParamIdBoard = "idBoard";

    public static Response createList(String listName, String boardId) {
        return given()
            .spec(Specification.baseRequestSpecification)
            .when()
            .basePath(LISTS_PATH)
            .queryParam(queryParamName, listName)
            .queryParam(queryParamIdBoard, boardId)
            .post()
            .then()
            .extract().response();
    }
}
