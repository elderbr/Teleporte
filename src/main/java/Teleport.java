import comandos.Comands;
import comandos.CommandTab;
import org.bukkit.plugin.java.JavaPlugin;
import util.Msg;

public class Teleport extends JavaPlugin {

    @Override
    public void onEnable() {

        Msg.ServidorGold(
                "\n+-----------------------------------------------------------+\n" +
                        "|        Teleport ElderBR para vs 1.15*                     |\n" +
                        "|        Version 1.0                                        |\n" +
                        "|        Dircord: ElderBR#5398                              |\n" +
                        "+-----------------------------------------------------------+");

        saveDefaultConfig();

        getCommand("sethome").setExecutor(new Comands());
        getCommand("sethome").setTabCompleter(new CommandTab());

        getCommand("tpa").setExecutor(new Comands());
        getCommand("tpa").setTabCompleter(new CommandTab());

        getCommand("deleteHome").setExecutor(new Comands());
        getCommand("deleteHome").setTabCompleter(new CommandTab());

    }
}
