package com.epam.tc.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringStartsWith.startsWith;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.entities.CardEntity;
import com.epam.tc.entities.ListEntity;
import com.epam.tc.utils.Endpoints;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CardTestAPI extends BaseTestAPI {

    CardEntity card;
    String expectedListName;
    String expectedCardName;
    ListEntity listEntity;

    @BeforeClass
    protected void createBoardWithList() {
        expectedBoardName = RandomStringUtils.random(50, true, true);
        expectedListName = RandomStringUtils.random(10, true, true);
        var boardCreate = BoardEntity.builder().name(expectedBoardName).build();
        board = createBoard(expectedBoardName);
        listEntity = createListOnTheBoard(board.id(), expectedListName);
    }

    @AfterClass
    protected void deleteBoard() {
        deleteBoard(board.id());
    }

    @Test(priority = 1)
    protected void addNewCard() {
        expectedCardName = RandomStringUtils.random(15, true, true);
        card = given()
            .spec(baseRequestSpecification)
            .when()
            .queryParam("idList", listEntity.id())
            .queryParam("name", expectedCardName)
            .basePath(Endpoints.CARDS)
            .post()
            .then()
            .body("name", equalTo(expectedCardName))
            .spec(okResponse)
            .extract().body().as(CardEntity.class);
    }

    @BeforeMethod
    protected void createCard() {
        expectedCardName = RandomStringUtils.random(15, true, true);
        card = given()
            .spec(baseRequestSpecification)
            .when()
            .queryParam("idList", listEntity.id())
            .queryParam("name", expectedCardName)
            .basePath(Endpoints.CARDS)
            .post()
            .then()
            .body("name", equalTo(expectedCardName))
            .log().all()
            .spec(okResponse)
            .extract().body().as(CardEntity.class);
    }

    @Test(priority = 2)
    protected void editCard() {
        expectedCardName = RandomStringUtils.random(15, true, true);
        var cardNew = CardEntity.builder().name(expectedCardName).id(card.id()).build();
        card = given()
            .spec(baseRequestSpecification)
            .when().basePath(Endpoints.CARDS_ID)
            .pathParam("id", card.id())
            .body(cardNew)
            .put()
            .then()
            .body("name", equalTo(expectedCardName))
            .log().all()
            .spec(okResponse)
            .extract().body().as(CardEntity.class);
    }

    @Test(priority = 3)
    protected void getInfoAboutCard() {
        card = given()
            .spec(baseRequestSpecification)
            .when().basePath(Endpoints.CARDS_ID)
            .pathParam("id", card.id())
            .get()
            .then()
            .spec(okResponse)
            .body("name", startsWith(expectedCardName))
            .extract().body().as(CardEntity.class);
    }

    @Test(priority = 4)
    protected void deleteCard() {
        given()
            .spec(baseRequestSpecification)
            .when().pathParam("id", card.id())
            .delete(Endpoints.CARDS_ID)
            .then()
            .spec(okResponse);
        given()
            .spec(baseRequestSpecification)
            .when().basePath(Endpoints.CARDS_ID)
            .pathParam("id", card.id())
            .get()
            .then()
            .spec(notFoundResponse);
    }
}
