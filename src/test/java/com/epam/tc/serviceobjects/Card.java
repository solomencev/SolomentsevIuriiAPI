package com.epam.tc.serviceobjects;

import static io.restassured.RestAssured.given;

import com.epam.tc.spec.Specification;
import io.restassured.response.Response;

public class Card {

    /** Path. */
    private static final String CARDS_PATH = "/1/cards";
    private static final String CARD_ID = "/1/cards/{id}";

    /** Path parameters. */
    private static String pathParamId = "id";

    /** Query parameters. */
    private static String queryParamName = "name";
    private static String queryParamIdList = "idList";

    /** Card body keys. */
    public static String cardBodyKeyId = "id";
    public static String cardBodyKeyName = "name";
    public static String cardBodyKeyIdBoard = "idBoard";
    public static String cardBodyKeyIdList = "idList";

    public static Response createCard(String listId, String cardName) {
        return given()
            .spec(Specification.baseRequestSpecification)
            .when()
            .basePath(CARDS_PATH)
            .queryParam(queryParamIdList, listId)
            .queryParam(queryParamName, cardName)
            .post()
            .then()
            .extract().response();
    }

    public static Response updateCard(String cardId, String newCardName) {
        return given()
            .spec(Specification.baseRequestSpecification)
            .when()
            .basePath(CARD_ID)
            .pathParam(pathParamId, cardId)
            .queryParam(queryParamName, newCardName)
            .put()
            .then()
            .extract().response();
    }

    public static Response getCard(String cardId) {
        return given()
            .spec(Specification.baseRequestSpecification)
            .when()
            .basePath(CARD_ID)
            .pathParam(pathParamId, cardId)
            .get()
            .then()
            .extract().response();
    }

    public static Response deleteCard(String cardId) {
        return given()
            .spec(Specification.baseRequestSpecification)
            .when()
            .basePath(CARD_ID)
            .pathParam(pathParamId, cardId)
            .delete()
            .then()
            .extract().response();
    }
}
