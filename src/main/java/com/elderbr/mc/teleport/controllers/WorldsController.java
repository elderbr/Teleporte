package com.elderbr.mc.teleport.controllers;

import com.elderbr.mc.teleport.config.FileConfig;
import com.elderbr.mc.teleport.config.TeleportConfig;
import com.elderbr.mc.teleport.config.WorldConfig;
import com.elderbr.mc.teleport.enums.MundoTipo;
import com.elderbr.mc.teleport.interfaces.Global;
import com.elderbr.mc.teleport.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class WorldsController implements Global {

    private FileConfig config = FileConfig.getInstance();
    private WorldConfig worldConfig;
    private TeleportConfig localConfig = TeleportConfig.getInstance();
    private MundoTipo worldType = MundoTipo.NORMAL;

    public WorldsController() {
        worldConfig = WorldConfig.getInstance();
    }

    public boolean tpa(Player player, String[] args){
        if(args.length < 1){
            throw new RuntimeException("Digite o nome do mundo!");
        }
        String name = "world_"+args[0].toLowerCase().replaceAll("world_", "");
        if(!WORLDS_LIST.contains(name)){
            throw new RuntimeException("O mundo invalido ou não existe!");
        }

        World world = worldConfig.findByNameNormal(name);// Busca mundo no World
        if(Objects.isNull(world)){
            world = worldConfig.findByNameNether(name);// Busca o mundo no Nether
            if(Objects.isNull(world)){
                world = worldConfig.findByNameTheEnd(name);// Busca o mundo no The End
                if(Objects.isNull(world)){
                    throw new RuntimeException("O mundo invalido ou não existe!");
                }
            }
        }
        return player.teleport(world.getSpawnLocation());
    }

    public boolean create(Player player, String[] args) {
        if (Objects.isNull(player) || !player.isOp()) {
            throw new RuntimeException("§l§cOps, você não tem permissão para usar esse comando!");
        }

        if(args.length < 1){
            throw new RuntimeException("Digite o nome do mundo!");
        }

        String worldName = args[0];
        if (Objects.isNull(worldName) || worldName.isBlank()) {
            throw new RuntimeException("§l§cNome do mundo invalido!");
        }

        if(args.length == 2){
            String type = args[1].toLowerCase();
            switch (type.toLowerCase()){
                case "normal":
                    worldType = MundoTipo.NORMAL;
                    break;
                case "nether":
                    worldType = MundoTipo.NETHER;
                    break;
                case "the_end":
                    worldType = MundoTipo.THE_END;
                    break;
                default:
                    worldType = MundoTipo.NORMAL;
            }
        }

        Msg.PlayerAll(String.format("§l§eCriando novo mundo %s!", worldName));
        worldConfig.createWorld("world_" + worldName, worldType); // Salva novo mundo escolhendo o tipo
        Msg.PlayerAll(String.format("§l§eMundo %s criado com sucesso!", worldName));
        return true;
    }

    public World findByName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new RuntimeException(String.format("§l§cNome %s do mundo invalido!", name));
        }
        String worldName = name;
        if (!name.contains("world_")) {
            worldName = "world_" + name;
        }
        World world = worldConfig.findByName(worldName);
        if (Objects.isNull(world)) {
            throw new RuntimeException(String.format("§l§cNome %s do mundo invalido!", name));
        }
        return world;
    }

    public boolean delete(Player player, String worldName) {
        if(!player.isOp()){
            Msg.PlayerGold(player, "Ops, você não tem permissão para usar esse comando!");
            return false;
        }
        if (Objects.isNull(worldName) || worldName.isBlank()) {
            throw new RuntimeException("Nome do mundo invalido");
        }
        World world = findByName(worldName);
        boolean unloaded = Bukkit.unloadWorld(world, false);
        if (!unloaded) {
            throw new RuntimeException("Não foi possivel parar o mundo!");
        }
        File worldFolder = new File(".");
        for (File file : worldFolder.listFiles()) {
            if (!file.isDirectory()) continue;
            if (file.getName().equals(world.getName())) {
                deleteFolder(file);
            }
        }
        FileConfig.deleteWorld(world.getName());// Removendo o mundo da lista
        TeleportController.getInstance().deleteByNameWorld(player, world.getName());// Apagando todos as home do mundo
        Msg.PlayerAll(String.format("O mundo %s foi apagado!", worldName));// Mensagem para todos os players
        return true;
    }

    public void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { // Verifica se há arquivos/pastas na pasta
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);// Deleta subpastas recursivamente
                } else {
                    file.delete();// Deleta arquivo
                }
            }
        }
        folder.delete();// Deleta a pasta em si
    }
}
