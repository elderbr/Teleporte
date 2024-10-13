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

    public boolean add(@NotNull Player player, @NotNull String[] cmd) throws Exception {
        this.cmd = cmd;
        validation(player, cmd);
        return localConfig.save(player, Text.toString(cmd));
    }

    public Location findByLocal(String[] args) {
        String name = Text.toString(args);
        Location location = null;
        if (name.isBlank() || name.length() < 3) {
            throw new RuntimeException(String.format("§f§lNome §e%s §fé inválido!!!", name));
        }
        try {
            location = localConfig.findLocal(name);
        } catch (Exception e) {
            location = worldsCtrl.findByName(name).getSpawnLocation();
        }
        return location;
    }

    public boolean update(@NotNull Player player, @NotNull String[] cmd) throws Exception {
        this.cmd = cmd;
        validation(player, cmd);
        return localConfig.save(player, nameLocal);
    }

    public boolean delete(Player player, String[] name) throws Exception {
        validation(player, name);
        if (findByLocal(name) == null) {
            throw new Exception(String.format("O local §e%s §rnão existe!!!", Text.toString(name)));
        }
        return localConfig.delete(nameLocal);
    }

    private void validation(Player player, String[] name) throws Exception {
        if (!player.isOp()) {
            throw new Exception("§f§lOps, você não tem permissão para usar esse comando!!");
        }
        if (name.length < 1) {
            throw new Exception("§f§lDigite o nome do local!!");
        }

        nameLocal = Text.toString(name);
        if (nameLocal.isBlank() || nameLocal.length() < 3) {
            throw new Exception("§f§lO nome do local é inválido, digite mais do que 3 caracteres!!!");
        }
    }
}
