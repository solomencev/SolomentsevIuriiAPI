package com.epam.tc.test;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.entities.LabelEntity;
import com.epam.tc.serviceobjects.Board;
import com.epam.tc.serviceobjects.Label;
import com.epam.tc.utils.Color;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.core.IsEqual;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LabelTestAPI  extends BaseTestAPI {

    @BeforeClass
    protected void createBoardWithLabel() {
        boardName = RandomStringUtils.random(50, true, true);
        labelName = RandomStringUtils.random(10, true, true);
        labelColor = Color.randomAvailableColor();
        boardEntity = Board.createNewBoard(boardName, boardDescription).getBody().as(BoardEntity.class);
        labelEntity = Label.createLabel(labelName, boardEntity.getId(), labelColor).getBody().as(LabelEntity.class);

    }

    @AfterClass
    protected void deleteBoard() {
        Board.deleteBoard(boardEntity.getId())
             .then()
             .spec(okResponse);
    }

    @Test
    public void labelLifecycle() {

        /* get label */
        Label.getLabel(labelEntity.getId())
            .then()
            .spec(okResponse)
            .body(Label.labelBodyKeyName, IsEqual.equalTo(labelName));

        /* update name of created label */
        String newLabelName = RandomStringUtils.random(100, true, true);
        Label.updateLabel(labelEntity.getId(), newLabelName)
            .then()
            .spec(okResponse)
            .body(Label.labelBodyKeyId, IsEqual.equalTo(labelEntity.getId()))
            .body(Label.labelBodyKeyName, IsEqual.equalTo(newLabelName));

        /* remove created label */
        Label.deleteLabel(labelEntity.getId())
            .then()
            .spec(okResponse);

        /* make sure label is removed */
        Label.getLabel(labelEntity.getId())
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
