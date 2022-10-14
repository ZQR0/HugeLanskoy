package me.zqr0.hugelanskoy.Events;

import me.zqr0.hugelanskoy.Lanskoy.Manager;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Arrays;
import java.util.List;

public class LanskoyEvent implements Listener {

    private final Manager manager;

    public LanskoyEvent(Manager lanskoyManager) {
        manager = lanskoyManager;
    }

    @EventHandler
    public void startLanskoy(EntitySpawnEvent event, PlayerCommandSendEvent playerEvent) {
        Player player = playerEvent.getPlayer();
        boolean isAggressive = true;

        try {
            manager.spawnLanskoy(player.getLocation(), isAggressive);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onDamageGiant(EntityDamageEvent event) {
        try {
            List<EntityDamageEvent.DamageCause> damageCauses = Arrays.asList(
                    EntityDamageEvent.DamageCause.FIRE,
                    EntityDamageEvent.DamageCause.FALL,
                    EntityDamageEvent.DamageCause.ENTITY_EXPLOSION,
                    EntityDamageEvent.DamageCause.BLOCK_EXPLOSION,
                    EntityDamageEvent.DamageCause.FIRE_TICK
            );

            if ((damageCauses.contains(event.getCause())) && (event.getEntity() instanceof Giant)) {
                event.setCancelled(true);
            }
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
}
