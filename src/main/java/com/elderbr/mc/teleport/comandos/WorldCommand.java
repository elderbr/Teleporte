package com.elderbr.mc.teleport.comandos;

import com.elderbr.mc.teleport.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldCommand implements CommandExecutor {

    private String name;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            name = Text.toString(args);
        }
        return false;
    }
}
