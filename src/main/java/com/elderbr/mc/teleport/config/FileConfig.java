package com.elderbr.mc.teleport.config;

import com.elderbr.mc.teleport.interfaces.Global;
import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class FileConfig implements Global {

    private static FileConfig instance;
    private YamlConfiguration config;

    private static final String WORLDS = "worlds";
    private World world;


    private FileConfig() {
        try {
            if (!CONFIG_FILE.exists()) {
                CONFIG_FILE.createNewFile();
            }
        } catch (IOException e) {
            Msg.ServidorErro("Erro ao criar a o arquivo config.yml", "FileConfig", getClass(), e);
        }
        config = YamlConfiguration.loadConfiguration(CONFIG_FILE);
        updateVersion();
    }

    public static FileConfig getInstance() {
        synchronized (FileConfig.class) {
            if (Objects.isNull(instance)) {
                instance = new FileConfig();
            }
            return instance;
        }
    }

    public FileConfig updateVersion() {
        config.set("version", VERSION);
        config.setComments("version", Arrays.asList("Versão atual do Teleport"));
        save();
        return instance;
    }

    public void add(String ID, Object name) {
        if (config.getString(ID) == null) {
            config.addDefault(ID, name);
        } else {
            config.set(ID, name);
        }
    }


    public void addLocation(Player player, String name) {
        add(name + ".world", player.getWorld().getName());
        add(name + ".location", (int) player.getLocation().getX() + ".5 " + ((int) player.getLocation().getY() + 1) + " " + (int) player.getLocation().getZ() + ".5");
        save();
    }

    public Location getLocation(String name) {
        world = Bukkit.getWorld(config.getString(name + ".world"));
        String[] local = config.getString(name + ".location").split("\\s");
        return new Location(world, Double.parseDouble(local[0]), Double.parseDouble(local[1]), Double.parseDouble(local[2]));
    }

    public List<String> list() {
        List<String> list = new ArrayList<>();
        for (Object local : config.getKeys(false)) {
            list.add(local.toString());
        }
        return list;
    }

    public List<String> list(String name) {
        List<String> list = new ArrayList<>();
        for (String local : config.getKeys(false)) {
            if (local.contains(name)) {
                list.add(local);
            }
        }
        return list;
    }


    public void remove(String arg) {
        config.set(arg, null);
        save();
    }

    /*********************************
     *
     *  Criação e de novos mundos
     *
     *********************************/
    public void createWorld(String world) {
        WORLDS_LIST.add(world);
        saveWorlds(); // Salva a lista dos nomes dos mundos
        if (Bukkit.getWorld(world) == null) {
            WorldCreator create = new WorldCreator(world);
            create.createWorld();
        }
    }

    public boolean saveWorlds() {
        config.set(WORLDS, new ArrayList<>(WORLDS_LIST));// Cria o valor worlds no config
        config.setComments(WORLDS, Arrays.asList("Mundos criados"));
        return save();
    }

    public List<String> findWorldAll() {
        config = YamlConfiguration.loadConfiguration(CONFIG_FILE);
        if (config.get(WORLDS) == null || config.getList(WORLDS).isEmpty()) {// Verificar se existir worlds no config
            saveWorlds();// Salva a com os nomes dos mundos
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
            saveWorlds();// Salva a com os nomes dos mundos
        }
        return false;
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
