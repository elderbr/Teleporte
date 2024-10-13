package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.LocalConfig;
import com.elderbr.mc.teleport.dao.LocalDAO;
import com.elderbr.mc.teleport.model.Local;
import com.elderbr.mc.teleport.util.Msg;
import com.elderbr.mc.teleport.util.Text;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LocalController {

    private Local local;
    private String nameLocal;
    private String[] cmd;
    private LocalDAO localDAO = new LocalDAO();
    private LocalConfig localConfig = LocalConfig.getInstance();
    private String command;

    public LocalController() {
    }

    public boolean add(@NotNull Player player, @NotNull String[] cmd) throws Exception {
        this.cmd = cmd;
        validation(player, cmd);
        return localConfig.save(player, Text.toString(cmd));
    }

    public Location findByLocal(String[] args) throws Exception {
        String name = Text.toString(args);
        if (name.isBlank() || name.length() < 3) {
            throw new Exception(String.format("§f§lNome §e%s §fé inválido!!!", name));
        }
        return localConfig.findLocal(name);
    }

    public boolean update(@NotNull Player player, @NotNull String[] cmd) throws Exception {
        this.cmd = cmd;
        validation(player, cmd);
        return localDAO.update(new Local(player, nameLocal));
    }

    public boolean delete(Player player, String[] name) throws Exception {
        validation(player, name);
        if(findByLocal(name)==null){
            throw new Exception(String.format("O local §e%s §rnão existe!!!", local.getName()));
        }
        local = new Local();
        local.setName(nameLocal);
        return localDAO.delete(local);
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
