package me.zqr0.hugelanskoy.Lanskoy;

import me.zqr0.hugelanskoy.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
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

    public void startLanskoy() {
        this.findEntitiesNearby();
        this.startToMove();
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

    public boolean isDead() {
        return this.giantMobEntity.isDead();
    }

    public boolean isInWater() {
        return this.giantMobEntity.isInWater();
    }

    private LivingEntity findEntitiesNearby() {

        try {

            List<Entity> entityList = (List<Entity>) Objects.requireNonNull(this.giantMobEntity
                            .getLocation()
                            .getWorld())
                    .getNearbyEntities(this.giantMobEntity.getLocation(), 40, 40, 40);

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


    private void startToMove() {
        final Lanskoy lanskoy = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                LivingEntity livingLanskoy = lanskoy.giantMobEntity;

                if (lanskoy.isDead()) {
                    return;
                }

                Entity target = lanskoy.findEntitiesNearby();

                double xDiff = target.getLocation().getX() - livingLanskoy.getEyeLocation().getX();
                double yDiff = target.getLocation().getY() - livingLanskoy.getEyeLocation().getY();
                double zDiff = target.getLocation().getZ() - livingLanskoy.getEyeLocation().getZ();
                double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
                double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);
            }

        }.runTask(Plugin.getPlugin(Plugin.class));
    }
}
