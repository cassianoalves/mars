package com.cassiano.elo7.codetest.mars.business.component;

import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand;
import com.cassiano.elo7.codetest.mars.exception.PlateauNotFoundException;
import com.cassiano.elo7.codetest.mars.exception.ProbeNotFoundException;
import com.cassiano.elo7.codetest.mars.integration.ProbeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.cassiano.elo7.codetest.mars.business.entity.Direction.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProbeComponentTest {
    @Mock
    private ProbeRepository probeRepository;
    @Mock
    private PlateauComponent plateauComponent;
    @Mock
    private ProbeMovementComponent probeMovementComponent;

    @InjectMocks
    private ProbeComponent probeComponent;


    @Test
    public void should_create_and_save_probe_with_new_UUID_and_given_plateau () {
        when(probeRepository.save(any(Probe.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Plateau plateau = new Plateau();
        when(plateauComponent.findById(anyString())).thenReturn(plateau);

        Probe newProbe = new Probe(null, 5, 3, N, null);
        Probe createdProbe = probeComponent.save("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", newProbe);

        verify(probeRepository).save(same(newProbe));
        verify(plateauComponent).findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertTrue(createdProbe.getId().matches("[a-f,0-9]{32}"));
        assertEquals(5, createdProbe.getPositionX());
        assertEquals(3, createdProbe.getPositionY());
        assertSame(plateau, createdProbe.getContainingPlateau());
    }

    @Test(expected = PlateauNotFoundException.class)
    public void should_throw_PlateauNotFoundException_when_given_plateau_not_found () {
        when(plateauComponent.findById(anyString())).thenReturn(null);

        Probe newProbe = new Probe(null, 5, 3, S, null);

        try {
            probeComponent.save("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", newProbe);
        } catch (PlateauNotFoundException e) {
            assertEquals("Plateau aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa not found", e.getMessage());
            verifyZeroInteractions(probeRepository);
            throw e;
        }
    }

    @Test
    public void should_find_a_probe_by_ids() {
        Plateau plateau = new Plateau("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 10, 10);
        when(plateauComponent.findById(anyString())).thenReturn(plateau);
        Probe probe = new Probe("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", 1, 2, E, plateau);
        when(probeRepository.findById(anyString())).thenReturn(probe);

        Probe result = probeComponent.findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        verify(plateauComponent).findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        verify(probeRepository).findById("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        assertSame(probe, result);
    }


    @Test
    public void should_not_find_probe_when_plateau_not_match() {
        Plateau plateau = new Plateau("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 10, 10);
        when(plateauComponent.findById(anyString())).thenReturn(plateau);
        Probe probe = new Probe("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", 1, 2, W, new Plateau("cccccccccccccccccccccccccccccccc", 10, 10));
        when(probeRepository.findById(anyString())).thenReturn(probe);

        Probe result = probeComponent.findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        verify(plateauComponent).findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        verify(probeRepository).findById("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        assertNull(result);
    }

    @Test
    public void should_not_find_probe_when_plateau_not_found() {
        when(plateauComponent.findById(anyString())).thenReturn(null);

        Probe result = probeComponent.findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        verify(plateauComponent).findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        verifyZeroInteractions(probeRepository);
        assertNull(result);
    }

    @Test
    public void should_not_find_probe_when_plateau_found_but_not_probe() {
        Plateau plateau = new Plateau("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 10, 10);
        when(plateauComponent.findById(anyString())).thenReturn(plateau);
        when(probeRepository.findById(anyString())).thenReturn(null);

        Probe result = probeComponent.findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        verify(plateauComponent).findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        verify(probeRepository).findById("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        assertNull(result);
    }

    @Test
    public void should_find_all_plateau_probes_with_repo() {
        List<Probe> probes = new ArrayList<>();
        when(probeRepository.findAllByPlateau(anyString())).thenReturn(probes);

        List<Probe> result = probeComponent.findAll("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        verify(probeRepository).findAllByPlateau("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertSame(probes, result);
    }

    @Test
    public void should_move_probe_and_return_last_position() {
        Probe finalPosition = new Probe();
        when(probeMovementComponent.move(any(Probe.class), anyList())).thenReturn(finalPosition);
        Plateau plateau = new Plateau("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 10, 10);
        when(plateauComponent.findById(anyString())).thenReturn(plateau);
        Probe probe = new Probe("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", 1, 2, S, plateau);
        when(probeRepository.findById(anyString())).thenReturn(probe);
        when(probeRepository.save(any(Probe.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        ArrayList<ProbeCommand> commandList = new ArrayList<>();
        Probe result = probeComponent.move("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", commandList);

        verify(probeMovementComponent).move(same(probe), same(commandList));
        verify(plateauComponent).findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        verify(probeRepository).findById("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        verify(probeRepository).save(finalPosition);
        assertSame(result, finalPosition);
    }

    @Test(expected = ProbeNotFoundException.class)
    public void should_throw_not_found_when_probe_not_found() {
        Plateau plateau = new Plateau("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 10, 10);
        when(plateauComponent.findById(anyString())).thenReturn(plateau);
        when(probeRepository.findById(anyString())).thenReturn(null);

        try {
            Probe result = probeComponent.move("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", new ArrayList<>());
        } catch (ProbeNotFoundException e) {
            verify(plateauComponent).findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            verify(probeRepository).findById("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            verifyZeroInteractions(probeMovementComponent);
            assertEquals("Probe bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb not found", e.getMessage());
            throw e;
        }
    }
}