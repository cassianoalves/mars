package com.cassiano.elo7.codetest.mars.business.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plateau {
    private String id;
    private int sizeX;
    private int sizeY;
}
