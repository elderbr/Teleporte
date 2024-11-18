package com.elderbr.mc.teleport.config;

import com.elderbr.mc.teleport.enums.MundoTipo;
import com.elderbr.mc.teleport.interfaces.Global;
import com.elderbr.mc.teleport.model.Mundo;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WorldConfig implements Global {

    private static WorldConfig instance;
    private YamlConfiguration config;
    private static final String WORLDS = FileConfig.WORLDS;

    private Mundo mundo = new Mundo();
    private List<Mundo> WORLDS_NORMAL = new ArrayList<>();
    private List<Mundo> WORLDS_NETHER = new ArrayList<>();
    private List<Mundo> WORLDS_THE_END = new ArrayList<>();

    private WorldConfig() {
        config = FileConfig.getInstance().getConfig();
        createWorlds();// Cria a lista de mundos
        findAllNormal();
        findAllNether();
        findAllTheEnd();
    }

    public static WorldConfig getInstance() {
        if (Objects.isNull(instance)) {
            instance = new WorldConfig();
        }
        return instance;
    }

    public World createWorld(String name, MundoTipo type) {
        try {
            mundo = new Mundo(name, type);
            WorldCreator create = new WorldCreator(name);
            switch (type) {
                case NETHER:
                    create.environment(World.Environment.NETHER);
                    saveNether();
                    break;
                case THE_END:
                    create.environment(World.Environment.THE_END);
                    saveTheEnd();
                    break;
                default:
                    create.environment(World.Environment.NORMAL);
                    saveNormal();
            }
            World world = create.createWorld();// Cria o mundo
            save();// Salva no arquivo config.yml
            return world;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar novo mundo");
        }
    }

    public World findByName(String world) {
        if (!config.getList(WORLDS).contains(world)) {
            throw new RuntimeException(String.format("O mundo %s não existe", world));
        }
        return Bukkit.getWorld(world);
    }

    public World findByNameNormal(String name) {
        List<String> list = config.getStringList(WORLD);
        for (String value : list) {
            if (Objects.equals(value, name)) {
                return Bukkit.getWorld(name);
            }
        }
        return null;
    }

    public void findAllNormal() {
        List<String> list = config.getStringList(WORLD);
        if (list.isEmpty()) return;
        for (String value : list) {
            WorldCreator world = new WorldCreator(value);
            world.environment(World.Environment.NORMAL);
            world.createWorld();
            WORLDS_LIST.add(value);
        }
    }

    public void findAllNether() {
        List<String> list = config.getStringList(NETHER);
        if (list.isEmpty()) return;
        for (String value : list) {
            WorldCreator world = new WorldCreator(value);
            world.environment(World.Environment.NETHER);
            world.createWorld();
            WORLDS_LIST.add(value);
        }
    }

    public void findAllTheEnd() {
        List<String> list = config.getStringList(THE_END);
        if (list.isEmpty()) return;
        for (String value : list) {
            WorldCreator world = new WorldCreator(value);
            world.environment(World.Environment.THE_END);
            world.createWorld();
            WORLDS_LIST.add(value);
        }
    }

    public World findByNameNether(String name) {
        List<String> list = config.getStringList(NETHER);
        for (String value : list) {
            if (Objects.equals(value, name)) {
                return Bukkit.getWorld(name);
            }
        }
        return null;
    }

    public World findByNameTheEnd(String name) {
        List<String> list = config.getStringList(THE_END);
        for (String value : list) {
            if (Objects.equals(value, name)) {
                return Bukkit.getWorld(name);
            }
        }
        return null;
    }

    public boolean deleteWorlds(String world) {
        if (WORLDS_LIST.contains(world)) {
            WORLDS_LIST.remove(world);// Remove o mundo da lista global
            save();// Salva a com os nomes dos mundos
        }
        return false;
    }


    private void saveNormal() {
        WORLDS_NORMAL.add(mundo);
        WORLDS_LIST.add(mundo.getName());
        config.set(WORLD, WORLDS_NORMAL.stream()
                .map(Mundo::getName)
                .collect(Collectors.toList()));
    }

    private void saveNether() {
        WORLDS_NETHER.add(mundo);
        WORLDS_LIST.add(mundo.getName());
        config.set(WORLD, WORLDS_NETHER.stream()
                .map(Mundo::getName)
                .collect(Collectors.toList()));
    }

    private void saveTheEnd() {
        WORLDS_THE_END.add(mundo);
        WORLDS_LIST.add(mundo.getName());
        config.set(WORLD, WORLDS_THE_END.stream()
                .map(Mundo::getName)
                .collect(Collectors.toList()));
    }

    public boolean save() {
        try {
            config.save(CONFIG_FILE);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createWorlds() {
        boolean isCreate = false;
        if (Objects.isNull(config.get(WORLD))) {
            config.set(WORLD, List.of());
            config.setComments(WORLD, Arrays.asList("Mundos criados"));
            isCreate = true;
        }
        if (Objects.isNull(config.get(NETHER))) {
            config.set(NETHER, List.of());
            isCreate = true;
        }
        if (Objects.isNull(config.get(THE_END))) {
            config.set(THE_END, List.of());
            isCreate = true;
        }
        if (isCreate) {
            save();
        }
    }
}
