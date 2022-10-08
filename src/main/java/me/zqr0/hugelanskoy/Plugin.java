package me.zqr0.hugelanskoy;

import me.zqr0.hugelanskoy.Commands.GetAuthor;
import me.zqr0.hugelanskoy.Commands.SpawnLanskoy;
import me.zqr0.hugelanskoy.Events.LanskoyEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Plugin instance = this;

        try {
            getCommand("getAuthor").setExecutor(new GetAuthor());
            getCommand("lanskoy").setExecutor(new SpawnLanskoy(this));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        System.out.println("Plugin is finished");
    }
}
