package me.bart_.hackreporter;


import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.bart_.hackreporter.titlesystem.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Created by Bart_ on 29/09/2016.
 */
public class Main extends JavaPlugin {

    public static Main instance;
    public static TitleSystem titlesystem;

    @Override
    public void onEnable() {

        instance = this;
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Activating plugin HackReported version: 4.0");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Credits by: Bart_");
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new Servers());


        if (setupTitleSystem()) {

            getLogger().info("Plugin ready to start...!");
            getLogger().info("The plugin setup process is complete!");

        } else {

            getLogger().severe("Plugin can't start!");
            getLogger().severe("Your server version is not compatible with this plugin, contact the author of the plugin 'Bart_' on SpigotMC for support!");

            instance = null;
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        //CREARE I FILE
        FilesCreate.configfile();
        FilesCreate.reportsfile();
        //REGISTRARE I COMANDI
        getCommand("report").setExecutor(new Commands());
        //report.use.setspawn
        getCommand("checksetspawn").setExecutor(new Commands());
        //report.use.staff
        getCommand("check").setExecutor(new Commands());
        getCommand("removereport").setExecutor(new Commands());
        //EVENT REGISTER
        Bukkit.getPluginManager().registerEvents(new Eventi(), this);
        //PRENDI LISTA SE I REPORT SONO VISTI OPPURE NO
        Variabili.lookfile();
    }

    @Override
    public void onDisable() {
        instance = null;
        if (FilesCreate.reportsf.exists()) {
            try {
                FilesCreate.reports.save(FilesCreate.reportsf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Disabling plugin HackReported version: 4.0");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Credits by: Bart_");
        Bukkit.getPluginManager().disablePlugin(this);
    }


    public static String CheckPlayer(String player, Player p) {
        getServersList(p);
        String found = "false";
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        while (Servers.serverList == null);
        if (Servers.serverList != null) {
            for (String s : Servers.serverList) {
                out.writeUTF("PlayerList");
                out.writeUTF(s);
                p.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
                while(Servers.playerList == null);
                for (String players : Servers.playerList) {
                    if (players.equalsIgnoreCase(player)) {
                        found = "true, " + s;
                        return found;
                    }
                }
            }
        }
        return found;
    }

    public static void getServersList(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        p.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
    }


    private boolean setupTitleSystem() {

        String version;

        try {

            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            return false;
        }

        getLogger().info("Your server is running version " + version);

        if (version.equals("v1_8_R3")) {
            //server is running 1.8-1.8.1 so we need to use the 1.8 R1 NMS class
            titlesystem = new TitleSystem_1_8_R3();

        } else if (version.equals("v1_8_R1")) {
            //server is running 1.8.3 so we need to use the 1.8 R2 NMS class
            titlesystem = new TitleSystem_1_8_R1();

        } else if (version.equals("v1_8_R2")) {
            //server is running 1.8.3 so we need to use the 1.8 R2 NMS class
            titlesystem = new TitleSystem_1_8_R2();

        } else if (version.equals("v1_9_R1")) {
            //server is running 1.8.3 so we need to use the 1.8 R2 NMS class
            titlesystem = new TitleSystem_1_9_R1();

        } else if (version.equals("v1_9_R2")) {
            //server is running 1.8.3 so we need to use the 1.8 R2 NMS class
            titlesystem = new TitleSystem_1_9_R2();

        } else if (version.equals("v1_10_R1")) {
            //server is running 1.8.3 so we need to use the 1.8 R2 NMS class
            titlesystem = new TitleSystem_1_10_2_R1();
        } else if (version.equals("v1_11_R1")) {
            //server is running 1.8.3 so we need to use the 1.8 R2 NMS class
            titlesystem = new TitleSystem_1_11_R1();
        }

        // This will return true if the server version was compatible with one of our NMS classes
        // because if it is, our actionbar would not be null
        return titlesystem != null;
    }

}
