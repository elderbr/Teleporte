package com.elderbr.mc.teleport.comandos;


import com.elderbr.mc.teleport.interfaces.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandTab implements TabCompleter, Global {

    private Player player;

    private List<String> names;

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        names = new ArrayList<>();

        if (sender instanceof Player) {
            player = ((Player) sender).getPlayer();
            if (command.getName().equalsIgnoreCase("tpa")) {
                // Lista todos os locais criados pelo plugin
                for (String value : LOCATION_LIST) {
                    if (value.contains(args[0])) {
                        names.add(value);
                    }
                }
                // Lista todos os mundos criados pelo plugin
                for (String world : WORLDS_LIST) {
                    if (world.contains(args[0])) {
                        names.add(world);
                    }
                }
                return names;
            }
        }
        return names;
    }
}
