package com.elderbr.mc.teleport.interfaces;

import com.elderbr.mc.teleport.enums.MundoTipo;
import com.elderbr.mc.teleport.model.Mundo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;

public interface Global {
    Plugin MyPlugin = Bukkit.getServer().getPluginManager().getPlugin("Teleport");
    File diretory = MyPlugin.getDataFolder();
    String VERSION = MyPlugin.getDescription().getVersion();

    File CONFIG_FILE = new File(diretory, "config.yml");

    Set<String> ADM_LIST = new TreeSet<>();

    // Teleport
    File TELEPORT_FILE = new File(diretory, "teleport.yml");
    List<String> LOCATION_LIST = new ArrayList<>();

    // World
    String WORLD = "world";
    String NETHER = "nether";
    String THE_END = "the_end";
    Set<String> WORLDS_LIST = new HashSet<>();
    List<String> WORLDS_TYPE = Arrays.stream(MundoTipo.values()).map(v->v.name()).toList();
}
