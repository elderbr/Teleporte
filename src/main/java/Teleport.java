import com.elderbr.mc.teleport.comandos.*;
import com.elderbr.mc.teleport.config.TeleportConfig;
import com.elderbr.mc.teleport.config.WorldConfig;
import com.elderbr.mc.teleport.controllers.AdmController;
import com.elderbr.mc.teleport.interfaces.Global;
import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.plugin.java.JavaPlugin;

public class Teleport extends JavaPlugin {

    @Override
    public void onEnable() {

        Msg.ServidorGold(
                String.format("\n+-----------------------------------------------------------+\n" +
                        "|        Teleport                                           |\n" +
                        "|        Version %s                                        |\n" +
                        "|        Dircord: ElderBR#5398                              |\n" +
                        "+-----------------------------------------------------------+", Global.VERSION));
        saveDefaultConfig();

        AdmController.getInstance();
        WorldConfig.getInstance().findWorldAll();// Gera o valor worlds se não existir
        TeleportConfig.getInstance().findAll(); // Carrega a lista dos locais


        getCommand("sethome").setExecutor(new TeleportCommand());
        getCommand("sethome").setTabCompleter(new CommandTab());

        getCommand("tpa").setExecutor(new TeleportCommand());
        getCommand("tpa").setTabCompleter(new CommandTab());

        getCommand("addAdm").setExecutor(new AdmCommand());
        getCommand("addAdm").setTabCompleter(new AdmTab());
        getCommand("removeAdm").setExecutor(new AdmCommand());
        getCommand("removeAdm").setTabCompleter(new AdmTab());

        getCommand("deleteHome").setExecutor(new TeleportCommand());
        getCommand("deleteHome").setTabCompleter(new CommandTab());

        // Worlds
        getCommand("createworld").setExecutor(new WorldCommand());
        getCommand("createworld").setTabCompleter(new WorldTab());
        getCommand("deleteworld").setExecutor(new WorldCommand());
        getCommand("deleteworld").setTabCompleter(new WorldTab());

    }
}
