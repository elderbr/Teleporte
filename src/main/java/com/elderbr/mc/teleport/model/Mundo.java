package com.elderbr.mc.teleport.model;

import com.elderbr.mc.teleport.enums.MundoTipo;
import com.elderbr.mc.teleport.interfaces.IMundo;

public class Mundo implements IMundo {

    private String name;
    private MundoTipo type;

    public Mundo() {
    }

    public Mundo(String name, MundoTipo type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        if(!name.contains("world_")){
            name = "world_"+name;
        }
        return name;
    }

    @Override
    public IMundo setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public MundoTipo getWorldType() {
        return type;
    }

    @Override
    public IMundo setWorldType(MundoTipo type) {
        this.type = type;
        return this;
    }
}
