package com.elderbr.mc.teleport.config;

import com.elderbr.mc.teleport.interfaces.Global;
import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class FileConfig implements Global {

    private static FileConfig instance;
    private YamlConfiguration config;

    public static final String AUTHOR = "ElderBR";
    public static final String DISCORD = "ElderBR#5398";
    public static final String WORLDS = "worlds";

    private FileConfig() {
        try {
            if (!CONFIG_FILE.exists()) {
                CONFIG_FILE.createNewFile();
            }
        } catch (IOException e) {
            Msg.ServidorErro("Erro ao criar a o arquivo config.yml", "FileConfig", getClass(), e);
        }
        config = YamlConfiguration.loadConfiguration(CONFIG_FILE);
        update();
    }

    public static FileConfig getInstance() {
        synchronized (FileConfig.class) {
            if (Objects.isNull(instance)) {
                instance = new FileConfig();
            }
            return instance;
        }
    }

    public FileConfig update() {
        config.set("author", AUTHOR);
        config.setComments("author", Arrays.asList("# Desenvolvidor"));

        config.set("discord", DISCORD);
        config.setComments("discord", Arrays.asList("Entre em contato através do Discord"));

        config.set("version", VERSION);
        config.setComments("version", Arrays.asList("Versão atual do Teleport"));

        if (config.get(WORLDS) == null) {
            config.set(WORLDS, new ArrayList<>(WORLDS_LIST));
            config.setComments(WORLDS, Arrays.asList("Lista dos nomes dos mundos criados"));
        }

        save();
        return instance;
    }

    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(CONFIG_FILE);
    }

    public static boolean deleteWorld(String worldName) {
        try {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(CONFIG_FILE);
            WORLDS_LIST.remove(worldName);// Removendo o mundo da lista
            config.set(WORLDS, new ArrayList<>(WORLDS_LIST));
            config.save(CONFIG_FILE);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save() {
        try {
            config.save(CONFIG_FILE);
            return true;
        } catch (IOException e) {
            Msg.ServidorErro("Erro ao salvar o mc.config.yml", "save()", getClass(), e);
        }
        return false;
    }
}
