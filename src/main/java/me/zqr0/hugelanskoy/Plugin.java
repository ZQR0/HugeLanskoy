package me.zqr0.hugelanskoy;

import me.zqr0.hugelanskoy.Commands.GetAuthor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            getCommand("getAuthor").setExecutor(new GetAuthor());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
