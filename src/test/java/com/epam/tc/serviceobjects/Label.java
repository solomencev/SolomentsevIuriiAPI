package com.epam.tc.serviceobjects;

import static io.restassured.RestAssured.given;

import com.epam.tc.spec.Specification;
import io.restassured.response.Response;

public class Label {

    /** Path. */
    private static final String LABELS_PATH = "/1/labels";
    private static final String LABEL_ID = "/1/labels/{id}";

    /** Path parameters. */
    private static String pathParamId = "id";

    /** Query parameters. */
    private static String queryParamName = "name";
    private static String queryParamIdBoard = "idBoard";
    private static String queryParamColor = "color";

    /** Card body keys. */
    public static String labelBodyKeyId = "id";
    public static String labelBodyKeyName = "name";

    public static Response createLabel(String labelName, String boardId, String color) {
        return given()
            .spec(Specification.baseRequestSpecification)
            .when()
            .basePath(LABELS_PATH)
            .queryParam(queryParamName, labelName)
            .queryParam(queryParamIdBoard, boardId)
            .queryParam(queryParamColor, color)
            .post()
            .then()
            .extract().response();
    }

    public static Response getLabel(String labelId) {
        return given()
            .spec(Specification.baseRequestSpecification)
            .when()
            .basePath(LABEL_ID)
            .pathParam(pathParamId, labelId)
            .get()
            .then()
            .extract().response();
    }

    public static Response updateLabel(String labelId, String newLabelName) {
        return given()
            .spec(Specification.baseRequestSpecification)
            .when()
            .basePath(LABEL_ID)
            .pathParam(pathParamId, labelId)
            .queryParam(queryParamName, newLabelName)
            .put()
            .then()
            .extract().response();
    }

    public static Response deleteLabel(String labelId) {
        return given()
            .spec(Specification.baseRequestSpecification)
            .when()
            .basePath(LABEL_ID)
            .pathParam(pathParamId, labelId)
            .delete()
            .then()
            .extract().response();
    }
}
