package com.cassiano.elo7.codetest.mars.business.component;

import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.integration.PlateauRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlateauComponentTest {
    @Mock
    private PlateauRepository plateauRepository;

    @InjectMocks
    private PlateauComponent plateauComponent;

    @Test
    public void should_create_and_save_plateau_with_new_UUID () {
        when(plateauRepository.save(any(Plateau.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        Plateau newPlateau = new Plateau(null, 12,34);
        Plateau createdPlateau = plateauComponent.save(newPlateau);

        verify(plateauRepository).save(same(newPlateau));
        assertTrue(createdPlateau.getId().matches("[a-f,0-9]{32}"));
        assertEquals(12, createdPlateau.getXSize());
        assertEquals(34, createdPlateau.getYSize());
    }

}