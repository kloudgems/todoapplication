package com.application.todoapplication.exceptions;

import lombok.Getter;

@Getter
public class MessageKey {
    private final String name;
    private final int numTokens;

    private MessageKey(String key, int numTokens){
        this.name = key;
        this.numTokens = numTokens;
    }
    public static final MessageKey bad_credentials = new MessageKey("bad_credentials",0);
    public static final MessageKey bad_request = new MessageKey("bad_request",1);
}
