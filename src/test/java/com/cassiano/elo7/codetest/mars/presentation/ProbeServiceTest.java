package com.cassiano.elo7.codetest.mars.presentation;

import com.cassiano.elo7.codetest.mars.business.component.ProbeComponent;
import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.cassiano.elo7.codetest.mars.business.entity.Direction.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProbeServiceTest {
    @Mock
    private ProbeComponent probeComponent;

    @InjectMocks
    private ProbeService probeService;

    private HttpServletRequest postHttpServletRequest;

    @Before
    public void setUp() {
        postHttpServletRequest = mock(HttpServletRequest.class);
        when(postHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://marsexplorer.com/plateau/aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/probe"));
    }

    @Test
    public void should_create_a_new_probe_and_return_created_resource_at_Location_header() throws URISyntaxException {
        Probe createdProbe = new Probe("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", 12, 30, N, new Plateau());
        when(probeComponent.save(anyString(), any(Probe.class))).thenReturn(createdProbe);

        Probe newProbe = new Probe(null, 12, 30, N, null);
        ResponseEntity<Probe> result = probeService.createProbe("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", newProbe, postHttpServletRequest);

        verify(probeComponent).save("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", newProbe);
        assertEquals(createdProbe, result.getBody());
        assertEquals("http://marsexplorer.com/plateau/aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/probe/bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
                Objects.requireNonNull(result.getHeaders().get("Location")).get(0));
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void should_find_probe_by_id() {
        Probe probe = new Probe();
        when(probeComponent.findById(anyString(), anyString())).thenReturn(probe);

        ResponseEntity<Probe> result = probeService.findProbeById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        assertSame(probe, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void should_return_httpstatus_not_found_when_component_returns_null() {
        when(probeComponent.findById(anyString(), anyString())).thenReturn(null);

        ResponseEntity<Probe> result = probeService.findProbeById("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        assertFalse(result.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    @Test
    public void should_find_all_probes_in_a_plateau() {
        List<Probe> probes = Arrays.asList(new Probe(), new Probe());
        when(probeComponent.findAll(anyString())).thenReturn(probes);

        ResponseEntity<List<Probe>> result = probeService.findAllProbes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        assertSame(probes, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void should_move_probe_and_return_current_position() {
        Probe currentProbe = new Probe();
        when(probeComponent.move(anyString(), anyString(), anyList())).thenReturn(currentProbe);

        List<ProbeCommand> commandList = new ArrayList<>();
        ResponseEntity<Probe> result = probeService.moveProbe("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", commandList);

        assertSame(currentProbe, result.getBody());
    }

}