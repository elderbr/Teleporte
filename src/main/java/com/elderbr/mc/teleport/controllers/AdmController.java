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
import java.util.stream.Collectors;


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
        findAll();
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
        validation(player, name);// Verifica os dados do player e o nome do adm
        List<String> list = config.getStringList(ADM);
        if(list.contains(name)){// Verificar se o jogador já é adm
            throw new AdmExcepetion(String.format("Jogador %s já está na lista dos administração!", name));
        }
        list.add(name);
        ADM_LIST.add(name);// Adiciona o nome do novo administrador na variável global
        Collections.sort(list);
        config.set(ADM, list);
        return save();
    }

    public List<String> findAll(){
        if (config.getString("adm") == null) {
            config.set(ADM, Arrays.asList());
            config.setComments(ADM, Arrays.asList("Administradores"));
            save();
        }
        ADM_LIST.clear();
        ADM_LIST.addAll(config.getStringList(ADM));
        return ADM_LIST.stream().toList();
    }

    public boolean remove(Player player, String name){
        validation(player, name);// Verifica os dados do player e o nome do adm
        List<String> list = config.getStringList(ADM);
        if(!list.contains(name)){
            throw new AdmExcepetion(String.format("Jogador %s não está na lista dos administração!", name));
        }
        list.remove(name);
        ADM_LIST.clear();
        ADM_LIST.addAll(list);

        Collections.sort(list);
        config.set(ADM, list);
        return save();
    }

    public void validation(Player player, String name){
        if (Objects.isNull(player) || !player.isOp()) {
            throw new AdmExcepetion("Ops, você não tem permissão para usar esse comando!");
        }
        if (Objects.isNull(name) || name.length() < 4 || name.contains(" ")) {
            throw new AdmExcepetion("Nome do jogador invalido!");
        }
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
