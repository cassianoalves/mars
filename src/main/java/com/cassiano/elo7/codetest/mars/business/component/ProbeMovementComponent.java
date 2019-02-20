package com.cassiano.elo7.codetest.mars.business.component;

import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand;
import com.cassiano.elo7.codetest.mars.exception.ProbeOutOfPlateauBoundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.cassiano.elo7.codetest.mars.business.entity.Direction.*;

@Component
public class ProbeMovementComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProbeMovementComponent.class);


    public Probe move(Probe probe, List<ProbeCommand> commands) {
        LOGGER.info(". => " + probe.getPositionX() + " " + probe.getPositionY() + " " + probe.getDirection() + " (" + probe.getId() + ")");
        commands.forEach(c -> {
            executeCommand(probe, c);
            LOGGER.info(c + " => " + probe.getPositionX() + " " + probe.getPositionY() + " " + probe.getDirection() + " (" + probe.getId() + ")");
        });
        return probe;
    }

    private void executeCommand(Probe probe, ProbeCommand command) {

        switch (command) {
            case M:
                moveForward(probe);
                break;
            case L:
                turnLeft(probe);
                break;
            case R:
                turnRight(probe);
        }
    }

    private void turnRight(Probe probe) {
        switch (probe.getDirection()) {
            case N:
                probe.setDirection(E);
                break;
            case E:
                probe.setDirection(S);
                break;
            case S:
                probe.setDirection(W);
                break;
            case W:
                probe.setDirection(N);
                break;
        }
    }

    private void turnLeft(Probe probe) {
        switch (probe.getDirection()) {
            case N:
                probe.setDirection(W);
                break;
            case E:
                probe.setDirection(N);
                break;
            case S:
                probe.setDirection(E);
                break;
            case W:
                probe.setDirection(S);
                break;
        }
    }

    private void moveForward(Probe probe) {
        long x = probe.getPositionX();
        long y = probe.getPositionY();

        switch (probe.getDirection()) {
            case N:
                y++;
                break;
            case E:
                x++;
                break;
            case S:
                y--;
                break;
            case W:
                x--;
                break;
        }

        if(x < 0 || x >= probe.getContainingPlateau().getSizeX()
           || y < 0 || y >= probe.getContainingPlateau().getSizeY()) {
            throw new ProbeOutOfPlateauBoundsException(probe);
        }

        probe.setPositionX(x);
        probe.setPositionY(y);
    }

}
