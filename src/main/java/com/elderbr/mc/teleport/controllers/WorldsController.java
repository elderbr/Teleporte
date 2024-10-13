package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.WorldConfig;
import com.elderbr.mc.teleport.enums.WorldType;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Objects;

public class WorldsController {

    private WorldConfig worldConfig = WorldConfig.getInstance();
    private TeleportController teleportCtrl = new TeleportController();

    public boolean create(Player player, String worldName){
        if(Objects.isNull(player) || !player.isOp()){
            throw new RuntimeException("Ops, você não tem permissão para usar esse comando!");
        }
        if(Objects.isNull(worldName) || worldName.isBlank()){
            throw new RuntimeException("Nome do mundo invalido!");
        }
        String name = "world_".concat(worldName);
        worldConfig.createWorld(name); // Salva novo mundo
        teleportCtrl.add(player, name);// Adiciona o local no arquivo teleport.yml
        return true;
    }

    public boolean create(Player player, String worldName, WorldType type) throws Exception {
        if(Objects.isNull(player) || !player.isOp()){
            throw new RuntimeException("Ops, você não tem permissão para usar esse comando!");
        }
        if(Objects.isNull(worldName) || worldName.isBlank()){
            throw new RuntimeException("Nome do mundo invalido!");
        }
        String name = "world_".concat(worldName);
        worldConfig.createWorld(name, type); // Salva novo mundo escolhendo o tipo
        teleportCtrl.add(player, name);// Adiciona o local no arquivo teleport.yml
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
