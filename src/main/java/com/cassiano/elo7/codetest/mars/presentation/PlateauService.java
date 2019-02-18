package com.cassiano.elo7.codetest.mars.presentation;

import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/plateau")
public class PlateauService {

    @PostMapping
    public ResponseEntity<Plateau> createPlateau(@RequestBody Plateau newPlateau, HttpServletRequest request) throws URISyntaxException {
        return ResponseEntity.created(new URI("http://localhost:123/plateau/qwerqwerqwer")).build();
    }
}
