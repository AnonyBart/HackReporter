package me.bart_.hackreporter;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Commands implements CommandExecutor {

    public static Map<UUID, Location> previousl = new HashMap<>();
    public static Map<UUID, UUID> controlli = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            String sendername = p.getName();
            if (label.equalsIgnoreCase("report")) {
                if (args.length >= 1) {
                    if (Bukkit.getPlayer(args[0]) != null && !Bukkit.getPlayer(args[0]).hasPermission("report.use.staff") && !Bukkit.getPlayer(args[0]).isOp()) {
                        if (!Bukkit.getPlayer(args[0]).getUniqueId().equals(p.getUniqueId())) {
                            if (args.length >= 2) {
                                StringBuilder motivo = new StringBuilder("");
                                for (int i = 1; i < args.length; i++) {
                                    motivo.append(args[i] + " ");
                                }
                                String messaggio = FilesCreate.config.getString("Translations.reportmessage");
                                messaggio = messaggio.replaceAll("%s", Bukkit.getPlayer(args[0]).getDisplayName());
                                messaggio = messaggio.replaceAll("%n", String.valueOf(motivo));
                                messaggio = (ChatColor.translateAlternateColorCodes('&', messaggio));
                                Player playerr = Bukkit.getPlayer(args[0]);
                                String playero = Bukkit.getPlayer(args[0]).getDisplayName();
                                if (FilesCreate.reports.get(playero) == null) {
                                    FilesCreate.reports.createSection(playero);
                                    FilesCreate.reports.set(playero + ".UUID", String.valueOf(playerr.getUniqueId()));
                                    FilesCreate.reports.set(playero + ".Motivation", String.valueOf(motivo));
                                    FilesCreate.reports.set(playero + ".ReportedBy", p.getName());
                                    FilesCreate.reports.set(playero + ".ReportedTimes", 1);
                                    FilesCreate.reports.set(playero + ".ReportedSee", false);
                                    try {
                                        FilesCreate.reports.save(FilesCreate.reportsf);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else if (FilesCreate.reports.get(playero) != null) {

                                    String motivazione = FilesCreate.reports.getString(playero + ".Motivation") + " , " + String.valueOf(motivo);
                                    FilesCreate.reports.set(playero + ".Motivation", motivazione);
                                    FilesCreate.reports.set(playero + ".ReportedTimes", (FilesCreate.reports.getInt(playero + ".ReportedTimes") + 1));
                                    FilesCreate.reports.set(playero + ".ReportedSee", false);
                                    try {
                                        FilesCreate.reports.save(FilesCreate.reportsf);
                                        FilesCreate.reports.load(FilesCreate.reportsf);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                boolean reportedsee = false;
                                for (Player online : Bukkit.getOnlinePlayers()) {
                                    if (online.hasPermission("report.use.staff")) {
                                        // TEXT COMPONENT
                                        TextComponent message = new TextComponent(ChatColor.GOLD + "Click me to Teleport to the player reported!");
                                        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + playerr.getName()));
                                        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.AQUA + "Teleport to the player reported!").create()));
                                        // FINE TEXT COMPONENT
                                        String messaggio2 = FilesCreate.config.getString("Translations.reportmessageadmin");
                                        messaggio2 = messaggio2.replaceAll("%c", p.getName());
                                        messaggio2 = messaggio2.replaceAll("%n", String.valueOf(motivo));
                                        messaggio2 = messaggio2.replaceAll("%s", playerr.getName());
                                        messaggio2 = ChatColor.translateAlternateColorCodes('&', messaggio2);
                                        // messaggio2 = messaggio2.replaceAll("%s", message);
                                        online.sendMessage(messaggio2);
                                        online.sendMessage("");
                                        online.spigot().sendMessage(message);
                                        reportedsee = true;
                                    }
                                }
                                FilesCreate.reports.set(playero + ".ReportedSee", reportedsee);
                                try {
                                    FilesCreate.reports.save(FilesCreate.reportsf);
                                    FilesCreate.reports.load(FilesCreate.reportsf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                p.sendMessage(messaggio);
                                return true;
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.cantfindreason")));
                                return true;
                            }
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.cantreportyourself")));
                            return true;
                        }
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.cantfindplayer").replaceAll("%s", args[0])));
                        return true;
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.helpmessage")));
                    return true;
                }
            }

            if (p.hasPermission("report.use.setspawn") && label.equalsIgnoreCase("checksetspawn")) {
                Location controllospawn = p.getLocation();
                FilesCreate.config.set("ControlloSpawn.World", String.valueOf(controllospawn.getWorld().getName()));
                FilesCreate.config.set("ControlloSpawn.x", controllospawn.getX());
                FilesCreate.config.set("ControlloSpawn.y", controllospawn.getY());
                FilesCreate.config.set("ControlloSpawn.z", controllospawn.getZ());
                FilesCreate.config.set("ControlloSpawn.yaw", controllospawn.getYaw());
                FilesCreate.config.set("ControlloSpawn.pitch", controllospawn.getPitch());
                p.sendMessage(ChatColor.GREEN + "The controllo spawn has been set successfull");
                try {
                    FilesCreate.config.save(FilesCreate.configf);
                    FilesCreate.config.load(FilesCreate.configf);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            if (p.hasPermission("report.use.staff") && label.equalsIgnoreCase("check")) {
                if (args.length > 0) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (previousl.get(Bukkit.getPlayer(args[0]).getUniqueId()) == null && previousl.get(p.getUniqueId()) == null) {
                            if (!FilesCreate.config.getString("ControlloSpawn.World").equalsIgnoreCase("")) {
                                previousl.put(Bukkit.getPlayer(args[0]).getUniqueId(), Bukkit.getPlayer(args[0]).getLocation());
                                previousl.put(p.getUniqueId(), p.getLocation());
                                controlli.put(Bukkit.getPlayer(args[0]).getUniqueId(), p.getUniqueId());

                                World world = Bukkit.getWorld(FilesCreate.config.getString("ControlloSpawn.World"));
                                double x = FilesCreate.config.getDouble("ControlloSpawn.x");
                                double y = FilesCreate.config.getDouble("ControlloSpawn.y");
                                double z = FilesCreate.config.getDouble("ControlloSpawn.z");
                                float yaw = (float) FilesCreate.config.getDouble("ControlloSpawn.yaw");
                                float pitch = (float) FilesCreate.config.getDouble("ControlloSpawn.pitch");

                                Location finale = new Location(world, x, y, z, yaw, pitch);
                                p.teleport(finale);
                                // TITLE & SUBTITLE
                                String title = new String(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.titlecheckstart")));
                                String subtitle = new String(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.subtitlecheckstart")));
                                TitleSystems.TitSub(title, subtitle, Bukkit.getPlayer(args[0]));

                                Bukkit.getPlayer(args[0]).teleport(finale);
                                Bukkit.getPlayer(args[0]).sendMessage("-----------------------------------------------------");
                                Bukkit.getPlayer(args[0]).sendMessage("");
                                Bukkit.getPlayer(args[0]).sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.controllostart").replaceAll("%c", p.getDisplayName())));
                                Bukkit.getPlayer(args[0]).sendMessage("");
                                Bukkit.getPlayer(args[0]).sendMessage("-----------------------------------------------------");
                                p.sendMessage(ChatColor.GREEN + "The Player " + ChatColor.GRAY + Bukkit.getPlayer(args[0]).getName() + ChatColor.GREEN + " has received this message: " + ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.controllostart").replaceAll("%c", p.getDisplayName())));
                                return true;
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.forgotsetspawn")));
                                return true;
                            }
                        } else if (previousl.get(Bukkit.getPlayer(args[0]).getUniqueId()) != null && previousl.get(p.getUniqueId()) != null) {
                            Bukkit.getPlayer(args[0]).teleport(previousl.get(Bukkit.getPlayer(args[0]).getUniqueId()));
                            p.teleport(previousl.get(p.getUniqueId()));
                            previousl.remove(Bukkit.getPlayer(args[0]).getUniqueId());
                            previousl.remove(p.getUniqueId());
                            controlli.remove(Bukkit.getPlayer(args[0]).getUniqueId());
                            Bukkit.getPlayer(args[0]).sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.controllofinish")));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.controllofinish")));
                            return true;
                        }
                    } else {
                        if (FilesCreate.config.getBoolean("Settings.usingbungeecord") == true) {
                            String risultato = Main.CheckPlayer(args[0], p);
                            String[] risultatos = risultato.split(", ");
                            if (risultatos[0].equalsIgnoreCase("true")) {
                                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                                out.writeUTF("ConnectOther");
                                out.writeUTF(sendername);
                                out.writeUTF(risultatos[1]);

                                out.writeUTF("ForwardToPlayer");
                                out.writeUTF(sendername);
                                out.writeUTF("MyChannel");

                                ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
                                DataOutputStream msgout = new DataOutputStream(msgbytes);
                                try {
                                    msgout.writeUTF(ChatColor.RED + "You got teleported in the server where is the player, now run " + org.bukkit.ChatColor.YELLOW + org.bukkit.ChatColor.BOLD + "/controllo " + args[0]);
                                    msgout.writeShort(123);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                out.writeShort(msgbytes.toByteArray().length);
                                out.write(msgbytes.toByteArray());
                                return true;
                            }
                        }
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.cantfindplayer").replaceAll("%s", args[0])));
                        return true;
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.cantfindplayer").replaceAll("%s", "NULL")));
                    return true;
                }
            }

            if (p.hasPermission("report.use.staff") && label.equalsIgnoreCase("removereport")) {
                if (args.length > 0) {
                    if (FilesCreate.reports.get(args[0]) == null) {
                        p.sendMessage(ChatColor.RED + "Can't find the player " + ChatColor.YELLOW + args[0] + ChatColor.RED + " in the reports!");
                        return true;
                    } else {
                        FilesCreate.reports.set(args[0], null);
                        p.sendMessage(ChatColor.GREEN + "The player " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " has been removed from the reports with success!");
                    }
                } else p.sendMessage(ChatColor.RED + "You have to specify the name of the player!");
                return true;
            }

            if (label.equalsIgnoreCase("reporthelp")) {
                if (p.hasPermission("report.use.staff")) {
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " - - - - - - - - - - HackReporter - - - - - - - - - ");
                    p.sendMessage("");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.YELLOW + "/report [player] [motivation]      |      " + ChatColor.GRAY + "No Permission needed");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.YELLOW + "/checksetspawn      |      " + ChatColor.GRAY + "report.use.setspawn");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.YELLOW + "/check [player]      |      " + ChatColor.GRAY + "report.use.staff");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.YELLOW + "/removereport [player]      |      " + ChatColor.GRAY + "report.use.staff");
                    p.sendMessage("");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " - - - - - - - - - - HackReporter - - - - - - - - - ");
                    return true;
                }
                if (!p.hasPermission("report.use.staff")) {
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " - - - - - - - - - - HackReporter - - - - - - - - - ");
                    p.sendMessage("");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.YELLOW + "/report [player] [motivation]      |      " + ChatColor.GRAY + "No Permission needed");
                    p.sendMessage("");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " - - - - - - - - - - HackReporter - - - - - - - - - ");
                }
            }

            return true;
        } else {
            Bukkit.getConsoleSender().sendMessage("HackReporter can only be used in-game!");
            return true;
        }
    }

}
