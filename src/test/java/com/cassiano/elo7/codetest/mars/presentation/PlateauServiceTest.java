package com.cassiano.elo7.codetest.mars.presentation;

import com.cassiano.elo7.codetest.mars.business.component.PlateauComponent;
import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlateauServiceTest {
    @Mock
    private PlateauComponent plateauComponent;

    @InjectMocks
    private PlateauService plateauService;

    private HttpServletRequest postHttpServletRequest;

    @Before
    public void setUp() {
        postHttpServletRequest = mock(HttpServletRequest.class);
        when(postHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://marsexplorer.com/plateau"));
    }

    @Test
    public void should_create_a_new_plateau_and_return_created_resource_at_Location_header() throws URISyntaxException {
        Plateau createdPlateau = new Plateau("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 12, 30);
        when(plateauComponent.save(any(Plateau.class))).thenReturn(createdPlateau);

        Plateau newPlateau = new Plateau(null, 12, 30);
        ResponseEntity<Plateau> result = plateauService.createPlateau(newPlateau, postHttpServletRequest);

        verify(plateauComponent).save(newPlateau);
        assertEquals(createdPlateau, result.getBody());
        assertEquals("http://marsexplorer.com/plateau/aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Objects.requireNonNull(result.getHeaders().get("Location")).get(0));
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void should_find_plateau_by_id() {
        Plateau plateau = new Plateau();
        when(plateauComponent.findById(anyString())).thenReturn(plateau);

        ResponseEntity<Plateau> result = plateauService.findPlateauById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        assertSame(plateau, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    public void should_find_all_plateau() {
        List<Plateau> plateaus = Arrays.asList(new Plateau(), new Plateau());
        when(plateauComponent.findAll()).thenReturn(plateaus);

        ResponseEntity<List<Plateau>> result = plateauService.findAllPlateus();

        assertSame(plateaus, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}