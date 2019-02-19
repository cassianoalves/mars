package com.cassiano.elo7.codetest.mars.business.component;

import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import com.cassiano.elo7.codetest.mars.integration.ProbeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProbeComponentTest {
    @Mock
    private ProbeRepository probeRepository;
    @Mock
    private PlateauComponent plateauComponent;

    @InjectMocks
    private ProbeComponent probeComponent;


    @Test
    public void should_create_and_save_probe_with_new_UUID_and_given_plateau () {
        when(probeRepository.save(any(Probe.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Plateau plateau = new Plateau();
        when(plateauComponent.findById(anyString())).thenReturn(plateau);

        Probe newProbe = new Probe(null, 5, 3,null);
        Probe createdProbe = probeComponent.save("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", newProbe);

        verify(probeRepository).save(same(newProbe));
        verify(plateauComponent).findById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertTrue(createdProbe.getId().matches("[a-f,0-9]{32}"));
        assertEquals(5, createdProbe.getPositionX());
        assertEquals(3, createdProbe.getPositionY());
        assertSame(plateau, createdProbe.getContainingPlateau());
    }

}