package com.cassiano.elo7.codetest.mars.integration;

import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PlateauRepositoryTest {
    private PlateauRepository plateauRepository;

    @Before
    public void setUp() {
        plateauRepository = new PlateauRepository();
    }

    @After
    public void tearDown() {
        plateauRepository.deleteAll();
    }

    @Test
    public void should_save_and_read_saved_plateau() {
        Plateau plateau = new Plateau("abcdabcdabcdabcdabcdabcdabcdabcd", 98, 76);
        Plateau created = plateauRepository.save(plateau);
        assertEquals(plateau, created);

        Plateau read = plateauRepository.findById("abcdabcdabcdabcdabcdabcdabcdabcd");
        assertEquals(plateau, read);
    }

    @Test
    public void should_return_null_when_not_found() {
        Plateau plateau = new Plateau("abcdabcdabcdabcdabcdabcdabcd1234", 23, 45);
        Plateau created = plateauRepository.save(plateau);
        assertEquals(plateau, created);

        Plateau read = plateauRepository.findById("1234abcdabcdabcdabcdabcdabcdabcd");
        assertNull(read);
    }

    @Test
    public void should_return_all_records() {
        Plateau plateau1 = new Plateau("abcdabcdabcdabcdabcdabcdabcd1234", 12, 34);
        Plateau plateau2 = new Plateau("abcdabcdabcdabcdabcdabcdabcd2345", 23, 45);

        plateauRepository.save(plateau1);
        plateauRepository.save(plateau2);

        assertEquals(2, plateauRepository.findAll().size());
    }

    @Test
    public void should_delete_all_records() {
        Plateau plateau1 = new Plateau("abcdabcdabcdabcdabcdabcdabcd1234", 12, 34);
        Plateau plateau2 = new Plateau("abcdabcdabcdabcdabcdabcdabcd2345", 23, 45);
        plateauRepository.save(plateau1);
        plateauRepository.save(plateau2);
        assertEquals(2, plateauRepository.findAll().size());

        plateauRepository.deleteAll();

        assertEquals(0, plateauRepository.findAll().size());
    }

}