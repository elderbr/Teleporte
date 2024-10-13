package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.WorldConfig;
import org.bukkit.World;

import java.util.Objects;

public class WorldsController {

    private WorldConfig worldConfig = WorldConfig.getInstance();

    public World findByName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new RuntimeException(String.format("Nome %s do mundo invalido!", name));
        }
        World world = worldConfig.findByName(name);
        if (Objects.isNull(world)) {
            throw new RuntimeException(String.format("Nome %s do mundo invalido!", name));
        }
        return world;
    }
}
