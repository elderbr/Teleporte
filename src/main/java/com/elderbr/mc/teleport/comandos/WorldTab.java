package com.elderbr.mc.teleport.comandos;

import com.elderbr.mc.teleport.controllers.WorldsController;
import com.elderbr.mc.teleport.util.Msg;
import com.elderbr.mc.teleport.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorldTab implements TabCompleter {

    private String name;
    private WorldsController worldCtrl = new WorldsController();
    private List<String> worldList = worldCtrl.findByAll();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            name = Text.toString(args);
            List<String> worlds = new ArrayList<>();
            switch (command.getName().toLowerCase()) {
                case "createworld":
                    if(args.length == 2){
                        worlds.add("normal");
                        worlds.add("nether");
                        worlds.add("the_end");
                        return worlds;
                    }
                    return List.of();
                case "deleteworld":
                    for (String worldName : worldList) {
                        if (worldName.contains(name)) {
                            worlds.add(worldName.replaceAll("world_",""));
                        }
                    }
                    return worlds;
            }
        }
        return List.of();
    }
}
