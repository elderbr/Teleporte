package com.elderbr.mc.teleport.dao;

import com.elderbr.mc.teleport.interfaces.Global;
import com.elderbr.mc.teleport.model.Local;
import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class LocalDAO implements Global {
    private final static File file = new File(diretory, "config.yml");
    private YamlConfiguration config;

    public LocalDAO() {
        if(!file.exists()){
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
                config.set("author", "ElderBR");
                config.set("discord", "ElderBR#5398");
                config.set("version", Global.version);
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public boolean save(Local local) throws Exception {
        try {
            config = YamlConfiguration.loadConfiguration(file);
            String name = local.getName();
            config.set(name.concat(".world"), local.getWorld().getName());
            config.set(name.concat(".location"), local.getX() + " " + local.getY() + " " + local.getZ());
            config.save(file);
            return true;
        }catch (IOException e){
            throw new Exception("Erro ao salvar o novo local!!!");
        }
    }

    public ConfigurationSection findByName(String name){
        config = YamlConfiguration.loadConfiguration(file);
        return config.getConfigurationSection(name);
    }

    public boolean update(Local local) throws Exception {
        try {
            config = YamlConfiguration.loadConfiguration(file);
            String name = local.getName();
            config.set(name.concat(".location"), local.getX() + " " + local.getY() + " " + local.getZ());
            config.save(file);
            return true;
        }catch (IOException e){
            throw new Exception("Erro ao atualizar o local!!!");
        }
    }

    public boolean delete(Local local) throws Exception {
        try {
            config = YamlConfiguration.loadConfiguration(file);
            config.set(local.getName(), null);
            config.save(file);
            return true;
        }catch (IOException e){
            throw new Exception("Erro ao deletar o local!!!");
        }
    }
}
