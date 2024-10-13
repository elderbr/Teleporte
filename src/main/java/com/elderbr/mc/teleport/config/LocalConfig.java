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

    public boolean save(Player player, String name) {
        Block local = player.getLocation().getBlock();
        config.set(name + ".world", player.getWorld().getName());
        config.set(name + ".location", local.getX()+" "+ local.getY()+" "+ local.getZ() );
        save();
        return true;
    }

    public Location findLocal(String name) {
        World world = Bukkit.getWorld(config.getString(name + ".world"));
        String[] local = config.getString(name + ".location").split("\\s");
        return new Location(world, Double.parseDouble(local[0]), Double.parseDouble(local[1]), Double.parseDouble(local[2]));
    }

    public List<String> findAll(){
        config = YamlConfiguration.loadConfiguration(file);
        LOCATION_LIST.clear();
        for (Object local : config.getKeys(false)) {
            LOCATION_LIST.add(local.toString());
        }
        Collections.sort(LOCATION_LIST);
        return LOCATION_LIST;
    }

    public boolean delete(String world){
        if(!LOCATION_LIST.isEmpty()){
            LOCATION_LIST.remove(world);
            config.set(world, null);
            save();
            return true;
        }
        return false;
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
