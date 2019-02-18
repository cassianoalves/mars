package com.cassiano.elo7.codetest.mars.business.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plateau {
    private String id;
    private int xSize;
    private int ySize;
}
