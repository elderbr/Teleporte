package com.elderbr.mc.teleport.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.WorldType;
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

    Set<String> WORLDS_LIST = new TreeSet<>();
    List<String> WORLDS_TYPE = Arrays.asList("normal", "nether", "the_end");
}
