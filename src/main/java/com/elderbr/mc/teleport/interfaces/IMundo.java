package com.elderbr.mc.teleport.interfaces;

import com.elderbr.mc.teleport.enums.MundoTipo;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public interface IMundo {

    String getName();

    IMundo setName(String name);

    MundoTipo getWorldType();
    IMundo setWorldType(MundoTipo type);

    default World create(){
        WorldCreator create = new WorldCreator(getName());
        switch (getWorldType()) {
            case NETHER -> create.environment(World.Environment.NETHER);
            case THE_END -> create.environment(World.Environment.THE_END);
            default -> create.environment(World.Environment.NORMAL);
        }
        return create.createWorld();
    }

}
