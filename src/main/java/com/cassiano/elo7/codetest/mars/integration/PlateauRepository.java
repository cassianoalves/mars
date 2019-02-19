package com.cassiano.elo7.codetest.mars.integration;

import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlateauRepository {
    private Map<String, Plateau> table;

    public PlateauRepository() {
        table = new HashMap<>();
    }

    public Plateau save(Plateau plateau) {
        table.put(plateau.getId(), plateau);
        return plateau;
    }

    public Plateau findById(String id) {
        return table.get(id);
    }

    public void deleteAll() {
        table.clear();
    }

    public List<Plateau> findAll() {
        return new ArrayList<>(table.values());
    }
}
