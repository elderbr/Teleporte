package comandos;

import config.FileConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comands implements CommandExecutor {

    private Player player;
    private FileConfig fileConfig;

    private String arg;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            fileConfig = new FileConfig();

            player = ((Player) sender).getPlayer();


            arg = "";
            if (args.length > 0) {
                for (String x : args) {
                    arg += x + " ";
                }
            }
            if (command.getName().equalsIgnoreCase("sethome") && args.length > 0) {
                if (player.isOp()) {
                    fileConfig.addLocation(player, arg.trim());
                    player.sendMessage("§a§lA home §6" + arg.trim() + "§a§l foi criada com sucesso.");
                } else {
                    player.sendMessage("§e§lSomente os Ops podem usar esse comando!!!");
                }
            }
            if (command.getName().equalsIgnoreCase("deleteHome") && args.length > 0) {
                if (player.isOp()) {
                    fileConfig.remove(arg.trim());
                    player.sendMessage("§a§lA home §6" + arg.trim() + " §a§lfoi deletada com sucesso!!!");
                } else {
                    player.sendMessage("§e§lSomente os Ops podem usar esse comando!!!");
                }
            }

            if (command.getName().equalsIgnoreCase("tpa")) {
                if (arg != null && fileConfig.getLocation(arg.trim()) != null) {
                    player.teleport(fileConfig.getLocation(arg.trim()));
                } else {
                    player.sendMessage("§e§lEsse local não existe!!!");
                }
            }
        }
        return false;
    }
}
