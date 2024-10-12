package com.elderbr.mc.teleport.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public interface Global {
    Plugin MyPlugin = Bukkit.getServer().getPluginManager().getPlugin("Teleport");
    File diretory = MyPlugin.getDataFolder();
    String VERSION = MyPlugin.getDescription().getVersion();

    Set<String> WORLDS_LIST = new TreeSet<>();
}
