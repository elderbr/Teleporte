package com.elderbr.mc.teleport.config;

import com.elderbr.mc.teleport.interfaces.Global;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WorldConfig implements Global {

    private static WorldConfig instance;
    private YamlConfiguration config;
    private static final String WORLDS = FileConfig.WORLDS;

    private WorldConfig() {
        config = FileConfig.getInstance().getConfig();
    }

    public static WorldConfig getInstance(){
        if(Objects.isNull(instance)){
            instance = new WorldConfig();
        }
        return instance;
    }

    public void createWorld(String world) {
        WORLDS_LIST.add(world);
        save(); // Salva a lista dos nomes dos mundos
        if (Bukkit.getWorld(world) == null) {
            WorldCreator create = new WorldCreator(world);
            create.createWorld();
        }
    }

    public World findByName(String world){
        if(!config.getList(WORLDS).contains(world)){
            throw new RuntimeException(String.format("O mundo %s não existe", world));
        }
        return Bukkit.getWorld(world);
    }

    public List<String> findWorldAll() {
        config = FileConfig.getInstance().getConfig();
        if (config.get(WORLDS) == null || config.getList(WORLDS).isEmpty()) {// Verificar se existir worlds no config
            save();// Salva a com os nomes dos mundos
        }
        if (!config.getList(WORLDS).isEmpty()) {
            for (Object obj : config.getList(WORLDS)) {
                String name = obj.toString().replaceAll("\\s", "_").toLowerCase();
                WORLDS_LIST.add(name);// Adiciona novo mundo na lista de mundos
                createWorld(name);// Cria novo mundo se não existir
            }
        }
        return new ArrayList<>(WORLDS_LIST);
    }

    public boolean deleteWorlds(String world) {
        if (WORLDS_LIST.contains(world)) {
            WORLDS_LIST.remove(world);// Remove o mundo da lista global
            save();// Salva a com os nomes dos mundos
        }
        return false;
    }

    public boolean save() {
        try {
            config.set(WORLDS, new ArrayList<>(WORLDS_LIST));// Cria o valor worlds no config
            config.setComments(WORLDS, Arrays.asList("Mundos criados"));
            config.save(CONFIG_FILE);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
