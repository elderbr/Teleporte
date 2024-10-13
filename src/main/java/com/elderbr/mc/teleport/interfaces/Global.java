package com.elderbr.mc.teleport.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface Global {
    Plugin MyPlugin = Bukkit.getServer().getPluginManager().getPlugin("Teleport");
    File diretory = MyPlugin.getDataFolder();
    String VERSION = MyPlugin.getDescription().getVersion();

    File CONFIG_FILE = new File(diretory, "config.yml");

    // Teleport
    File TELEPORT_FILE = new File(diretory, "teleport.yml");
    List<String> LOCATION_LIST = new ArrayList<>();

    Set<String> WORLDS_LIST = new TreeSet<>();
}
