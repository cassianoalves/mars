package com.cassiano.elo7.codetest.mars.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Probe {
    private String id;
    private long positionX;
    private long positionY;
    @JsonIgnore
    private Plateau containingPlateau;
}
