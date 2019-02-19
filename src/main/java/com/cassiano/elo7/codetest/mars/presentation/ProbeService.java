package com.cassiano.elo7.codetest.mars.presentation;


import com.cassiano.elo7.codetest.mars.business.component.ProbeComponent;
import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ProbeService {
    private ProbeComponent plateauComponent;

    public ResponseEntity<Probe> createProbe(Probe probe, HttpServletRequest httpServletRequest) {
        return null;
    }

    public ResponseEntity<Probe> findProbeById(String id) {
        return null;
    }

    public ResponseEntity<List<Probe>> findAllProbes() {
        return null;
    }
}
