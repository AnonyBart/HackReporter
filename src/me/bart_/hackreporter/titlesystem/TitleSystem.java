package me.bart_.hackreporter.titlesystem;

import org.bukkit.entity.Player;

public interface TitleSystem {

    public abstract void sendTitle(String title, String subtitle, Player Admin);
}