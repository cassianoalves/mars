package com.cassiano.elo7.codetest.mars.business.component;

import com.cassiano.elo7.codetest.mars.business.entity.Direction;
import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand.*;
import static com.cassiano.elo7.codetest.mars.business.entity.Direction.*;

public class ProbeMovementComponentTest {



    @Test
    public void should_move_probe_by_all_plateau() {
        Plateau plateau = new Plateau("aaa", 3, 3);
        Probe probe = new Probe("bbb", 0, 0, N, plateau);
        List<ProbeCommand> commands = Arrays.asList(R, M);

    }

}