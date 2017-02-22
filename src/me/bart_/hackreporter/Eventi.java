package me.bart_.hackreporter;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bart_ on 02/10/2016.
 */
public class Eventi implements Listener {

    List<String> messages = FilesCreate.config.getStringList("CommandsToBlock");

    @EventHandler
    private void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        String lowercase = e.getMessage().toLowerCase();

        if (Commands.previousl.get(e.getPlayer().getUniqueId()) != null && !e.getPlayer().hasPermission("report.use.staff") && !e.getPlayer().hasPermission("report.check.commandsbypass")) {
            for (String comando : messages) {
                if (lowercase.startsWith(comando.toLowerCase())) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Translations.cantexecutewhilecontrollo")));
                }
            }
        } else return;
    }

    private String data (int seconds) {
        String ritorno = null;

        if (seconds <=0 ) {
            ritorno = "&7&oPERMANENT";
            return ritorno;
        }

        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
        ritorno = "&7&oDays: " + day + ", Hours: " + hours + ", Minutes: " + minute + ", Seconds: " + second;

        return ritorno;
    }

    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent e) {
        if (Commands.previousl.get(e.getPlayer().getUniqueId()) != null && !e.getPlayer().hasPermission("report.use.staff")) {
            String messaggio = FilesCreate.config.getString("Ban.broadcastbanmessage").replaceAll("%c", e.getPlayer().getName());
            String tempo = data(FilesCreate.config.getInt("Ban.time"));
            messaggio = messaggio.replaceAll("%s" , tempo);
            if (FilesCreate.config.getBoolean("Ban.value") == true) {
                Date data = new Date(System.currentTimeMillis() + (FilesCreate.config.getInt("Ban.time")*1000));
                Bukkit.getBanList(BanList.Type.NAME).addBan(e.getPlayer().getName(), ChatColor.translateAlternateColorCodes('&', FilesCreate.config.getString("Ban.banmessage")), data, null);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', messaggio));
                Commands.previousl.remove(e.getPlayer().getUniqueId());
                Commands.previousl.remove(Commands.controlli.get(e.getPlayer().getUniqueId()));
                Commands.controlli.remove(e.getPlayer().getUniqueId());
                return;
            }
            Commands.previousl.remove(e.getPlayer().getUniqueId());
            Commands.previousl.remove(Commands.controlli.get(e.getPlayer().getUniqueId()));
            Commands.controlli.remove(e.getPlayer().getUniqueId());
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', messaggio));
            return;
        } if (Commands.previousl.get(e.getPlayer().getUniqueId()) != null && e.getPlayer().hasPermission("report.use.staff")) {
            Commands.previousl.remove(e.getPlayer().getUniqueId());
            Commands.previousl.remove(Commands.controlli.get(e.getPlayer().getUniqueId()));
            Commands.controlli.remove(e.getPlayer().getUniqueId());
            return;
        }
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPermission("report.use.staff") && Variabili.reportnotsee.size() > 0) {
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l======================== Reports to view ======================="));
            e.getPlayer().sendMessage("");
            for (int i = 0; i < Variabili.reportnotsee.size(); i++) {
                String nomeplayer = Variabili.reportnotsee.get(i);
                String messaggio = FilesCreate.config.getString("Translations.reportmessageadmin");
                messaggio = messaggio.replaceAll("%c", FilesCreate.reports.getString(nomeplayer + ".ReportedBy"));
                messaggio = messaggio.replaceAll("%s", nomeplayer);
                messaggio = messaggio.replaceAll("%n", FilesCreate.reports.getString(nomeplayer + ".Motivation"));
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', messaggio));
                FilesCreate.reports.set(nomeplayer + ".ReportedSee", true);
                try {
                    FilesCreate.reports.save(FilesCreate.reportsf);
                } catch (IOException event) {
                    event.printStackTrace();
                }
            }
            e.getPlayer().sendMessage("");
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l======================== Reports to view ======================="));
            Variabili.reportnotsee.clear();
        } else return;
    }


}
