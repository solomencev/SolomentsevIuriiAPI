package com.epam.tc.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringStartsWith.startsWith;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.entities.LabelEntity;
import com.epam.tc.utils.Endpoints;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LabelTestAPI extends BaseTestAPI {

    String expectedLabelName;
    String labelColor;
    LabelEntity labelEntity;

    @BeforeClass
    protected void createBoardWithLabel() {
        expectedBoardName = RandomStringUtils.random(50, true, true);
        expectedLabelName = RandomStringUtils.random(10, true, true);
        labelColor = randomAvailableColor();
        var boardCreate = BoardEntity.builder().name(expectedBoardName).build();
        board = createBoard(expectedBoardName);
        labelEntity = createLabelOnTheBoard(board.id(), expectedLabelName, labelColor);
    }

    @AfterClass
    protected void deleteBoard() {
        deleteBoard(board.id());
    }

    @Test(priority = 1)
    protected void getInfoAboutLabel() {
        labelEntity = given()
            .spec(baseRequestSpecification)
            .when().basePath(Endpoints.LABEL_ID)
            .pathParam("id", labelEntity.id())
            .get()
            .then()
            .spec(okResponse)
            .body("name", startsWith(expectedLabelName))
            .extract().body().as(LabelEntity.class);
    }

    @Test(priority = 2)
    protected void editLabel() {
        expectedLabelName = RandomStringUtils.random(15, true, true);
        var labelNew = LabelEntity.builder().name(expectedLabelName).id(labelEntity.id()).build();
        labelEntity = given()
            .spec(baseRequestSpecification)
            .when().basePath(Endpoints.LABEL_ID)
            .pathParam("id", labelEntity.id())
            .body(labelNew)
            .put()
            .then()
            .body("name", equalTo(expectedLabelName))
            .log().all()
            .spec(okResponse)
            .extract().body().as(LabelEntity.class);
    }

    @Test(priority = 3)
    protected void deleteLabel() {
        given()
            .spec(baseRequestSpecification)
            .when().pathParam("id", labelEntity.id())
            .delete(Endpoints.LABEL_ID)
            .then()
            .spec(okResponse);
        given()
            .spec(baseRequestSpecification)
            .when().basePath(Endpoints.LABEL_ID)
            .pathParam("id", labelEntity.id())
            .get()
            .then()
            .spec(notFoundResponse);
    }

    public String randomAvailableColor() {
        String[] myString = new String[]{"yellow", "purple", "blue", "red", "green", "orange", "black", "sky", "pink", "lime"};
        int n = (int)Math.floor(Math.random() * myString.length);
        return myString[n];
    }
}
