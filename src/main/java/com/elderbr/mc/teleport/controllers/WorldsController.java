package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.TeleportConfig;
import com.elderbr.mc.teleport.config.WorldConfig;
import com.elderbr.mc.teleport.enums.WorldType;
import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class WorldsController {

    private WorldConfig worldConfig;
    private TeleportConfig localConfig = TeleportConfig.getInstance();

    public WorldsController() {
        worldConfig = WorldConfig.getInstance();
    }

    public boolean create(Player player, String worldName) {
        if (Objects.isNull(player) || !player.isOp()) {
            throw new RuntimeException("§l§cOps, você não tem permissão para usar esse comando!");
        }
        if (Objects.isNull(worldName) || worldName.isBlank()) {
            throw new RuntimeException("§l§cNome do mundo invalido!");
        }
        Msg.PlayerAll(String.format("§l§eCriando novo mundo %s!", worldName));
        worldConfig.createWorld("world_" + worldName); // Salva novo mundo
        Msg.PlayerAll(String.format("§l§eMundo %s criado com sucesso!", worldName));
        return true;
    }

    public boolean create(Player player, String worldName, WorldType type) {
        if (Objects.isNull(player) || !player.isOp()) {
            throw new RuntimeException("§l§cOps, você não tem permissão para usar esse comando!");
        }
        if (Objects.isNull(worldName) || worldName.isBlank()) {
            throw new RuntimeException("§l§cNome do mundo invalido!");
        }
        Msg.PlayerAll(String.format("§l§eCriando novo mundo %s!", worldName));
        worldConfig.createWorld("world_" + worldName, type); // Salva novo mundo escolhendo o tipo
        Msg.PlayerAll(String.format("§l§eMundo %s criado com sucesso!", worldName));
        return true;
    }

    public World findByName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new RuntimeException(String.format("§l§cNome %s do mundo invalido!", name));
        }
        String worldName = name;
        if (!name.contains("world_")) {
            worldName = "world_" + name;
        }
        World world = worldConfig.findByName(worldName);
        if (Objects.isNull(world)) {
            throw new RuntimeException(String.format("§l§cNome %s do mundo invalido!", name));
        }
        return world;
    }

    public boolean delete(String worldName) {
        if (Objects.isNull(worldName) || worldName.isBlank()) {
            throw new RuntimeException("Nome do mundo invalido");
        }
        World world = findByName(worldName);
        boolean unloaded = Bukkit.unloadWorld(world, false);
        if (!unloaded) {
            throw new RuntimeException("Não foi possivel parar o mundo!");
        }
        File worldFolder = new File(Bukkit.getWorldContainer().getAbsolutePath());
        for (File file : worldFolder.listFiles()) {
            if (!file.isDirectory()) continue;
            if (file.getName().equals(world.getName())) {
                file.delete();
            }
        }
        Msg.PlayerAll(String.format("O mundo %s foi apagado!", worldName));
        return true;
    }

    private boolean deleteFolder(File folder) {
        if (!folder.exists()) return true;

        // Deleta todos os arquivos e subdiretórios
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file); // Chamada recursiva para subdiretórios
                } else {
                    file.delete(); // Deleta arquivos
                }
            }
        }
        // Deleta o diretório em si
        return folder.delete();
    }
}
