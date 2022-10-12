package me.zqr0.hugelanskoy;

import me.zqr0.hugelanskoy.Commands.GetAuthor;
import me.zqr0.hugelanskoy.Commands.SetGamemode;
import me.zqr0.hugelanskoy.Commands.SetGamemodeCompleter;
import me.zqr0.hugelanskoy.Commands.SpawnLanskoy;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Plugin instance = this;

        try {
            getCommand("getAuthor").setExecutor(new GetAuthor());
            getCommand("lanskoy").setExecutor(new SpawnLanskoy(instance));
            getCommand("setgamemode").setExecutor(new SetGamemode());
            getCommand("setgamemode").setTabCompleter(new SetGamemodeCompleter());
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        System.out.println("Plugin is finished");
    }
}
