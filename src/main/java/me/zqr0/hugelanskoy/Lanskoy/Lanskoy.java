package me.zqr0.hugelanskoy.Lanskoy;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Lanskoy {

    private boolean isAggressive;
    private final Giant giantMobEntity;
    private Location playerLocation;
    private UUID uuid;
    private final String NAME = "Lanskoy";

    Lanskoy(@NotNull Location playerLocation, boolean isAggressive) {
        this.isAggressive = isAggressive;
        this.playerLocation = playerLocation;

        Vector playerDirection = playerLocation.getDirection();

        this.giantMobEntity = (Giant) Objects.requireNonNull(playerLocation.getWorld()).spawnEntity(
                playerLocation.clone().add(
                        playerDirection.clone().multiply(5)
                ).setDirection(playerDirection.multiply(-1)), EntityType.GIANT);

        this.uuid = this.giantMobEntity.getUniqueId();

        this.giantMobEntity.setCustomName(this.NAME);
        this.giantMobEntity.setCustomNameVisible(true);
        Objects.requireNonNull(this.giantMobEntity.getEquipment()).setItemInMainHand(new ItemStack(Material.IRON_AXE));

        playerDirection.setY(0);
        playerDirection.normalize();
    }

    public boolean isAggressive() {
        return this.isAggressive;
    }

    public void setAggression(@NotNull boolean status) {
        this.isAggressive = status;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    private Entity findEntitiesNearby(Giant giant) {

        try {

            List<Entity> entityList = (List<Entity>) Objects.requireNonNull(giant
                            .getLocation()
                            .getWorld())
                    .getNearbyEntities(giant.getLocation(), 15, 15, 15);

            for (Entity entity : entityList) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (livingEntity instanceof Player) {

                        return (Player) livingEntity;
                    }

                    return livingEntity;
                }

                return null;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
