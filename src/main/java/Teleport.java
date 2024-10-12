import com.elderbr.mc.teleport.comandos.Comands;
import com.elderbr.mc.teleport.comandos.CommandTab;
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
                        "+-----------------------------------------------------------+", Global.version));

        saveDefaultConfig();

        getCommand("sethome").setExecutor(new Comands());
        getCommand("sethome").setTabCompleter(new CommandTab());

        getCommand("tpa").setExecutor(new Comands());
        getCommand("tpa").setTabCompleter(new CommandTab());

        getCommand("deleteHome").setExecutor(new Comands());
        getCommand("deleteHome").setTabCompleter(new CommandTab());

    }
}
