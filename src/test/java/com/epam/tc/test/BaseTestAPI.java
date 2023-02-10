package com.epam.tc.test;

import com.epam.tc.entities.BoardEntity;
import com.epam.tc.entities.CardEntity;
import com.epam.tc.entities.LabelEntity;
import com.epam.tc.entities.ListEntity;
import com.epam.tc.test.spec.Specification;
import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTestAPI extends Specification  {

    protected String boardName;
    protected String listName;
    protected String boardDescription;
    protected String cardName;
    protected String labelName;
    protected String labelColor;

    public static final String BASE_ENDPOINT = "https://api.trello.com";

    BoardEntity boardEntity = new BoardEntity();
    ListEntity listEntity = new ListEntity();
    CardEntity cardEntity = new CardEntity();
    LabelEntity labelEntity = new LabelEntity();

    @BeforeClass
    public void baseSetup() {
        RestAssured.baseURI = BASE_ENDPOINT;
        Specification.setUpRequest();
        Specification.setUpResponse();
    }

    @AfterClass
    public void teardown() {
        RestAssured.reset();
        Specification.teardownSpec();
    }

}
