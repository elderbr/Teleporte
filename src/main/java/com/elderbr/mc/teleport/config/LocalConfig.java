package com.elderbr.mc.teleport.config;

import com.elderbr.mc.teleport.interfaces.Global;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LocalConfig implements Global {

    private static LocalConfig instance;
    private File file = TELEPORT_FILE;
    private YamlConfiguration config;

    private LocalConfig(){
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        }catch (IOException e){
            throw new RuntimeException("Erro ao criar o arquivo teleport.yml");
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static LocalConfig getInstance(){
        synchronized (LocalConfig.class) {
            if (Objects.isNull(instance)) {
                instance = new LocalConfig();
            }
            return instance;
        }
    }

    public void save(Player player, String name) {
        Block local = player.getLocation().subtract(0, 1, 0).getBlock();
        config.set(name + ".world", player.getWorld().getName());
        config.set(name + ".location", local.getX()+" "+ local.getY()+" "+ local.getZ() );
        save();
    }

    public Location findLocal(String name) {
        World world = Bukkit.getWorld(config.getString(name + ".world"));
        String[] local = config.getString(name + ".location").split("\\s");
        return new Location(world, Double.parseDouble(local[0]), Double.parseDouble(local[1]), Double.parseDouble(local[2]));
    }

    public boolean save(){
        try{
            config.save(file);
            return true;
        }catch (IOException e){
            throw new RuntimeException("Erro ao salvar nova posição!");
        }
    }
}
