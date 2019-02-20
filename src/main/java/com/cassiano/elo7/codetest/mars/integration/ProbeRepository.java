package com.cassiano.elo7.codetest.mars.integration;

import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ProbeRepository {
    private Map<String, Probe> table;

    public ProbeRepository() {
        table = new HashMap<>();
    }

    public void deleteAll() {
        table.clear();
    }

    public Probe save(Probe probe) {
        table.put(probe.getId(), probe);
        return probe;
    }

    public Probe findById(String probeId) {
        return table.get(probeId);
    }

    public List<Probe> findAllByPlateau(String plateauId) {
        return table.values().stream()
                .filter(p -> plateauId.equals(p.getContainingPlateau().getId()))
                .collect(Collectors.toList());
    }
}
