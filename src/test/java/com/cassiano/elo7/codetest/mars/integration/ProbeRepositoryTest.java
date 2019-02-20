package com.cassiano.elo7.codetest.mars.integration;

import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.cassiano.elo7.codetest.mars.business.entity.Direction.*;
import static org.junit.Assert.*;

public class ProbeRepositoryTest {
    private ProbeRepository probeRepository;

    @Before
    public void setUp() {
        probeRepository = new ProbeRepository();
    }

    @After
    public void tearDown() {
        probeRepository.deleteAll();
    }

    @Test
    public void should_save_and_read_saved_probe() {
        Probe probe = new Probe("abcdabcdabcdabcdabcdabcdabcdabcd", 98, 76, N, new Plateau());
        Probe created = probeRepository.save(probe);
        assertEquals(probe, created);

        Probe read = probeRepository.findById("abcdabcdabcdabcdabcdabcdabcdabcd");
        assertEquals(probe, read);
    }

    @Test
    public void should_return_null_when_not_found() {
        Probe probe = new Probe("abcdabcdabcdabcdabcdabcdabcd1234", 23, 45, N, new Plateau());
        Probe created = probeRepository.save(probe);
        assertEquals(probe, created);

        Probe read = probeRepository.findById("1234abcdabcdabcdabcdabcdabcdabcd");
        assertNull(read);
    }

    @Test
    public void should_return_all_plateau_records() {
        Plateau plateau1 = new Plateau("abcdabcdabcdabcdabcdabcdabcd1234", 12, 34);
        Plateau plateau2 = new Plateau("abcdabcdabcdabcdabcdabcdabcd2345", 23, 45);
        Probe probe1 = new Probe("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, 0, N, plateau1);
        Probe probe2 = new Probe("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", 0, 0, S, plateau2);
        Probe probe3 = new Probe("cccccccccccccccccccccccccccccccc", 0, 0, E, plateau1);

        probeRepository.save(probe1);
        probeRepository.save(probe2);
        probeRepository.save(probe3);

        assertEquals(2, probeRepository.findAllByPlateau("abcdabcdabcdabcdabcdabcdabcd1234").size());
        assertEquals(1, probeRepository.findAllByPlateau("abcdabcdabcdabcdabcdabcdabcd2345").size());
    }

    @Test
    public void should_delete_all_records() {
        Plateau plateau1 = new Plateau("abcdabcdabcdabcdabcdabcdabcd1234", 12, 34);
        Plateau plateau2 = new Plateau("abcdabcdabcdabcdabcdabcdabcd2345", 23, 45);
        Probe probe1 = new Probe("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, 0, N, plateau1);
        Probe probe2 = new Probe("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", 0, 0, S, plateau2);
        Probe probe3 = new Probe("cccccccccccccccccccccccccccccccc", 0, 0, E, plateau1);

        probeRepository.save(probe1);
        probeRepository.save(probe2);
        probeRepository.save(probe3);

        assertEquals(2, probeRepository.findAllByPlateau("abcdabcdabcdabcdabcdabcdabcd1234").size());
        assertEquals(1, probeRepository.findAllByPlateau("abcdabcdabcdabcdabcdabcdabcd2345").size());

        probeRepository.deleteAll();

        assertEquals(0, probeRepository.findAllByPlateau("abcdabcdabcdabcdabcdabcdabcd1234").size());
        assertEquals(0, probeRepository.findAllByPlateau("abcdabcdabcdabcdabcdabcdabcd2345").size());
    }
}