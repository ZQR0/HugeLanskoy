package me.zqr0.hugelanskoy.Events;

import me.zqr0.hugelanskoy.Lanskoy.Manager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class LanskoyEvent implements Listener {

    private final Manager manager;

    public LanskoyEvent(Manager lanskoyManager) {
        manager = lanskoyManager;
    }

    @EventHandler
    public void startLanskoy(EntitySpawnEvent event, PlayerCommandSendEvent playerEvent) {
        Player player = (Player) playerEvent.getPlayer();
        boolean isAggressive = true;

        try {
            manager.spawnLanskoy(player.getLocation(), isAggressive);
        } catch (NullPointerException ex) {
            event.setCancelled(true);
            ex.printStackTrace();
        }
    }
}
