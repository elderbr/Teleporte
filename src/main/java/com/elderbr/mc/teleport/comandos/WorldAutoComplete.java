package com.elderbr.mc.teleport.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.elderbr.mc.teleport.interfaces.Global.WORLDS_LIST;
import static com.elderbr.mc.teleport.interfaces.Global.WORLDS_TYPE;

public class WorldAutoComplete implements TabCompleter {

    private Set<String> worlds = new HashSet<>();

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String worldName;
        if (sender instanceof Player player) {

            String cmd = command.getName().toLowerCase();
            switch (cmd) {
                case "world":
                    worlds.clear();
                    worldName = args[0].toLowerCase();
                    for (String name : WORLDS_LIST) {
                        if (name.toLowerCase().contains(worldName)) {
                            worlds.add(name);
                        }
                    }
                    return worlds.stream().toList();
                case "createworld":
                    if (args.length == 2) {
                        return WORLDS_TYPE;
                    }
                    return List.of();
                case "deleteworld":
                    if (args.length == 1) {
                        worlds.clear();
                        worldName = args[0].toLowerCase();
                        for (String name : WORLDS_LIST) {
                            if (name.toLowerCase().contains(worldName)) {
                                worlds.add(name);
                            }
                        }
                        return worlds.stream().toList();
                    }
                    return List.of();
            }
        }
        return List.of();
    }
}
