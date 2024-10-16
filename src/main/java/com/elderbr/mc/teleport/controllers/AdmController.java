package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.FileConfig;
import com.elderbr.mc.teleport.exceptions.AdmExcepetion;
import com.elderbr.mc.teleport.interfaces.Global;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class AdmController implements Global {

    private static AdmController instance;
    private static YamlConfiguration config = FileConfig.getInstance().getConfig();
    private static String ADM = "adm";

    private AdmController() {
        if (config.getString("adm") == null) {
            config.set(ADM, Arrays.asList());
            config.setComments(ADM, Arrays.asList("Administradores"));
            save();
        }
    }

    public static AdmController getInstance() {
        synchronized (AdmController.class) {
            if (Objects.isNull(instance)) {
                instance = new AdmController();
            }
            return instance;
        }
    }

    public boolean add(Player player, String name) {
        if (Objects.isNull(player) || !player.isOp()) {
            throw new AdmExcepetion("Ops, você não tem permissão para usar esse comando!");
        }
        if (Objects.isNull(name) || name.length() < 4) {
            throw new AdmExcepetion("Nome do jogador invalido!");
        }
        List<String> list = config.getStringList(ADM);
        list.add(name);
        Collections.sort(list);
        config.set(ADM, list);
        return save();
    }

    public boolean save() {
        try {
            config.save(CONFIG_FILE);
            return true;
        } catch (IOException e) {
            throw new AdmExcepetion(e.getMessage());
        }
    }
}
