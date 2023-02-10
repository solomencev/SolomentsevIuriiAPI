package com.epam.tc.test;

import static org.hamcrest.Matchers.equalTo;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.entities.CardEntity;
import com.epam.tc.entities.ListEntity;
import com.epam.tc.serviceobjects.Board;
import com.epam.tc.serviceobjects.Card;
import com.epam.tc.serviceobjects.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.core.IsEqual;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class CardTestAPI extends BaseTestAPI  {

    @BeforeClass
    protected void createBoardWithList() {
        boardName = RandomStringUtils.random(50, true, true);
        listName = RandomStringUtils.random(10, true, true);
        boardEntity = Board.createNewBoardForListFlow(boardName).getBody().as(BoardEntity.class);;
        listEntity = List.createList(listName, boardEntity.getId()).getBody().as(ListEntity.class);
    }

    @AfterClass
    protected void deleteBoard() {
        Board.deleteBoard(boardEntity.getId())
             .then()
             .spec(okResponse);
    }

    @Test
    public void cardLifecycle() {

        /* add new card */
        cardName = RandomStringUtils.random(50, true, true);
        cardEntity = Card.createCard(listEntity.getId(), cardName)
                         .then()
                         .spec(okResponse)
                         .body(Card.cardBodyKeyIdBoard, equalTo(boardEntity.getId()))
                         .body(Card.cardBodyKeyIdList, equalTo(listEntity.getId()))
                         .body(Card.cardBodyKeyName, equalTo(cardName))
                         .extract().body().as(CardEntity.class);

        /* update name of created card */
        String newCardName = RandomStringUtils.random(100, true, true);
        Card.updateCard(cardEntity.getId(), newCardName)
             .then()
             .spec(okResponse)
             .body(Card.cardBodyKeyId, IsEqual.equalTo(cardEntity.getId()))
             .body(Card.cardBodyKeyName, IsEqual.equalTo(newCardName));

        /* get card */
        Card.getCard(cardEntity.getId())
             .then()
             .spec(okResponse)
             .body(Card.cardBodyKeyName, IsEqual.equalTo(newCardName));

        /* delete card */
        Card.deleteCard(cardEntity.getId())
             .then()
             .spec(okResponse);

        /* make sure card is removed */
        Card.getCard(cardEntity.getId())
             .then()
             .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
