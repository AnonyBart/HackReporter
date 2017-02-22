package me.bart_.hackreporter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Bart_ on 27/10/2016.
 */
public class Inventory {
    public static ItemStack hackitem = null;
    public static ItemStack bugabusingitem = null;
    public static ItemStack flameitem = null;
    public static org.bukkit.inventory.Inventory myInventory = Bukkit.createInventory(null, 27, ChatColor.GREEN + "" + ChatColor.UNDERLINE + "Choose a motivation");

    public static void createInventory() {
        ItemStack hack = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta hackmeta = hack.getItemMeta();
        hackmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lHack/Cheat!"));
        hackmeta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
        ArrayList<String> lorehack = new ArrayList<>();
        lorehack.add("Click me to set the motivation to 'Hack/Cheat'!");
        hackmeta.setLore(lorehack);
        hack.setItemMeta(hackmeta);
        hackitem = hack;
        myInventory.setItem(0, hackitem);

        ItemStack bugabusing = new ItemStack(Material.BOOK);
        ItemMeta bugabusingmeta = bugabusing.getItemMeta();
        bugabusingmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lBug Abusing!"));
        bugabusingmeta.addEnchant(Enchantment.KNOCKBACK, 6, true);
        ArrayList<String> lorebug = new ArrayList<>();
        lorebug.add("Click me to set the motivation to 'Bug Abusing'!");
        bugabusingmeta.setLore(lorebug);
        bugabusing.setItemMeta(bugabusingmeta);
        bugabusingitem = bugabusing;
        myInventory.setItem(4, bugabusingitem);

        ItemStack flame = new ItemStack(Material.ANVIL);
        ItemMeta flamemeta = flame.getItemMeta();
        flamemeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lFlame!"));
        flamemeta.addEnchant(Enchantment.KNOCKBACK, 6, true);
        ArrayList<String> loreflame = new ArrayList<>();
        loreflame.add("Click me to set the motivation to 'Flame'!");
        flamemeta.setLore(loreflame);
        flame.setItemMeta(flamemeta);
        flameitem = flame;
        myInventory.setItem(8, flameitem);
    }
}
