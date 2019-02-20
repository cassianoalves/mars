package com.cassiano.elo7.codetest.mars.exception;

public class ProbeNotFoundException extends Error {
    public ProbeNotFoundException(String probeId) {
        super("Probe " + probeId + " not found");
    }
}
