package com.cassiano.elo7.codetest.mars.business.component;

import com.cassiano.elo7.codetest.mars.business.entity.Plateau;
import com.cassiano.elo7.codetest.mars.integration.PlateauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlateauComponent {
    @Autowired
    private PlateauRepository plateauRepository;

    public Plateau save(Plateau newPlateau) {
        newPlateau.setId(IdGenerator.createId());
        return plateauRepository.save(newPlateau);
    }

    public Plateau findById(String id) {
        return plateauRepository.findById(id);
    }

    public List<Plateau> findAll() {
        return plateauRepository.findAll();
    }
}
