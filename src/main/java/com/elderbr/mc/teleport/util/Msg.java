package com.elderbr.mc.teleport.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Msg {

    public static void ServidorGreen(String msg){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+ msg);
    }

    public static void ServidorGreen(String msg, Class getClass){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+ "class>> "+ getClass.getName()+" - "+ msg);
    }

    public static void ServidorRed(String msg){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+ msg);
    }

    public static void ServidorGold(String msg){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD+ msg);
    }

    public static void ServidorGold(String msg, Class getClass){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD+ "class>> "+ getClass.getName()+" - "+ msg );
    }

    public static void ServidorWhite(String msg){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE+ msg);
    }

    public static void ServidorColored(String msg, String msg1, String msg2){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD+ msg+ " - "+ ChatColor.YELLOW + msg1 +" - "+ ChatColor.AQUA+ msg2 );
    }

    //===========================================================================//

    //Mensagem do player
    public static void PlayerGreen(Player player, String msg){
        player.sendMessage(ChatColor.GREEN+ msg);
    }

    public static void PlayerGold(Player player, String msg){
        player.sendMessage(ChatColor.GOLD+ msg);
    }

    public static void PlayerRed(Player player, String msg){
        player.sendMessage(ChatColor.RED+ msg);
    }


    //======================= ERRROS ====================================================//

    public static void ServidorErro(Exception error, String funcao, Class classe){
        Bukkit.getServer().getConsoleSender().sendMessage(
                "§dErro: "+ error.getMessage()+
                "\nFuncao: "+ funcao+
                "\nCausa: "+ error.getCause()+
                "\nClasse: "+ classe.getSimpleName() );
        error.printStackTrace();
    }

    public static void ServidorErro(String msg, String funcao, Class classe, Exception error){
        Bukkit.getServer().getConsoleSender().sendMessage("§dErro: "+ error.getMessage()
                +"\nfuncao: "+ funcao
                +"\nCausa: "+ error.getCause()
                +"\nClasse: "+ classe.getSimpleName() );
    }

}
