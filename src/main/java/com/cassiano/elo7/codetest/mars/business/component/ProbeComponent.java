package com.cassiano.elo7.codetest.mars.business.component;

import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand;
import com.cassiano.elo7.codetest.mars.exception.PlateauNotFoundException;
import com.cassiano.elo7.codetest.mars.exception.ProbeNotFoundException;
import com.cassiano.elo7.codetest.mars.integration.ProbeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProbeComponent {
    @Autowired
    private ProbeRepository probeRepository;
    @Autowired
    private PlateauComponent plateauComponent;
    @Autowired
    private ProbeMovementComponent probeMovementComponent;

    public Probe save(String plateauId, Probe probe) {
        Plateau plateau = plateauComponent.findById(plateauId);
        if(plateau == null) {
            throw new PlateauNotFoundException(plateauId);
        }
        probe.setId(IdGenerator.createId());
        probe.setContainingPlateau(plateau);
        return probeRepository.save(probe);
    }

    public Probe findById(String plateauId, String probeId) {
        Plateau plateau = plateauComponent.findById(plateauId);
        if(plateau == null) return null;

        Probe probe = probeRepository.findById(probeId);
        if(probe == null) return null;
        if(!probe.getContainingPlateau().getId().equals(plateauId)) return null;

        return probe;
    }

    public List<Probe> findAll(String plateauId) {
        return probeRepository.findAllByPlateau(plateauId);
    }

    public Probe move(String plateauId, String probeId, List<ProbeCommand> commandList) {
        Probe probe = findById(plateauId, probeId);
        if(probe == null) throw new ProbeNotFoundException(probeId);
        Probe finalProbe = probeMovementComponent.move(probe, commandList);
        return probeRepository.save(finalProbe);
    }
}
