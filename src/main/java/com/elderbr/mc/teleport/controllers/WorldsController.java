package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.WorldConfig;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Objects;

public class WorldsController {

    private WorldConfig worldConfig = WorldConfig.getInstance();

    public boolean create(Player player, String worldName){
        if(Objects.isNull(player) || !player.isOp()){
            throw new RuntimeException("Ops, você não tem permissão para usar esse comando!");
        }
        if(Objects.isNull(worldName) || worldName.isBlank()){
            throw new RuntimeException("Nome do mundo invalido!");
        }
        worldConfig.createWorld(worldName);
        return true;
    }

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
