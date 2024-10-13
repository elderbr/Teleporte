package com.elderbr.mc.teleport.comandos;

import com.elderbr.mc.teleport.controllers.TeleportController;
import com.elderbr.mc.teleport.util.Msg;
import com.elderbr.mc.teleport.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    TeleportController teleportCtrl = new TeleportController();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            switch (command.getName().toLowerCase()) {
                case "tpa":
                    try {
                        player.teleport(teleportCtrl.findByLocal(args));
                    } catch (Exception e) {
                        Msg.PlayerGold(player, e.getMessage());
                    }
                    return true;
                case "sethome":
                    try {
                        teleportCtrl.add(player, args);
                        Msg.PlayerAll(String.format("§f§lNovo local criado %s pelo o jogador %s!!!", Text.toString(args), player.getName()));
                    } catch (Exception e) {
                        Msg.PlayerGold(player, e.getMessage());
                    }
                    return true;
                case "deletehome":
                    try {
                        teleportCtrl.delete(player, args);
                        Msg.PlayerAll(String.format("§f§lO local %s foi apagado pelo o jogador %s!!!", Text.toString(args), player.getName()));
                    } catch (Exception e) {
                        Msg.PlayerGold(player, e.getMessage());
                    }
                    return true;
            }
        }
        return false;
    }
}
