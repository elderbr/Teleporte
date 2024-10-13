package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.WorldConfig;

import java.util.Objects;

public class WorldsController {

    private WorldConfig worldConfig = WorldConfig.getInstance();

    public String getFindByName(String world){
        if(Objects.isNull(world) || world.isBlank()){
            throw new RuntimeException(String.format("Nome %s do mundo invalido!", world));
        }
        return worldConfig.getFindByName(world);
    }
}
