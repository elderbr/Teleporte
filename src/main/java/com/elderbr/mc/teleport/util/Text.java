package com.elderbr.mc.teleport.util;

public class Text {

    public static String toString(String[] value){
        StringBuilder sb = new StringBuilder();
        for(String name : value){
            sb.append(name).append(" ");
        }
        return sb.toString().trim();
    }

}
