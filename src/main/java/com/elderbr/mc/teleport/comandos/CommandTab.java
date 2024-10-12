package com.elderbr.mc.teleport.comandos;


import com.elderbr.mc.teleport.config.FileConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTab implements TabCompleter {

    private Player player;
    private FileConfig fileConfig;

    private List<String> locationList;

    private String arg;

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (sender instanceof Player) {
            fileConfig = FileConfig.getInstance();
            locationList = new ArrayList<>();
            player = ((Player) sender).getPlayer();

            arg = "";

            for(String x : args){
                arg += x+" ";
            }

            if( args.length > 0){
                return  fileConfig.list(arg.trim());
            }

        }
        return null;
    }
}
