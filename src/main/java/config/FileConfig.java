package config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import util.Msg;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileConfig {

    private final Path path = Paths.get(Bukkit.getServer().getPluginManager().getPlugin("Teleport").getDataFolder() + "/config.yml");
    private File file;
    private Charset utf = StandardCharsets.UTF_8;

    private FileConfiguration yml;

    private World world;
    private double x, z, y;


    public FileConfig() {

        file = new File(path.toString());

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                Msg.ServidorErro("Erro ao criar a o arquivo config.yml", "FileConfig", getClass(), e);
                e.printStackTrace();
            }
        }
        yml = YamlConfiguration.loadConfiguration(file);
    }

    public void add(String ID, Object name) {
        if( yml.getString(ID) == null) {
            yml.addDefault(ID, name);
        }else{
            yml.set(ID, name);
        }
    }

    public void save() {
        try {
            yml.options().copyDefaults(true);
            yml.save(file);
        } catch (IOException e) {
            Msg.ServidorErro("Erro ao salvar o mc.config.yml", "save()", getClass(), e);
        }
    }

    public void addLocation(Player player, String name){
        add(name+".world", player.getWorld().getName() );
        add(name+".location", (int) player.getLocation().getX()+".5 "+ ((int) player.getLocation().getY()+1)+" "+ (int) player.getLocation().getZ()+".5");
        save();
    }

    public Location getLocation(String name){
        world = Bukkit.getWorld(yml.getString(name+".world"));
        String[] local = yml.getString(name + ".location").split("\\s");
        return new Location(world, Double.parseDouble(local[0]), Double.parseDouble(local[1]), Double.parseDouble(local[2]));
    }

    public List<String> list(){
        List<String> list = new ArrayList<>();
        for(Object local : yml.getKeys(false)){
            list.add(local.toString());
        }
        return list;
    }
    public List<String> list(String name){
        List<String> list = new ArrayList<>();
        for(String local : yml.getKeys(false)){
            if( local.contains(name)) {
                list.add(local);
            }
        }
        return list;
    }


    public void remove(String arg) {
        yml.set(arg, null);
        save();
    }
}
