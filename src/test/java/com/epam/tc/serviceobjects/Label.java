package com.epam.tc.serviceobjects;

import static io.restassured.RestAssured.given;

import com.epam.tc.test.BaseTestAPI;
import io.restassured.response.Response;

public class Label {

    /** path */
    private static final String LABELS_PATH = "/1/labels";
    private static final String LABEL_ID = "/1/labels/{id}";

    /** path parameters */
    private static String pathParamId = "id";

    /** query parameters */
    private static String queryParamName = "name";
    private static String queryParamIdBoard = "idBoard";
    private static String queryParamIdLabel = "idList";

    /** card body keys */
    public static String labelBodyKeyId = "id";
    public static String labelBodyKeyName = "name";
    public static String labelBodyKeyIdBoard = "idBoard";
    public static String labelBodyKeyIdList = "idList";

    public static Response createLabel(String labelName, String boardId) {
        return given()
            .spec(BaseTestAPI.baseRequestSpecification)
            .when()
            .basePath(LABELS_PATH)
            .queryParam(queryParamName, labelName)
            .queryParam(queryParamIdBoard, boardId)
            .post()
            .then()
            .extract().response();
    }
}
