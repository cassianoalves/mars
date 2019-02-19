package com.cassiano.elo7.codetest.mars.presentation;

import com.cassiano.elo7.codetest.mars.business.component.PlateauComponent;
import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/plateau")
public class PlateauService {
    @Autowired
    private PlateauComponent plateauComponent;

    @PostMapping
    public ResponseEntity<Plateau> createPlateau(@RequestBody Plateau newPlateau, HttpServletRequest request) throws URISyntaxException {
        Plateau createdPlateau = plateauComponent.save(newPlateau);
        return ResponseEntity.created(buildLocation(createdPlateau, request)).body(createdPlateau);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plateau> findPlateauById(@PathParam("id") String id) {
        return ResponseEntity.ok(plateauComponent.findById(id));
    }

    private URI buildLocation(Plateau plateau, HttpServletRequest request) throws URISyntaxException {
        return new URI(request.getRequestURL() + "/" + plateau.getId());
    }

}
