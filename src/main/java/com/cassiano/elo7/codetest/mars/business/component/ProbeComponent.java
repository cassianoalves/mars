package com.cassiano.elo7.codetest.mars.business.component;

import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand;
import com.cassiano.elo7.codetest.mars.integration.ProbeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProbeComponent {
    @Autowired
    private ProbeRepository probeRepository;
    @Autowired
    private PlateauComponent plateauComponent;

    public Probe save(String plateauId, Probe probe) {
        return null;
    }

    public Probe findById(String id, String probeId) {
        return null;
    }

    public List<Probe> findAll(String s) {
        return null;
    }

    public Probe move(String plateauId, String probeId, List<ProbeCommand> commandList) {
        return null;
    }
}
