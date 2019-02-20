package com.cassiano.elo7.codetest.mars.exception;

import com.cassiano.elo7.codetest.mars.business.entity.Probe;

public class ProbeOutOfPlateauBoundsException extends Error {
    public ProbeOutOfPlateauBoundsException(Probe probe) {
        super("Movement sends probe " + probe.getId() + " out of plateau " + probe.getContainingPlateau().getId() + " bounds");
    }
}
