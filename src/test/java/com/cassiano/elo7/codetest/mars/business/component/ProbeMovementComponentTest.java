package com.cassiano.elo7.codetest.mars.business.component;

import com.cassiano.elo7.codetest.mars.business.entity.Direction;
import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand;
import com.cassiano.elo7.codetest.mars.exception.ProbeOutOfPlateauBoundsException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand.*;
import static com.cassiano.elo7.codetest.mars.business.entity.Direction.*;

public class ProbeMovementComponentTest {

    private ProbeMovementComponent probeMovementComponent;
    private Plateau plateau;
    private Probe probe;

    @Before
    public void setUp() {
        probeMovementComponent = new ProbeMovementComponent();
        plateau = new Plateau("aaa", 3, 3);
        probe = new Probe("bbb", 0, 0, N, plateau);
    }

    @Test
    public void should_move_probe_by_all_plateau() {
        List<ProbeCommand> commands = Arrays.asList(R, M, M, L, M, L, M, M, R, M, R, M, M);

        Probe result = probeMovementComponent.move(probe, commands);

        assertEquals(2, result.getPositionX());
        assertEquals(2, result.getPositionY());
        assertEquals(E, result.getDirection());
    }

    @Test(expected = ProbeOutOfPlateauBoundsException.class)
    public void should_throw_exception_when_move_probe_reach_plateau_limit() {

        List<ProbeCommand> commands = Arrays.asList(L, M, M);

        try {
            probeMovementComponent.move(probe, commands);
        } catch (ProbeOutOfPlateauBoundsException e) {
            assertEquals("Movement sends probe bbb out of plateau aaa bounds", e.getMessage());
            throw e;
        }

    }

}