package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.TeleportConfig;
import com.elderbr.mc.teleport.model.Local;
import com.elderbr.mc.teleport.util.Text;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportController {

    private Local local;
    private String nameLocal;
    private String[] cmd;
    private TeleportConfig localConfig = TeleportConfig.getInstance();
    private String command;

    private WorldsController worldsCtrl = new WorldsController();

    public TeleportController() {
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
            location = worldsCtrl.findByName(worldName).getSpawnLocation();
        }
        return location;
    }

    public boolean update(@NotNull Player player, @NotNull String worldName) throws Exception {
        validation(player, worldName);
        return localConfig.save(player, nameLocal);
    }

    public boolean delete(Player player, String worldName) throws Exception {
        validation(player, worldName);
        if (findByLocal(worldName) == null) {
            throw new Exception(String.format("O local §e%s §rnão existe!!!", worldName));
        }
        return localConfig.delete(nameLocal);
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
