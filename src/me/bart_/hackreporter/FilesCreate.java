package me.bart_.hackreporter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FilesCreate {
    public static File configf, reportsf;
    public static FileConfiguration config, reports;

    public static void configfile() {

        configf = new File(Main.instance.getDataFolder(), "config.yml");

        if (configf.exists()) {
            config = YamlConfiguration.loadConfiguration(configf);
            Bukkit.getConsoleSender().sendMessage("File config.yml already exist!");
            if (config.getInt("Version") < 4.0 || config.get("Version") == null) {
                if (config.getString("Translations.banmessage") != null) {
                    Bukkit.getConsoleSender().sendMessage("It seems you updated the plugin...let's update the config too ;)");
                    String banmessage = config.getString("Translations.banmessage");
                    String broadcastbanmessage = config.getString("Translations.broadcastbanmessage");
                    config.set("Translations.banmessage", null);
                    config.set("Translations.broadcastbanmessage", null);
                    config.createSection("Ban");
                    config.set("Ban.value", true);
                    config.set("Ban.banmessage", "&6&lYou left the server while the controllo isn't finished!");
                    config.set("Ban.broadcastbanmessage", "&c&lThe player &7&n%c &c&lleft the server during a controllo SO he got PERMANENT BANNED!");
                    try {
                        Bukkit.getConsoleSender().sendMessage("Saving & Loading config.yml");
                        config.save(configf);
                        config.load(configf);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (config.getString("Translations.controllostart") == null) {
                    Bukkit.getConsoleSender().sendMessage("It seems you updated the plugin...let's update the config too ;)");
                    config.set("Translations.controllostart", "&6&lThe Staffer &c&o%c &6&ldecided to make a check, don't leave the server or you will be &4&lpunished&6&l...");
                    try {
                        Bukkit.getConsoleSender().sendMessage("Saving & Loading config.yml");
                        config.save(configf);
                        config.load(configf);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (config.getString("Translations.titlecheckstart") == null) {
                    Bukkit.getConsoleSender().sendMessage("It seems you updated the plugin...let's update the config too ;)");
                    config.set("Translations.titlecheckstart", "&4&oCONTROLLO!");
                    config.set("Translations.subtitlecheckstart", "&4&oREAD THE CHAT AND DON'T LEFT!");
                    try {
                        Bukkit.getConsoleSender().sendMessage("Saving & Loading config.yml");
                        config.save(configf);
                        config.load(configf);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (config.getString("Ban.time") == null) {
                    Bukkit.getConsoleSender().sendMessage("It seems you updated the plugin...let's update the config too ;)");
                    config.set("Ban.time", 3600);
                    try {
                        Bukkit.getConsoleSender().sendMessage("Saving & Loading config.yml");
                        config.save(configf);
                        config.load(configf);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (config.getString("Settings.usingbungeecord") == null) {
                    Bukkit.getConsoleSender().sendMessage("It seems you updated the plugin...let's update the config too ;)");
                    config.createSection("Settings");
                    config.set("Settings.usingbungeecord", false);
                    try {
                        Bukkit.getConsoleSender().sendMessage("Saving & Loading config.yml");
                        config.save(configf);
                        config.load(configf);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                config.set("Ban.broadcastbanmessage", "&c&lThe player &7&n%c &c&lleft the server during a controllo SO he got banned for: %s!");
            }
        }

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            try {
                Bukkit.getConsoleSender().sendMessage("Creating config.yml");
                configf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            config = YamlConfiguration.loadConfiguration(configf);
            try {
                Bukkit.getConsoleSender().sendMessage("Saving config.yml");
                config.save(configf);
            } catch (IOException e) {
                e.printStackTrace();
            }

            config = new YamlConfiguration();
            // TRADUZIONE
            config.set("Version", 4.0);
            config.createSection("Settings");
            config.set("Settings.usingbungeecord", false);
            config.createSection("Translations");
            config.set("Translations.helpmessage", "&a&oTo report a player use: &e&l/report &7&l<playername> <reason>");
            config.set("Translations.cantfindplayer", "&4&oCan't find the player %s Or simple you can't report a staffer!");
            config.set("Translations.cantfindreason", "&4&oYou forgot to put the reason!");
            config.set("Translations.cantexecutewhilecontrollo", "&c&oYou can't use this command while the controllo!");
            config.set("Translations.reportmessage", "&e&lYou reported the player &7&n%s &e&lfor: &a&o%n");
            config.set("Translations.reportmessageadmin", "&4&lThe player &7&n%c have reported &7&n%s &e&lfor: &a&o%n");
            config.set("Translations.forgotsetspawn", "&4&lYou forgot to set the spawn for the controllo!");
            config.set("Translations.controllofinish", "&6&lThe controllo has finished! &7&nYou've been teleported to your previous location.");
            config.set("Translations.controllostart", "&6&lThe Staffer &c&o%c &6&ldecided to make a check, don't leave the server or you will be &4&lpunished&6&l...");
            config.set("Translations.cantreportyourself", "&4&oYou can't report yoursel!");
            config.set("Translations.titlecheckstart", "&4&oCONTROLLO!");
            config.set("Translations.subtitlecheckstart", "&4&oREAD THE CHAT AND DON'T LEFT!");
            // VALORI BAN
            config.createSection("Ban");
            config.set("Ban.value", true);
            config.set("Ban.time", 3600);
            config.set("Ban.banmessage", "&6&lYou left the server while the controllo isn't finished!");
            config.set("Ban.broadcastbanmessage", "&c&lThe player &7&n%c &c&lleft the server during a controllo SO he got banned for %s!");
            // VALORI SPAWN
            config.createSection("ControlloSpawn");
            config.set("ControlloSpawn.World", "");
            config.set("ControlloSpawn.x", 0.0d);
            config.set("ControlloSpawn.y", 0.0d);
            config.set("ControlloSpawn.z", 0.0d);
            config.set("ControlloSpawn.yaw", 0.0f);
            config.set("ControlloSpawn.pitch", 0.0f);
            // VALORI COMANDI DA BLOCCARE
            config.createSection("CommandsToBlock");
            List<String> messages = Arrays.asList("/tpa", "/home", "/spawn", "/suicide");
            config.set("CommandsToBlock", messages);

            try {
                Bukkit.getConsoleSender().sendMessage("Saving & Loading config.yml");
                config.save(configf);
                config.load(configf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(configf);
    }


    public static void reportsfile() {

        reportsf = new File(Main.instance.getDataFolder(), "reports.yml");

        if (reportsf.exists()) Bukkit.getConsoleSender().sendMessage("File reports.yml already exist!");
        if (!reportsf.exists()) {
            reportsf.getParentFile().mkdirs();
            try {
                Bukkit.getConsoleSender().sendMessage("Creating reports.yml");
                reportsf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reports = YamlConfiguration.loadConfiguration(reportsf);
            try {
                Bukkit.getConsoleSender().sendMessage("Saving reports.yml");
                reports.save(reportsf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reports = new YamlConfiguration();

        try {
            Bukkit.getConsoleSender().sendMessage("Saving & Loading reports.yml");
            reports.save(reportsf);
            reports.load(reportsf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reports = YamlConfiguration.loadConfiguration(reportsf);
    }

}
