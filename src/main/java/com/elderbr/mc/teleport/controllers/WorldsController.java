package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.WorldConfig;
import com.elderbr.mc.teleport.enums.WorldType;
import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Objects;

public class WorldsController {

    private WorldConfig worldConfig;

    public WorldsController() {
        worldConfig = WorldConfig.getInstance();
    }

    public boolean create(Player player, String worldName){
        if(Objects.isNull(player) || !player.isOp()){
            throw new RuntimeException("§l§cOps, você não tem permissão para usar esse comando!");
        }
        if(Objects.isNull(worldName) || worldName.isBlank()){
            throw new RuntimeException("§l§cNome do mundo invalido!");
        }
        Msg.PlayerAll(String.format("§l§eCriando novo mundo %s!", worldName));
        worldConfig.createWorld("world_"+worldName); // Salva novo mundo
        Msg.PlayerAll(String.format("§l§eMundo %s criado com sucesso!", worldName));
        return true;
    }

    public boolean create(Player player, String worldName, WorldType type) {
        if(Objects.isNull(player) || !player.isOp()){
            throw new RuntimeException("§l§cOps, você não tem permissão para usar esse comando!");
        }
        if(Objects.isNull(worldName) || worldName.isBlank()){
            throw new RuntimeException("§l§cNome do mundo invalido!");
        }
        Msg.PlayerAll(String.format("§l§eCriando novo mundo %s!", worldName));
        worldConfig.createWorld("world_"+worldName, type); // Salva novo mundo escolhendo o tipo
        Msg.PlayerAll(String.format("§l§eMundo %s criado com sucesso!", worldName));
        return true;
    }

    public World findByName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new RuntimeException(String.format("§l§cNome %s do mundo invalido!", name));
        }
        World world = worldConfig.findByName(name);
        if (Objects.isNull(world)) {
            throw new RuntimeException(String.format("§l§cNome %s do mundo invalido!", name));
        }
        return world;
    }
}
