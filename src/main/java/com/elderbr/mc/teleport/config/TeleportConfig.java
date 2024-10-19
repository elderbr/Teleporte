package com.elderbr.mc.teleport.config;

import com.elderbr.mc.teleport.interfaces.Global;
import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TeleportConfig implements Global {

    private static TeleportConfig instance;
    private File file = TELEPORT_FILE;
    private YamlConfiguration config;

    private TeleportConfig(){
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        }catch (IOException e){
            throw new RuntimeException("Erro ao criar o arquivo teleport.yml");
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static TeleportConfig getInstance(){
        synchronized (TeleportConfig.class) {
            if (Objects.isNull(instance)) {
                instance = new TeleportConfig();
            }
            return instance;
        }
    }

    public boolean save(Player player, String name) {
        Block local = player.getLocation().getBlock();
        config.set(name + ".world", player.getWorld().getName());
        config.set(name + ".location", local.getX()+" "+ local.getY()+" "+ local.getZ() );
        LOCATION_LIST.add(name);
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
            config.set(world, null);
            save();
            LOCATION_LIST.remove(world);// Removendo o nome da home da lista global
            return true;
        }
        return false;
    }

    public boolean deleteByNameWorld(String nameWorld){
        for(Map.Entry<String, Object> obj : config.getValues(false).entrySet()){
            MemorySection memory = (MemorySection) obj.getValue();
            if(memory.getString("world").equals(nameWorld)) {
                config.set(obj.getKey(), null);
                save();
                LOCATION_LIST.remove(nameWorld);// Removendo o nome da home da lista global
            }
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
