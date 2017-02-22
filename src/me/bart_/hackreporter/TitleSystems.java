package me.bart_.hackreporter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class TitleSystems {

    public static void TitSub(String title, String subtitle, Player player) {


        Main.titlesystem.sendTitle(title, subtitle, player);
    }

}
