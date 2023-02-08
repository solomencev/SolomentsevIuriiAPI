package com.epam.tc.utils;

public class Endpoints {

    private Endpoints() {
    }

    public static final String BASE_URI = "https://api.trello.com/1";
    public static final String BOARDS = "/boards";
    public static final String BOARD_ID = "/boards/{id}";

    public static final String CARDS = "/cards";
    public static final String CARDS_ID = "/cards/{id}";

    public static final String LABELS = "/labels";
    public static final String LABEL_ID = "/labels/{id}";


    public static final String MEMBERS = "/members";
    public static final String MEMBER_ID = "/members/{id}";
}
