package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.FileConfig;
import com.elderbr.mc.teleport.config.TeleportConfig;
import com.elderbr.mc.teleport.config.WorldConfig;
import com.elderbr.mc.teleport.enums.WorldType;
import com.elderbr.mc.teleport.interfaces.Global;
import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class WorldsController implements Global {

    private FileConfig config = FileConfig.getInstance();
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

    public boolean delete(Player player, String worldName) {
        if(!player.isOp()){
            Msg.PlayerGold(player, "Ops, você não tem permissão para usar esse comando!");
            return false;
        }
        if (Objects.isNull(worldName) || worldName.isBlank()) {
            throw new RuntimeException("Nome do mundo invalido");
        }
        World world = findByName(worldName);
        boolean unloaded = Bukkit.unloadWorld(world, false);
        if (!unloaded) {
            throw new RuntimeException("Não foi possivel parar o mundo!");
        }
        File worldFolder = new File(".");
        for (File file : worldFolder.listFiles()) {
            if (!file.isDirectory()) continue;
            if (file.getName().equals(world.getName())) {
                deleteFolder(file);
            }
        }
        FileConfig.deleteWorld(world.getName());// Removendo o mundo da lista
        TeleportController.getInstance().delete(player, world.getName());// Apagando todos as home do mundo
        Msg.PlayerAll(String.format("O mundo %s foi apagado!", worldName));// Mensagem para todos os players
        return true;
    }

    public void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { // Verifica se há arquivos/pastas na pasta
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);// Deleta subpastas recursivamente
                } else {
                    file.delete();// Deleta arquivo
                }
            }
        }
        folder.delete();// Deleta a pasta em si
    }


}
