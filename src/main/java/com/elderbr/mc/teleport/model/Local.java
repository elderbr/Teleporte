package com.elderbr.mc.teleport.model;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class Local {

    private World world;
    private double x, z, y;

    public Local() {
    }

    public Local(World world, double x, double z, double y) {
        this.world = world;
        this.x = x;
        this.z = z;
        this.y = y;
    }

    public Local(Player player){
        world = player.getWorld();
        x = player.getLocation().getX()+0.5;
        z = player.getLocation().getX()+0.5;
        y = player.getLocation().getZ()+1;
    }

    public World getWorld() {
        return world;
    }

    public Local setWorld(World world) {
        this.world = world;
        return this;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
