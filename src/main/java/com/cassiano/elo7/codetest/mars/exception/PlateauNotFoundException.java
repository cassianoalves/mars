package com.cassiano.elo7.codetest.mars.exception;

public class PlateauNotFoundException extends Error {
    public PlateauNotFoundException(String plateauId) {
        super("Plateau " + plateauId + " not found");
    }
}
