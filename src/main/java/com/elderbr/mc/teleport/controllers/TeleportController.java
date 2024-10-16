package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.TeleportConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TeleportController {

    private static TeleportController instance;
    private String nameLocal;
    private TeleportConfig localConfig = TeleportConfig.getInstance();

    private WorldsController worldsCtrl = new WorldsController();

    private TeleportController() {}

    public static TeleportController getInstance(){
        synchronized (TeleportController.class){
            if(Objects.isNull(instance)){
                instance = new TeleportController();
            }
        }
        return instance;
    }

    public boolean add(@NotNull Player player, @NotNull String worldName) {
        validation(player, worldName);
        return localConfig.save(player, worldName);
    }

    public Location findByLocal(String worldName) {
        Location location = null;
        if (worldName.isBlank() || worldName.length() < 3) {
            throw new RuntimeException(String.format("§f§lNome §e%s §fé inválido!!!", worldName));
        }
        try {
            location = localConfig.findLocal(worldName);
        } catch (Exception e) {
            String name = worldName;
            if (!worldName.contains("world_")) {
                name = "world_" + worldName;
            }
            location = worldsCtrl.findByName(name).getSpawnLocation();
        }
        return location;
    }

    public boolean update(@NotNull Player player, @NotNull String worldName) throws Exception {
        validation(player, worldName);
        return localConfig.save(player, nameLocal);
    }

    public boolean delete(Player player, String worldName) {
        validation(player, worldName);
        if (findByLocal(worldName) == null) {
            throw new RuntimeException(String.format("O local §e%s §rnão existe!!!", worldName));
        }
        return localConfig.delete(nameLocal);
    }

    public boolean deleteByNameWorld(Player player, String nameWorld){
        if(Objects.isNull(player) || !player.isOp()){
            throw new RuntimeException("§f§lOps, você não tem permissão para usar esse comando!!");
        }
        if (Objects.isNull(nameWorld) || nameWorld.isBlank()) {
            throw new RuntimeException("§f§lO nome do local é inválido, digite mais do que 3 caracteres!!!");
        }
        return localConfig.deleteByNameWorld(nameWorld);
    }

    private void validation(Player player, String worldName) {
        if (!player.isOp()) {
            throw new RuntimeException("§f§lOps, você não tem permissão para usar esse comando!!");
        }
        if (worldName.length() < 3) {
            throw new RuntimeException("§f§lDigite o nome do local!!");
        }

        nameLocal = worldName;
        if (nameLocal.isBlank() || nameLocal.length() < 3) {
            throw new RuntimeException("§f§lO nome do local é inválido, digite mais do que 3 caracteres!!!");
        }
    }
}
