package com.cassiano.elo7.codetest.mars.presentation;


import com.cassiano.elo7.codetest.mars.business.component.ProbeComponent;
import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.business.entity.Probe;
import com.cassiano.elo7.codetest.mars.business.entity.ProbeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/plateau/{plid}/probe")
public class ProbeService {
    @Autowired
    private ProbeComponent probeComponent;

    @PostMapping
    public ResponseEntity<Probe> createProbe(@RequestBody Probe probe, HttpServletRequest request) throws URISyntaxException {
        Probe createdProbe = probeComponent.save(probe);
        return ResponseEntity.created(buildLocation(createdProbe, request)).body(createdProbe);
    }

    private URI buildLocation(Probe probe, HttpServletRequest request) throws URISyntaxException {
        return new URI(request.getRequestURL() + "/" + probe.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Probe> findProbeById(@PathVariable("plid") String plateauId, @PathVariable("id") String probeId) {
        Probe probe = probeComponent.findById(plateauId, probeId);
        return probe == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(probe);
    }

    @GetMapping
    public ResponseEntity<List<Probe>> findAllProbes() {
        return ResponseEntity.ok(probeComponent.findAll());
    }

    @PostMapping("/{id}/move")
    public ResponseEntity<Probe> moveProbe(@PathVariable("plid") String plateauId, @PathVariable("plid") String probeId, @RequestBody List<ProbeCommand> commandList) {
        return ResponseEntity.ok(probeComponent.move(plateauId, probeId, commandList));
    }
}
