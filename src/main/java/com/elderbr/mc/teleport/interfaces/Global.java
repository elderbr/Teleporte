package com.elderbr.mc.teleport.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface Global {
    Plugin MyPlugin = Bukkit.getServer().getPluginManager().getPlugin("Teleport");
    File diretory = MyPlugin.getDataFolder();
    String version = MyPlugin.getDescription().getVersion();
}
