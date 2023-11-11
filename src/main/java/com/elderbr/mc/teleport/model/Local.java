package com.elderbr.mc.teleport.model;

import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class Local {

    private String name;
    private World world;
    private Player player;
    private double x, z, y;

    public Local() {
    }

    public Local(Player player, String name) {
        this.name = name;
        world = player.getWorld();
        this.player = player;
        x = player.getLocation().getX();
        y = player.getLocation().getY();
        z = player.getLocation().getZ();
    }

    public String getName() {
        return name;
    }

    public Local setName(String name) {
        this.name = name;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public Local setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public World getWorld() {
        return world;
    }

    public Local setWorld(World world) {
        this.world = world;
        return this;
    }
    public Local setWorld(String world) {
        this.world = Bukkit.getWorld(world);
        return this;
    }

    public double getX() {
        return parseDouble(x);
    }

    public Local setX(double x) {
        this.x = x;
        return this;
    }

    public double getZ() {
        return parseDouble(z);
    }

    public Local setZ(double z) {
        this.z = z;
        return this;
    }

    public double getY() {
        return parseDouble(y)+0.5;
    }

    public Local setY(double y) {
        this.y = y;
        return this;
    }

    private double parseDouble(double value){
        double x = (int) value;
        return (x > 0 ? x + 0.5 : x - 0.5);
    }

    public String toLocal() {
        return world.getName();
    }

    @Override
    public String toString() {
        return "Local{" +
                "world=" + world +
                ", player=" + player +
                ", x=" + x +
                ", z=" + z +
                ", y=" + y +
                '}';
    }
}
