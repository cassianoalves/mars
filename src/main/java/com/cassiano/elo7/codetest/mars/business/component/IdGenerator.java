package com.cassiano.elo7.codetest.mars.business.component;

import java.util.UUID;

public class IdGenerator {

    public static String createId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}