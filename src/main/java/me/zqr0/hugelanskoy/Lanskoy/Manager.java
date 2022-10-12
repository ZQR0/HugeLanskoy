package me.zqr0.hugelanskoy.Lanskoy;

import me.zqr0.hugelanskoy.Events.LanskoyEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


public class Manager {


    public Manager(Plugin thisPlugin) {
        Bukkit.getPluginManager().registerEvents(new LanskoyEvent(this), thisPlugin);
    }

    public void spawnLanskoy(@NotNull Location playerLocation, boolean isAggressive) {
        new Lanskoy(playerLocation, isAggressive).startLanskoy();
    }

}
