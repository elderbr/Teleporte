package com.elderbr.mc.teleport.comandos;

import com.elderbr.mc.teleport.controllers.WorldsController;
import com.elderbr.mc.teleport.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldCommand implements CommandExecutor {

    private String name;
    private WorldsController worldCtrl = new WorldsController();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            name = Text.toString(args);

            try {
                switch (command.getName().toLowerCase()) {
                    case "world":
                        return worldCtrl.tpa(player, args);
                    case "createworld":
                        return worldCtrl.create(player, args);
                    case "deleteworld":
                        return worldCtrl.delete(player, name);
                }
            }catch (Exception e){
                player.sendMessage("§4§l"+e.getMessage());
            }
        }
        return false;
    }
}
