package me.bart_.hackreporter;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Created by Bart_ on 20/11/2016.
 */
public class Servers implements PluginMessageListener {

    public static String[] serverList = null;
    public static String[] playerList = null;

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("GetServers")) {
            serverList = null;
            serverList = in.readUTF().split(", ");
        }
        if (subchannel.equals("PlayerList")) {
            playerList = null;
            playerList = in.readUTF().split(", ");
        }
    }
}
