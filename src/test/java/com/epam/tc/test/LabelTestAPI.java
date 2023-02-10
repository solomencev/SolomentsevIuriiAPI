package com.epam.tc.test;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.entities.LabelEntity;
import com.epam.tc.serviceobjects.Board;
import com.epam.tc.serviceobjects.Label;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LabelTestAPI  extends BaseTestAPI {

    @BeforeClass
    protected void createBoardWithLabel() {
        boardName = RandomStringUtils.random(50, true, true);
        labelName = RandomStringUtils.random(10, true, true);
        boardEntity = Board.createNewBoard(boardName, boardDescription).getBody().as(BoardEntity.class);
        labelEntity = Label.createLabel(labelName, boardEntity.getId()).getBody().as(LabelEntity.class);

    }

    /*@AfterClass
    protected void deleteBoard() {
        Board.deleteBoard(boardEntity.getId())
             .then()
             .spec(okResponse);
    }*/

    @Test
    public void Test() {

    }



}
