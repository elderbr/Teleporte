package com.elderbr.mc.teleport.config;

import com.elderbr.mc.teleport.interfaces.Global;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Objects;

public class LocationConfig implements Global {

    private static LocationConfig instance;
    private YamlConfiguration config;

    private LocationConfig(){
        config = FileConfig.getInstance().getConfig();
    }

    public static LocationConfig getInstance(){
        if(Objects.isNull(instance)){
            instance = new LocationConfig();
        }
        return instance;
    }
}
