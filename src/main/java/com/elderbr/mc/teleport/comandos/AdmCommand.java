package com.elderbr.mc.teleport.comandos;

import com.elderbr.mc.teleport.controllers.AdmController;
import com.elderbr.mc.teleport.exceptions.AdmExcepetion;
import com.elderbr.mc.teleport.util.Msg;
import com.elderbr.mc.teleport.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdmCommand implements CommandExecutor {

    private AdmController admCtrl = AdmController.getInstance();
    private String name;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            name = Text.toString(args);
            try {
                switch (command.getName().toLowerCase()) {
                    case "addadm":
                        return admCtrl.add(player, name);
                }
            } catch (AdmExcepetion e) {
                Msg.PlayerGold(player, e.getMessage());
            }
        }
        return false;
    }
}
