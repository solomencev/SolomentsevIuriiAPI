package com.epam.tc.test;

import static org.hamcrest.core.IsEqual.equalTo;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.serviceobjects.Board;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class BoardTestAPI extends BaseTestAPI  {

    @Test(priority = 1, groups = {"HW"})
    public void createBoard() {
        boardName = RandomStringUtils.random(50, true, true);
        boardDescription = RandomStringUtils.random(50, true, true);
        boardEntity = Board.createNewBoard(boardName, boardDescription)
                           .then()
                           .spec(okResponse)
                           .body(Board.boardBodyKeyName, equalTo(boardName))
                           .body(Board.boardBodyKeyDesc, equalTo(boardDescription))
                           .extract().body().as(BoardEntity.class);
    }

    @Test(priority = 2, groups = {"HW"})
    public void getBoard() {
        Board.getBoard(boardEntity.getId())
             .then()
             .spec(okResponse)
             .body(Board.boardBodyKeyName, equalTo(boardName))
             .body(Board.boardBodyKeyDesc, equalTo(boardDescription));
    }

    @Test(priority = 3, groups = {"HW"})
    public void updateNameBoard() {
        String newBoardName = RandomStringUtils.random(100, true, true);
        Board.updateBoard(boardEntity.getId(), newBoardName)
             .then()
             .spec(okResponse)
             .body(Board.boardBodyKeyId, equalTo(boardEntity.getId()))
             .body(Board.boardBodyKeyName, equalTo(newBoardName));
    }

    @Test(priority = 4, groups = {"HW"})
    public void deleteBoard() {
        Board.deleteBoard(boardEntity.getId())
             .then()
             .spec(okResponse);
    }

    @Test(priority = 5, groups = {"HW"})
    public void makeSureBoardDeletion() {
        Board.getBoard(boardEntity.getId())
             .then()
             .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
