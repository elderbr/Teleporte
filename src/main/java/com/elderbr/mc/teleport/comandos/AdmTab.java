package com.elderbr.mc.teleport.comandos;

import com.elderbr.mc.teleport.exceptions.AdmExcepetion;
import com.elderbr.mc.teleport.interfaces.Global;
import com.elderbr.mc.teleport.util.Msg;
import com.elderbr.mc.teleport.util.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdmTab implements TabCompleter, Global {

    private String name;
    private List<String> list = new ArrayList<>();

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            try{
                list = new ArrayList<>();
                name = Text.toString(args);
                String cmd = command.getName().toLowerCase();
                if(cmd.equals("addadm") || cmd.equals("removeadm")){
                    for(String adm : ADM_LIST){
                        if(adm.contains(name)){
                            list.add(adm);
                        }
                    }
                    return list;
                }
            }catch (AdmExcepetion e){
                Msg.PlayerGold(player, e.getMessage());
            }
        }
        return list;
    }
}
