package com.elderbr.mc.teleport.comandos;

import com.elderbr.mc.teleport.controllers.WorldsController;
import com.elderbr.mc.teleport.enums.WorldType;
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

            switch (command.getName().toLowerCase()) {
                case "createworld":
                    if (args.length > 1) {
                        return worldCreateType(player, args);
                    }
                    return worldCreate(player);
                case "deleteworld":
                    return worldCtrl.delete(name);
            }
        }
        return false;
    }

    private boolean worldCreate(Player player) {
        return worldCtrl.create(player, name);
    }

    private boolean worldCreateType(Player player, String[] args) {
        name = args[0];
        String type = "normal";
        WorldType worldType = WorldType.NORMAL;
        if (args.length > 1) {
            type = args[1];
            if (type.equals("nether")) {
                worldType = WorldType.NETHER;
            } else if (type.equals("the_end")) {
                worldType = WorldType.THE_END;
            }
        }
        return worldCtrl.create(player, name, worldType);
    }
}
