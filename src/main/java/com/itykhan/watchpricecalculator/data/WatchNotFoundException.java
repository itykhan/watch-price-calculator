package com.itykhan.watchpricecalculator.data;

public class WatchNotFoundException extends RuntimeException {

    public WatchNotFoundException(String id) {
        super(String.format("Could not find watch %s", id));
    }
}
